package exec;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;


class Surface extends JPanel {
    public scene currentScene;

    public Surface(){
        currentScene = new app.launch(); // need to call main.java from parent
        //currentScene.init();
        repaint();
    }
    public void setScene(scene newScene){
        this.currentScene = newScene;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        currentScene.draw(g); // draw scene
    }
}


public class run{
    public static final int deltaTime = 34; //ms
    public static Surface surface = new Surface();


    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(manager.windowTitle);

            // set frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(surface);
            frame.setSize(manager.windowWidth, manager.windowHeight);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addKeyListener(surface.currentScene);

            // update scene
            Timer timer = new Timer(deltaTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    surface.currentScene.update();
                    surface.repaint();
                }
            });
            timer.start();
        });
    }

}