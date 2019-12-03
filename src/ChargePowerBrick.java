import processing.core.PImage;

import java.util.List;

public class ChargePowerBrick extends ChargerParts {
    public static final String POWER_BRICK_ID = "PowerBrick";
    public static final int POWER_BRICK_NUM_PROPERTIES = 4;
    public ChargePowerBrick(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }

}
