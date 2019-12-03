import processing.core.PImage;

import java.util.List;

public class Hatalsky extends MoveEntity{
    private int charger_part_count = 0;
    public static Hatalsky hatalsky;
    public static final String HATALSKY_ID = "hatalsky";
    public static final int HATALSKY_NUM_PROPERTIES = 4;
    private Hatalsky(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                     int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
        this.charger_part_count = charger_part_count;
    }
    private static Hatalsky setInstance(ImageStore imageStore, Point p){
        if (hatalsky == null){
            hatalsky = new Hatalsky(HATALSKY_ID, p, imageStore.getImageList(imageStore, HATALSKY_ID),0 ,0,0);
        }
        return hatalsky;
    }

    public static Hatalsky getInstance(){return hatalsky;}

    public static Hatalsky getHatalsky(ImageStore imageStore, Point p) {
        return setInstance(imageStore, p);
    }

    public boolean moveTo(WorldModel world,
                          int dx, int dy, EventScheduler scheduler)
    {
        Point nextPoint = new Point(this.getPosition().x + dx, this.getPosition().y + dy);
        if (world.withinBounds(nextPoint) && !world.isOccupied(nextPoint))
        {
            world.moveEntity(this, nextPoint);
            return true;
        }
        return false;

    }
}