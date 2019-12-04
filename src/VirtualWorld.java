import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

import processing.core.*;

/*
VirtualWorld is our main wrapper
It keeps track of data necessary to use Processing for drawing but also keeps track of the necessary
components to make our world run (eventScheduler), the data in our world (WorldModel) and our
current view (think virtual camera) into that world (WorldView)
 */

public final class VirtualWorld
        extends PApplet
{
   public static final int TIMER_ACTION_PERIOD = 100;

   public static final int VIEW_WIDTH = 800;
   public static final int VIEW_HEIGHT = 640;
   public static final int TILE_WIDTH = 32;
   public static final int TILE_HEIGHT = 32;
   public static final int WORLD_WIDTH_SCALE = 1;
   public static final int WORLD_HEIGHT_SCALE = 1;

   public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
   public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
   public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
   public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

   public static final String IMAGE_LIST_FILE_NAME = "imagelist";
   public static final String DEFAULT_IMAGE_NAME = "background_default";
   public static final int DEFAULT_IMAGE_COLOR = 0x808080;

   public static final String LOAD_FILE_NAME = "world.sav";

   public static final String FAST_FLAG = "-fast";
   public static final String FASTER_FLAG = "-faster";
   public static final String FASTEST_FLAG = "-fastest";
   public static final double FAST_SCALE = 0.5;
   public static final double FASTER_SCALE = 0.25;
   public static final double FASTEST_SCALE = 0.10;

   public static double timeScale = 1.0;

   public ImageStore imageStore;
   public WorldModel world;
   public WorldView view;
   public EventScheduler scheduler;
   private static boolean start = false;
   private int numMovees = 0;
   public static boolean endWin = false;
   public static boolean endLose = false;
   private static boolean startSpawn = true;
   private Entity curBattery;
   private static boolean startBattery = true;
   private static int GAME_TIMER = 30;
   private static int BATTERY_INTERVALS = 5;

   public long next_time;

   public void settings()
   {
      size(VIEW_WIDTH, VIEW_HEIGHT);
   }

   /*
      Processing entry point for "sketch" setup.
   */
   public void setup() {
      this.imageStore = new ImageStore(
              createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
      this.world = new WorldModel(WORLD_ROWS, WORLD_COLS,
              createDefaultBackground(imageStore));
      this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world,
              TILE_WIDTH, TILE_HEIGHT);
      this.scheduler = new EventScheduler(timeScale);

      loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
      loadWorld(world, LOAD_FILE_NAME, imageStore);

      scheduleActions(world, scheduler, imageStore);

      next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
   }

   public void mouseClicked(){
      start = true;
      TimerTask gameTimerTask = new TimerTask() {
         public void run() {
            //go to lose game screen
            start = false;
            endLose = true;
         }
      };
      Timer gameTimer = new Timer("Timer");

      long gameDelay = GAME_TIMER*1000L;
      gameTimer.schedule(gameTimerTask, gameDelay);
      if (startBattery) {
      TimerTask batteryTask = new TimerTask() {
         public void run() {
            //lose battery cell
            batteryState();
         }
      };
      Timer batteryTimer = new Timer("Timer");

      long battDelay  = BATTERY_INTERVALS*1000L;
      long period = BATTERY_INTERVALS*1000L;
         batteryTimer.scheduleAtFixedRate(batteryTask, battDelay, period);
         startBattery = false;
      }
   }

   private void batteryState(){
      if (curBattery instanceof Battery1)
      {curBattery = ((Battery1)curBattery).changeBattery(world, imageStore);}
      else if (curBattery instanceof Battery2)
      {curBattery = ((Battery2)curBattery).changeBattery(world, imageStore);}
      else
      { curBattery = ((Battery3)curBattery).changeBattery(world, imageStore, scheduler);}
   }

   public void draw()
   {
      if (start) {
         long time = System.currentTimeMillis();
         if (time >= next_time) {
            EventScheduler.updateOnTime(this.scheduler, time);
            next_time = time + TIMER_ACTION_PERIOD;
         }
         view.drawViewport();
      }
      else
      {
         Color color = new Color(1, 70, 174);
         fill(color.getRGB());
         rect(0, 0, 800, 650);
         fill(255);
         textSize(32);
         text("Your battery is running low (4%)", 175, 200);
         text("Click Anywhere to Continue", 175, 300);
      }
      if (endWin)
      { drawWinScreen(); }
      if (endLose)
      { drawLoseScreen();}
   }

   public void drawLoseScreen()
   {
      Color color = new Color(255, 0, 0);
      fill(color.getRGB());
      rect(0, 0, 800, 650);
      fill(255);
      textSize(40);
      text("You lose!", 175, 200);
   }
   public void drawWinScreen()
   {

      Color color = new Color(0, 100, 255);
      fill(color.getRGB());
      rect(0, 0, 800, 650);
      fill(255);
      textSize(40);
      text("You win!", 175, 200);
   }

   public void keyPressed()
   {
      if (key == CODED)
      {
         int dx = 0;
         int dy = 0;

         switch (keyCode)
         {
            case UP:
               dy = -1;
               break;
            case DOWN:
               dy = 1;
               break;
            case LEFT:
               dx = -1;
               break;
            case RIGHT:
               dx = 1;
               break;
         }
         if (start == true){
            if (startSpawn){
               TimerTask repeatedTask = new TimerTask() {
                  public void run() {
                     StudentFactory.addStudent(world,imageStore,scheduler);
                  }
               };
               Timer timer = new Timer("Timer");

               long delay  = 3000L;
               long period = 3000L;
               timer.scheduleAtFixedRate(repeatedTask, delay, period);
               startSpawn = false;
            }
//            numMovees +=1;
//            if (numMovees == 30)
//            {
//               StudentFactory.addStudent(world, imageStore, scheduler);
//               numMovees = 0;
//            }
            Hatalsky.getInstance().moveTo(world, dx, dy, scheduler);
         }
      }
   }

   public static Background createDefaultBackground(ImageStore imageStore)
   {
      return new Background(DEFAULT_IMAGE_NAME,
              ImageStore.getImageList(imageStore, DEFAULT_IMAGE_NAME));
   }

   public static PImage createImageColored(int width, int height, int color)
   {
      PImage img = new PImage(width, height, RGB);
      img.loadPixels();
      for (int i = 0; i < img.pixels.length; i++)
      {
         img.pixels[i] = color;
      }
      img.updatePixels();
      return img;
   }

   private static void loadImages(String filename, ImageStore imageStore,
                                  PApplet screen)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         imageStore.loadImages(in, imageStore, screen);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public void loadWorld(WorldModel world, String filename,
                         ImageStore imageStore)
   {
      try
      {
         Scanner in = new Scanner(new File(filename));
         load(in, world, imageStore);
      }
      catch (FileNotFoundException e)
      {
         System.err.println(e.getMessage());
      }
   }

   public static void scheduleActions(WorldModel world,
                                      EventScheduler scheduler, ImageStore imageStore)
   {
      for (Entity entity : world.nonObsentities)
      {
         //Only start actions for entities that include action (not those with just animations)

         if (entity instanceof ActiveEntity && ((ActiveEntity)entity).getActionPeriod() > 0)
            ((ActiveEntity)entity).scheduleActions(scheduler, world, imageStore);
      }
   }

   public static void parseCommandLine(String [] args)
   {
      for (String arg : args)
      {
         switch (arg)
         {
            case FAST_FLAG:
               timeScale = Math.min(FAST_SCALE, timeScale);
               break;
            case FASTER_FLAG:
               timeScale = Math.min(FASTER_SCALE, timeScale);
               break;
            case FASTEST_FLAG:
               timeScale = Math.min(FASTEST_SCALE, timeScale);
               break;
         }
      }
   }

   public void scheduleStudentSpawn(){
      StudentFactory.addStudent(world, imageStore, scheduler);
      TimerTask task = new TimerTask() {
         @Override
         public void run() {
            System.out.println("Spawn student");
            scheduleStudentSpawn();
         }
      };
      Timer timer = new Timer();
      timer.schedule(task,100000);
   }

   public static void main(String [] args)
   {
      parseCommandLine(args);
      PApplet.main(VirtualWorld.class);
   }
   public void load(Scanner in, WorldModel world, ImageStore imageStore)
   {
      int lineNumber = 0;
      while (in.hasNextLine())
      {
         try
         {
            if (!processLine(in.nextLine(), world, imageStore))
            {
               System.err.println(String.format("invalid entry on line %d",
                       lineNumber));
            }
         }
         catch (NumberFormatException e)
         {
            System.err.println(String.format("invalid entry on line %d",
                    lineNumber));
         }
         catch (IllegalArgumentException e)
         {
            System.err.println(String.format("issue on line %d: %s",
                    lineNumber, e.getMessage()));
         }
         lineNumber++;
      }
   }
   public boolean processLine(String line, WorldModel world,
                              ImageStore imageStore)
   {
      String[] properties = line.split("\\s");
      if (properties.length > 0) {
         switch (properties[PROPERTY_KEY]) {
            case BGND_KEY:
               return parseBackground(properties, world, imageStore);
            case Octo_Full.OCTO_KEY:
               return parseOcto(properties, world, imageStore);
            case Obstacle.OBSTACLE_KEY:
               return parseObstacle(properties, world, imageStore);
            case Hatalsky.HATALSKY_ID:
               return parseHatal(properties, world, imageStore);
            case ChargeIntoOutlet.INTO_OUTLET_ID:
               return parseIntoOutlet(properties, world, imageStore);
            case ChargePlug.PLUG_ID:
               return parsePlug(properties, world, imageStore);
            case ChargePowerBrick.POWER_BRICK_ID:
               return parsePowerBrick(properties, world, imageStore);
            case Battery1.BATTERY1_ID:
               return parseBattery(properties, world, imageStore);
         }
      }

      return false;
   }

   public boolean parseBattery(String [] properties, WorldModel world,
                               ImageStore imageStore) {
      if (properties.length == Battery1.BATTERY1_NUM_PROP) {
         Point pt = new Point(Integer.parseInt(properties[2]),
                 Integer.parseInt(properties[3]));
         Battery1 battery1 = new Battery1(Battery1.BATTERY1_ID, pt, imageStore.getImageList(imageStore, Battery1.BATTERY1_ID), 0);
         world.tryAddEntity(battery1);
         curBattery = battery1;
      }
      return properties.length == Battery1.BATTERY1_NUM_PROP;
   }
   public boolean parsePowerBrick(String [] properties, WorldModel world,
                                  ImageStore imageStore) {
      if (properties.length == ChargePowerBrick.POWER_BRICK_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[2]),
                 Integer.parseInt(properties[3]));
         ChargePowerBrick powerBrick = new ChargePowerBrick(ChargePowerBrick.POWER_BRICK_ID, pt, imageStore.getImageList(imageStore,ChargePowerBrick.POWER_BRICK_ID), 0 );
         world.tryAddEntity(powerBrick);
         Hatalsky.addChargerParts(powerBrick);
      }
      return properties.length == ChargePowerBrick.POWER_BRICK_NUM_PROPERTIES;
   }
   public boolean parseIntoOutlet(String [] properties, WorldModel world,
                                  ImageStore imageStore) {
      if (properties.length == ChargeIntoOutlet.INTO_OUTLET_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[2]),
                 Integer.parseInt(properties[3]));
         ChargeIntoOutlet outlet = new ChargeIntoOutlet(ChargeIntoOutlet.INTO_OUTLET_ID, pt, imageStore.getImageList(imageStore, ChargeIntoOutlet.INTO_OUTLET_ID), 0 );
         world.tryAddEntity(outlet);
         Hatalsky.addChargerParts(outlet);
      }
      return properties.length == ChargeIntoOutlet.INTO_OUTLET_NUM_PROPERTIES;
   }
   public boolean parsePlug(String [] properties, WorldModel world,
                            ImageStore imageStore) {
      if (properties.length == ChargePlug.PLUG_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[2]),
                 Integer.parseInt(properties[3]));
         ChargePlug plug = new ChargePlug(ChargePlug.PLUG_ID, pt, imageStore.getImageList(imageStore,ChargePlug.PLUG_ID), 0 );
         world.tryAddEntity(plug);
         Hatalsky.addChargerParts(plug);
      }
      return properties.length == ChargePlug.PLUG_NUM_PROPERTIES;
   }


   public boolean parseHatal(String [] properties, WorldModel world,
                             ImageStore imageStore) {
      if (properties.length == Hatalsky.HATALSKY_NUM_PROPERTIES) {
         Point pt = new Point(Integer.parseInt(properties[2]),
                 Integer.parseInt(properties[3]));
         Hatalsky hatalsky = Hatalsky.getHatalsky(imageStore, pt, scheduler, world);
         world.tryAddEntity(hatalsky);
      }
      return properties.length == Hatalsky.HATALSKY_NUM_PROPERTIES;
   }


   public static boolean parseBackground(String [] properties,
                                         WorldModel world, ImageStore imageStore)
   {
      if (properties.length == BGND_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                 Integer.parseInt(properties[BGND_ROW]));
         String id = properties[BGND_ID];
         world.setBackground( pt,
                 new Background(id, imageStore.getImageList(imageStore, id)));
      }

      return properties.length == BGND_NUM_PROPERTIES;
   }


   public static boolean parseOcto(String [] properties, WorldModel world,
                                   ImageStore imageStore)
   {
      if (properties.length == Octo_Full.OCTO_NUM_PROPERTIES)
      {
         Point pt = new Point(Integer.parseInt(properties[Octo_Full.OCTO_COL]),
                 Integer.parseInt(properties[Octo_Full.OCTO_ROW]));
         Octo_Not_Full octo = new Octo_Not_Full(properties[Octo_Full.OCTO_ID],
                 Integer.parseInt(properties[Octo_Full.OCTO_LIMIT]),
                 pt,
                 Integer.parseInt(properties[Octo_Full.OCTO_ACTION_PERIOD]),
                 Integer.parseInt(properties[Octo_Full.OCTO_ANIMATION_PERIOD]),
                 ImageStore.getImageList(imageStore, Octo_Full.OCTO_KEY));
         world.tryAddEntity( octo);
      }

      return properties.length == Octo_Full.OCTO_NUM_PROPERTIES;
   }

   public static boolean parseObstacle(String [] properties, WorldModel world,
                                       ImageStore imageStore)
   {
      if (properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES)
      {
         Point pt = new Point(
                 Integer.parseInt(properties[Obstacle.OBSTACLE_COL]),
                 Integer.parseInt(properties[Obstacle.OBSTACLE_ROW]));
         Obstacle obstacle = new Obstacle(properties[Obstacle.OBSTACLE_ID],
                 pt, imageStore.getImageList(imageStore, Obstacle.OBSTACLE_KEY),0);
         world.tryAddEntity( obstacle);
      }

      return properties.length == Obstacle.OBSTACLE_NUM_PROPERTIES;
   }

   public static final String BGND_KEY = "background";
   public static final int BGND_NUM_PROPERTIES = 4;
   public static final int BGND_ID = 1;
   public static final int BGND_COL = 2;
   public static final int BGND_ROW = 3;

   public static final int COLOR_MASK = 0xffffff;
   public static final int KEYED_IMAGE_MIN = 5;
   public static final int PROPERTY_KEY = 0;

   public static void setStart(boolean start) {
      VirtualWorld.start = start;
   }


}