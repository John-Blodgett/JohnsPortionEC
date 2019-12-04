import processing.core.PImage;
import java.util.List;
import java.util.Optional;

public class InquisiteStudent extends MoveEntity implements Student {
    PathingStrategy strategy = new StudentPathingAlgorithm();
    public InquisiteStudent(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                   int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
    }

    public boolean moveTo(WorldModel world,
                          Entity target, EventScheduler scheduler)
    {
        Point nextPos = nextPosition(world, target.getPosition());

        if (Point.adjacent(this.getPosition(), target.getPosition()))
        {
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);
        }
        else if (!this.getPosition().equals(nextPos)) {
            world.moveEntity(this, nextPos);
        }
        return false;
    }

    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> points = strategy.computePath(this.getPosition(), destPos, p -> !world.isOccupied(p) && world.withinBounds(p)
                , (p1, p2) -> Point.adjacent(p1, p2), PathingStrategy.CARDINAL_NEIGHBORS);


        if (points.size() == 0) {
            return this.getPosition();
        }
        return points.get(0);
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> fullTarget = world.findNearest(world, this.getPosition(),
                Hatalsky.class);
        moveTo(world, fullTarget.get(), scheduler);
        scheduler.scheduleEvent(this, new Activity(this, world, imageStore, scheduler), this.getActionPeriod());

    }
}
