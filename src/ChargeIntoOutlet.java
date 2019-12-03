import processing.core.PImage;

import java.util.List;

public class ChargeIntoOutlet extends ChargerParts {
    public ChargeIntoOutlet(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }
    public void partTaken(){
        super.addPart();
    }

}
