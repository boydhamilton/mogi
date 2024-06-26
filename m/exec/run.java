package m.exec;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import m.engine.scene;


class Surface extends JPanel {
    public scene currentScene;

    public Surface(){
        gameManager.deleteLog();
        currentScene = new app.launch(); // enforced folder structure
        currentScene.init();
        repaint();
        gameManager.createLog();
    }
    public void setScene(scene newScene){
        this.currentScene = newScene;
        currentScene.init();
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        currentScene.draw(g); // draw scene
    }
}


public class run{
    public static int deltaTime = 34; //ms
    public static double[] frameSize=new double[2];
    public static Surface surface = new Surface();

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(gameManager.windowTitle);

            // set frame
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(surface);
            frame.setResizable(gameManager.windowIsResizeable);
            frame.setSize(gameManager.windowWidth, gameManager.windowHeight);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addKeyListener(surface.currentScene);

            // get frame values
            frameSize[0]=frame.getSize().getWidth();
            frameSize[1]=frame.getSize().getHeight();

            // update scene
            Timer timer = new Timer(deltaTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    surface.currentScene.update(deltaTime);
                    surface.repaint();
                }
            });
            timer.start();
        });
    }

}