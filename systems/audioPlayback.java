package systems;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class audioPlayback implements LineListener{

    boolean clipIsPlaying = false;
    public void playSound(String src){ // small clips
        File audioFile = new File(src);
        try{
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);
 
            audioClip.addLineListener(this);
            audioClip.open(audioStream);
            audioClip.start();

            if(audioClip.getMicrosecondLength() <= audioClip.getMicrosecondPosition()){
                System.out.println("ad"); // never called, never closed
                audioClip.close();
                audioStream.close();
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void update(LineEvent event) {
        return;
    }
}
