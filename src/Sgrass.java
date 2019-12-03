import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Sgrass extends ActiveEntity{
    public static final String SGRASS_KEY = "seaGrass";
    public static final int SGRASS_NUM_PROPERTIES = 5;
    public static final int SGRASS_ID = 1;
    public static final int SGRASS_COL = 2;
    public static final int SGRASS_ROW = 3;
    public static final int SGRASS_ACTION_PERIOD = 4;

    public Sgrass(String id, Point position, List<PImage> images, int actionPeriod)
    {
        super(id,position,images,0, actionPeriod);

    }

    public void executeActivity( WorldModel world,
                                             ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Point> openPt = world.findOpenAround( getPosition());

        if (openPt.isPresent())
        {
            Fish fish = new Fish(Fish.FISH_ID_PREFIX + id,
                    openPt.get(), Fish.FISH_CORRUPT_MIN +
                    rand.nextInt(Fish.FISH_CORRUPT_MAX - Fish.FISH_CORRUPT_MIN),
                    imageStore.getImageList(imageStore,Fish.FISH_KEY));
            world.addEntity( fish);
            fish.scheduleActions(scheduler, world, imageStore);
        }
        Activity activity = new Activity(this, world, imageStore, scheduler);
        scheduler.scheduleEvent(this,
                activity,
                getActionPeriod());
    }
    
}
