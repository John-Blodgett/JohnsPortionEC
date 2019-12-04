import processing.core.PImage;
import java.util.List;

public class AnnoyingStudent extends MoveEntity implements Student {
    public AnnoyingStudent(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod,
                   int animationPeriod){
        super(id,position,images,imageIndex, actionPeriod,animationPeriod);
    }
    public void playAudio(){
        try{
            AudioPlayer player = new AudioPlayer("AudioClips/annoyingSample.wav");
        }
        catch (Exception e){
            System.out.println(e);
        }
    }
}
