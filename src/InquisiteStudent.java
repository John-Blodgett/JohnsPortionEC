import processing.core.PImage;
import java.util.List;

public class InquisiteStudent extends MoveEntity implements Student {
    public InquisiteStudent(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                   int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
    }
    public void playAudio(){
        try{
            AudioPlayer player = new AudioPlayer("AudioClips/inquisiteSample.wav");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
