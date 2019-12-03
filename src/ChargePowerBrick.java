import processing.core.PImage;

import java.util.List;

public class ChargePowerBrick extends ChargerParts {
    public ChargePowerBrick(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public void partTaken(){
        super.addPart();
    }

}
