import processing.core.PImage;

import java.util.List;

public class Fish extends ActiveEntity{
    public static final String FISH_KEY = "fish";
    public static final int FISH_NUM_PROPERTIES = 5;
    public static final int FISH_ID = 1;
    public static final int FISH_COL = 2;
    public static final int FISH_ROW = 3;
    public static final int FISH_ACTION_PERIOD = 4;

    public static final String FISH_ID_PREFIX = "fish -- ";
    public static final int FISH_CORRUPT_MIN = 20000;
    public static final int FISH_CORRUPT_MAX = 30000;
    public static final int FISH_REACH = 1;

    public Fish(String id, Point position, int actionPeriod, List<PImage> images)
    {
        super(id,position,images,0,actionPeriod);
    }

    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Point pos = getPosition();  // store current position before removing

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        Crab crab = new Crab(this.id+Crab.CRAB_ID_SUFFIX,pos,getActionPeriod() / Crab.CRAB_PERIOD_SCALE,Crab.CRAB_ANIMATION_MIN +
                      rand.nextInt(Crab.CRAB_ANIMATION_MAX - Crab.CRAB_ANIMATION_MIN),
              imageStore.getImageList(imageStore, Crab.CRAB_KEY) );

        world.addEntity(crab);
        crab.scheduleActions(scheduler, world, imageStore);
    }

}
