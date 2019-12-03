import processing.core.PImage;

import java.util.List;

public class ChargePlug extends ChargerParts {
    public ChargePlug(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public void partTaken(){
        super.addPart();
    }

}
