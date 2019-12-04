import processing.core.PImage;

import java.util.List;

public class Battery1 extends Entity{
    public static final String BATTERY1_ID = "battery1";
    public static final int BATTERY1_NUM_PROP = 4;
    public Battery1(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public Battery2 changeBattery(WorldModel world , ImageStore imageStore){
        world.removeEntity(this);
        Battery2 newBattery = new Battery2(Battery2.BATTERY2_ID, new Point(23, 0), imageStore.getImageList(imageStore, Battery2.BATTERY2_ID), 0);
        world.addEntity(newBattery);
        return newBattery;
    }
}
