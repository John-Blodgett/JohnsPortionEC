import processing.core.PImage;

import java.util.List;

public class Quake extends AnimationEntity{
    public static final String QUAKE_KEY = "quake";
    public static final String QUAKE_ID = "quake";
    public static final int QUAKE_ACTION_PERIOD = 1100;
    public static final int QUAKE_ANIMATION_PERIOD = 100;
    public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

    public Quake( String id, Point position,
                  List<PImage> images)
    {
        super(id,position,images,0, QUAKE_ACTION_PERIOD,QUAKE_ACTION_PERIOD);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                new Activity(this, world, imageStore, scheduler),
                getActionPeriod());
        scheduler.scheduleEvent(this, new Animation(this, world, imageStore,scheduler, QUAKE_ANIMATION_REPEAT_COUNT),
                getActionPeriod());
    }

}
