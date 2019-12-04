import processing.core.PImage;

import java.util.List;

public class Battery2 extends Entity{
    public static final String BATTERY2_ID = "battery2";
    public Battery2(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public Battery3 changeBattery(WorldModel world , ImageStore imageStore){
        world.removeEntity(this);
        Battery3 newBattery = new Battery3(Battery3.BATTERY3_ID, new Point(23, 0), imageStore.getImageList(imageStore, Battery3.BATTERY3_ID), 0);
        world.addEntity(newBattery);
        return newBattery;
    }
}
