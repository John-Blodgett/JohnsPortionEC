import java.io.File;
import java.io.IOException;
import java.util.Timer;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
public class AudioPlayer {
    Clip clip;
//    private static String filePath;// = "audioClips/test.wav";
    private AudioInputStream audioInputStream;
    public AudioPlayer(String filePath)
            throws UnsupportedAudioFileException,
            IOException, LineUnavailableException
    {
        // create AudioInputStream object
        audioInputStream =
                AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());

        // create clip reference
        clip = AudioSystem.getClip();

        // open audioInputStream to the clip
        clip.open(audioInputStream);

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void playSample(String file){
        try{
            clip.start();
            System.out.print("start");
            Timer timer = new Timer();
            timer.wait(1000);
            clip.close();
        }
        catch (Exception e){
            System.out.print(e);
        }
    }
}