import processing.core.PImage;
import java.util.List;

public class MadStudent extends MoveEntity implements Student {
    public MadStudent(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                     int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
    }
}
