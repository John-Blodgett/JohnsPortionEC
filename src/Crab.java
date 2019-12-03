import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Crab extends MoveEntity{

    public static final String CRAB_KEY = "crab";
    public static final String CRAB_ID_SUFFIX = " -- crab";
    public static final int CRAB_PERIOD_SCALE = 4;
    public static final int CRAB_ANIMATION_MIN = 50;
    public static final int CRAB_ANIMATION_MAX = 150;

    public Crab( String id, Point position, int actionPeriod, int animationPeriod,List<PImage> images)
    {

        super(id,position,images,0, actionPeriod,animationPeriod);
    }
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> crabTarget = world.findNearest(world,
                getPosition(), Sgrass.class);
        long nextPeriod = getActionPeriod();

        if (crabTarget.isPresent()) {
            Point tgtPos = crabTarget.get().getPosition();

            if (moveTo(world, crabTarget.get(), scheduler)) {
                Quake quake = new Quake(Quake.QUAKE_ID,tgtPos,
                        imageStore.getImageList(imageStore, Quake.QUAKE_KEY));

                world.addEntity( quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(scheduler, world, imageStore);
            }
        }
        scheduler.scheduleEvent(this, new Activity(this,world, imageStore,scheduler),
                nextPeriod);
    }

    private boolean moveTo( WorldModel world,
                                     Entity target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
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
