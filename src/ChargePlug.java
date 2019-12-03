import processing.core.PImage;

import java.util.List;

public class ChargePlug extends ChargerParts {
    public static final String PLUG_ID = "Plug";
    public static final int PLUG_NUM_PROPERTIES = 4;
    public ChargePlug(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }


}
