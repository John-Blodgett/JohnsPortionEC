import processing.core.PImage;

import java.util.List;
import java.util.Random;

abstract class ActiveEntity extends Entity{
    private int actionPeriod;
    public static final Random rand = new Random();

    public ActiveEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod){
        super(id,position,images,imageIndex);
        this.actionPeriod = actionPeriod;
    }

    abstract void executeActivity(WorldModel world,
                         ImageStore imageStore, EventScheduler scheduler);

    void scheduleActions( EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
            new Activity(this, world, imageStore, scheduler), actionPeriod);
    }

    int getActionPeriod(){return this.actionPeriod;}


}
