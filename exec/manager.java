package exec;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class manager {

    // FRAME VARIABLES
    public static int windowWidth = 500;
    public static int windowHeight = 500;
    public static String windowTitle = "mogi Project Window";

    public static boolean DEBUG = true;


    // scene
    public static void loadScene(scene scene){
        run.surface.setScene(scene);
    }

    // window
    public static void setWindowDimensions(int w, int h){
        manager.windowWidth = w;
        manager.windowHeight = h;
    }
    public static void setWindowTitle(String windowTitle){
        manager.windowTitle = windowTitle;
    }

    // i remember this was useful for something but idr what. gonna leave it in
    public static int charToKeyCode(char c){
        return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
    }

    // log stuff
    public static void createLog(){
        try{
        File log = new File("exec/log.log");
            if(log.createNewFile()){
                writeToLog("Log created");
            }else{
                writeToLog("Attempted to create log when log already exists");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void deleteLog(){
        File log = new File("exec/log.log");
        if(log.delete()){
            System.out.println("Log cleared");
        }else{
            System.out.println("Log clear failed");
        }
    }

    public static void writeToLog(String line){
        if(DEBUG){
            try{
            FileWriter writer = new FileWriter("exec/log.log", true);
            writer.write('\n');
            writer.write(line + " @" + java.time.LocalTime.now());
            writer.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

}
