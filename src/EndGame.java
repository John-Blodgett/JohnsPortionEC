import java.util.TimerTask;

public class EndGame extends TimerTask {
    @Override
    public void run() {
        VirtualWorld.end = true;
    }
}
