// run file

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

class Surface extends JPanel {
    public scene gameScene;

    public Surface(){
        gameScene = new scene();
    }
    

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        gameScene.draw(g); // draw scene
    }
}

public class run{
    static int deltaTime = 34; //ms

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("jordan barret mogs francisco lachowski");

            // set frame
            Surface surface = new Surface();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(surface);
            frame.setSize(625, 625);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.addKeyListener(surface.gameScene);

            // update scene
            Timer timer = new Timer(deltaTime, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    surface.gameScene.update();
                    surface.repaint();
                }
            });
            timer.start();
        });
    }

}