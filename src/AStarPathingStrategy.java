import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        HashMap<Point,Node> closedMap = new HashMap<>();
        HashMap<Point,Node> openMap = new HashMap<>();
        Comparator<Node> openComp1 = (n1, n2)->n1.getF()-n2.getF();
        Comparator<Node> openComp2 = (o1,o2)->o2.getG()-o1.getG();
        Comparator<Node> openCompFinal = openComp1.thenComparing(openComp2);
        PriorityQueue<Node> openHeap = new PriorityQueue<>(openCompFinal);

        List<Point> path = new LinkedList<>();
        boolean foundTarget = false;
        Node startNode = new Node(start,0,calculateH(start,end),null);
        Node currNode = startNode;
        openHeap.add(currNode);
        while(!foundTarget && openHeap.size()!=0){
            int firstG = currNode.getG()+1;
            Node prevNode = currNode;
            List<Node> potentialList = potentialNeighbors.apply(currNode.getPos()).filter(canPassThrough)
                    .map(p -> new Node(p, firstG, calculateH(p,end), prevNode)).collect(Collectors.toList());
            for (Node p: potentialList){
                //update G if necessary
                if (p.getG()>calculateG(p.getPos(),start)){
                    p.setG(calculateG(p.getPos(),start));
                }
                //add node to open list if its not already there and not in closed list
                if (!openMap.containsKey(p.getPos()) && !closedMap.containsKey(p.getPos())){
                    openMap.put(p.getPos(),p);
                    openHeap.add(p);
                }
                //check if one of the potentials is the target/ is withinReach of the target
                if (withinReach.test(p.getPos(),end)){
                    //if so then set currNode equal to end and set foundTarget = true
                    currNode = p;
                    foundTarget = true;
                    break;
                }
            }
            closedMap.put(currNode.getPos(),currNode);
            //find new currNode by finding lowest f (tie breaker is greatest g) if target not within reach
            if (!foundTarget){
                boolean foundNext = false;
                while (!foundNext){
                    if (openHeap.size() == 0){
                        return path;
                    }
                    if ((closedMap.containsKey(openHeap.peek().getPos()))){
                        openHeap.remove(openHeap.peek());
                    }
                    else{
                        foundNext = true;
                    }
                }
                currNode = openHeap.peek();
            }
        }
        //make path by backtracing curr node(which is now target)
        Node addedNode = currNode;
        while (addedNode.getPos()!=start){
            path.add(0,addedNode.getPos());
            addedNode = addedNode.getPrev();
        }
        return path;
    }
    private int calculateG(Point p, Point start){
        return (Math.abs(p.x-start.x)+Math.abs(p.y-start.y));
    }
    private int calculateH(Point p, Point end){
        return (Math.abs(p.x-end.x)+Math.abs(p.y-end.y));
    }
}
