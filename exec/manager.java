package exec;

public class manager {

    // FRAME VARIABLES
    public static int windowWidth = 500;
    public static int windowHeight = 500;
    public static String windowTitle = "mogi Project Window";


    public static void switchScene(Surface surface, scene s){
        surface.setScene(s);
    }

    public void setFrameDimensions(int w, int h){
        manager.windowWidth = w;
        manager.windowHeight = h;
    }
    public void setWindowwindowTitle(String windowTitle){
        manager.windowTitle = windowTitle;
    }
}
