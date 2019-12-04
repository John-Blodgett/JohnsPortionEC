import processing.core.PImage;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Hatalsky extends AnimationEntity{
    private int charger_part_count = 0;
    private static Hatalsky hatalsky;
    public static final String HATALSKY_ID = "hatalsky";
    public static final int HATALSKY_NUM_PROPERTIES = 4;
    private static List<ChargerParts> chargerParts = new ArrayList<>();
    private Hatalsky(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                     int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
        this.charger_part_count = charger_part_count;
    }
    private static Hatalsky setInstance(ImageStore imageStore, Point p, EventScheduler scheduler, WorldModel world){
        if (hatalsky == null){
            hatalsky = new Hatalsky(HATALSKY_ID, p, imageStore.getImageList(imageStore, HATALSKY_ID),0 ,0,255);
        }
        hatalsky.scheduleActions(scheduler,world,imageStore);
        return hatalsky;
    }

    public static Hatalsky getInstance(){return hatalsky;}

    public static Hatalsky getHatalsky(ImageStore imageStore, Point p, EventScheduler scheduler, WorldModel world) {
        StudentFactory.addStudent(world, imageStore, scheduler);
        return setInstance(imageStore, p, scheduler, world);

    }

    public boolean moveTo(WorldModel world,
                          int dx, int dy, EventScheduler scheduler)
    {
        Point nextPoint = new Point(this.getPosition().x + dx, this.getPosition().y + dy);
        if (world.withinBounds(nextPoint) && !world.isOccupied(nextPoint))
        {
            for (ChargerParts i : chargerParts)
            {
                if (Point.adjacent(nextPoint, i.getPosition()))
                {
                    i.addPart(world);
                }
            }
            world.moveEntity(this, nextPoint);
            return true;
        }
        return false;

    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {scheduler.scheduleEvent(this, new Animation(this, world, imageStore,scheduler, 0),
            getAnimationPeriod());}

    public static void addChargerParts(ChargerParts chargerPart){ chargerParts.add(chargerPart); }

    public static void hitByStident()
    {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                VirtualWorld.setStart(false); }
                },5*1000 );
        VirtualWorld.setStart(true);
    }
}