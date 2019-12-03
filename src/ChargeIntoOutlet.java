import processing.core.PImage;

import java.util.List;

public class ChargeIntoOutlet extends ChargerParts {
    public static final String INTO_OUTLET_ID = "IntoOutlet";
    public static final int INTO_OUTLET_NUM_PROPERTIES = 4;
    public ChargeIntoOutlet(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }


}
