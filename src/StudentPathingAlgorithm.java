import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentPathingAlgorithm implements PathingStrategy {
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {

        List<Point> path = new LinkedList<>();
        List<Point> potentialDirections = new LinkedList<>();
        Point curPoint = start, lastPoint = start;
        Point direction;
        while (!curPoint.equals(end)) {
            potentialDirections.clear();
            direction = directionFunc(curPoint, end);
            Point finalCurPoint = lastPoint;
            potentialNeighbors.apply(curPoint).filter(canPassThrough).filter(point -> !point.equals(finalCurPoint)).forEach(potentialDirections::add);
            if ((!potentialDirections.contains(curPoint.translate(direction))))
            {
                lastPoint = curPoint;
                curPoint = potentialDirections.get(0);
                direction = directionFunc(curPoint, end);
                potentialDirections.clear();
                Point finalCurPoint1 = finalCurPoint;
                potentialNeighbors.apply(curPoint).filter(canPassThrough).filter(point -> point!= finalCurPoint1).forEach(potentialDirections::add);
            }
            while (potentialDirections.contains(curPoint.translate(direction)))
            {
                if (curPoint.equals(end))
                {break;}
                lastPoint = curPoint;
                curPoint = curPoint.translate(direction);
                path.add(curPoint);
                potentialDirections.clear();
                potentialNeighbors.apply(curPoint).filter(canPassThrough).forEach(potentialDirections::add);
            }
        }
        return path;
    }

    private Point directionFunc(Point start, Point end)
    {
        if (Math.abs(start.getX() - end.getX()) < Math.abs(start.getY() - end.getY()))
        {
            if (start.getX() - end.getX() < 0)
            {
                return new Point(1,0);
            }
            else  if (start.getX() - end.getX() > 0)
            {
                return new Point(-1, 0);
            }
            else if (start.getY() - end.getY() < 0)
            {
                return new Point(0,1);
            }
            else
            {
                return new Point(0,-1);
            }
        }
        else
        {
            if (start.getY() - end.getY() < 0)
            {
                return new Point(0,1);
            }
            else if (start.getY() - end.getY() > 0)
            {
                return new Point(0,-1);
            }
            else if (start.getX() - end.getX() < 0)
            {
                return new Point(1,0);
            }
            else
            {
                return new Point(-1, 0);
            }

        }
    }
}
