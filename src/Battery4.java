import processing.core.PImage;

import java.util.List;

public class Battery4 extends AnimationEntity{
    public static final String BATTERY4_ID = "battery4";
    public Battery4(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }
}
