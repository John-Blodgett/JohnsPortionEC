import processing.core.PImage;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class MoveEntity extends AnimationEntity{

    public MoveEntity(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                      int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        super.scheduleActions(scheduler, world,imageStore);
        scheduler.scheduleEvent(this, new Animation(this, world, imageStore,scheduler, 0),
                getAnimationPeriod());
    }

    private PathingStrategy strategy = new AStarPathingStrategy();
    //private PathingStrategy strategy = new SingleStepPathingStrategy();
    private static boolean neighbors(Point p1, Point p2)
    {
        return p1.x+1 == p2.x && p1.y == p2.y ||
                p1.x-1 == p2.x && p1.y == p2.y ||
                p1.x == p2.x && p1.y+1 == p2.y ||
                p1.x == p2.x && p1.y-1 == p2.y;
    }
    public Point nextPosition(WorldModel world, Point destPos){
        List<Point> points;
        Point pos = getPosition();
        points = strategy.computePath(getPosition(), destPos,
                p ->  world.withinBounds(p) && !(world.isOccupied(p)),
                (p1, p2) -> neighbors(p1,p2),
                PathingStrategy.CARDINAL_NEIGHBORS);
        if (points.size() == 0)
        {
            return getPosition();
        }

        pos = points.get(0);
        return pos;
    }


}