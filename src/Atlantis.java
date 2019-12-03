import processing.core.PImage;

import java.util.List;

public class Atlantis extends AnimationEntity{
    public static final String ATLANTIS_KEY = "atlantis";
    public static final int ATLANTIS_NUM_PROPERTIES = 4;
    public static final int ATLANTIS_ID = 1;
    public static final int ATLANTIS_COL = 2;
    public static final int ATLANTIS_ROW = 3;
    public static final int ATLANTIS_ANIMATION_PERIOD = 70;
    public static final int ATLANTIS_ANIMATION_REPEAT_COUNT = 7;

    public Atlantis(String id, Point position, List<PImage> images)
    {

        super(id,position,images,0, 0,ATLANTIS_ANIMATION_PERIOD);
    }

    public void scheduleActions(EventScheduler scheduler,WorldModel world, ImageStore imageStore ){
        scheduler.scheduleEvent(this,
                new Animation(this, world, imageStore, scheduler, ATLANTIS_ANIMATION_REPEAT_COUNT),
                ATLANTIS_ANIMATION_PERIOD);
    }


}
