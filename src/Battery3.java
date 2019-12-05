import processing.core.PImage;

import java.util.List;

public class Battery3 extends Entity{
    public static final String BATTERY3_ID = "battery3";
    public Battery3(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public Battery4 changeBattery(WorldModel world , ImageStore imageStore, EventScheduler scheduler){
        world.removeEntity(this);
        Battery4 newBattery = new Battery4(Battery4.BATTERY4_ID, new Point(23, 0), imageStore.getImageList(imageStore, Battery4.BATTERY4_ID), 0,992,100);
        world.addEntity(newBattery);
        scheduler.scheduleEvent(newBattery, new Animation(newBattery, world, imageStore,scheduler, 0),
                newBattery.getAnimationPeriod());
        return newBattery;
    }
}
