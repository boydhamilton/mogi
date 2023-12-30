package m.engine;

import java.io.File;
import javax.sound.sampled.*;

public class audio{

    Clip clip;

    public void setClip(String URL){
        File file = new File(URL);
        
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void playClip(){
        clip.start();
    }
    public void stopClip(){
        clip.stop();
    }
    public void loopClip(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

}
