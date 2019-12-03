import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Octo_Full extends MoveEntity{

    public static final String OCTO_KEY = "octo";
    public static final int OCTO_NUM_PROPERTIES = 7;
    public static final int OCTO_ID = 1;
    public static final int OCTO_COL = 2;
    public static final int OCTO_ROW = 3;
    public static final int OCTO_LIMIT = 4;
    public static final int OCTO_ACTION_PERIOD = 5;
    public static final int OCTO_ANIMATION_PERIOD = 6;

    private int resourceLimit;
    private int resourceCount;

    public Octo_Full(String id, int resourceLimit,
                     Point position, int actionPeriod, int animationPeriod,
                     List<PImage> images)
    {
        super(id, position, images, 0, actionPeriod,animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }
    public void executeActivity(WorldModel world,
                                        ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<Entity> fullTarget = world.findNearest(world, getPosition(),
                Atlantis.class);

        Atlantis atlantis = (Atlantis)fullTarget.get();

        if (fullTarget.isPresent() &&
                moveTo(world, fullTarget.get(), scheduler))
        {
            //at atlantis trigger animation
            atlantis.scheduleActions(scheduler,world, imageStore);

            //transform to unfull
            transform( world, scheduler, imageStore);
        }
        else
        {
            Activity activity = new Activity(this ,world,imageStore, scheduler);
            scheduler.scheduleEvent(this, activity, OCTO_ACTION_PERIOD);
        }
    }

    private void transform(WorldModel world,
                                     EventScheduler scheduler, ImageStore imageStore)
    {
        Octo_Not_Full octo = new Octo_Not_Full(id, resourceLimit,
                getPosition(), getActionPeriod(), getAnimationPeriod(),
                getImages());

        world.removeEntity( this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity( octo);
        octo.scheduleActions(scheduler, world, imageStore);
    }
    private boolean moveTo( WorldModel world,
                                     Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            return true;
        }
        else
        {
            Point nextPos = nextPosition( world, target.getPosition());

            if (!getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant( nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity( this, nextPos);
            }
            return false;
        }
    }

}
