import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class scene extends JFrame implements KeyListener{
    // object creation
    public static rectangle obj;

    // init
    public scene() {
        obj = new rectangle();
        obj.init();
    }

    // update
    public void update() {
        obj.update();
    }

    // draw
    public void draw(Graphics g) {
        obj.draw(g);
    }

    //input
    @Override
    public void keyPressed(KeyEvent e) {
        
        int currentx = obj.getposition()[0];
        int currenty = obj.getposition()[1];
        int currentw = obj.getsize()[0];
        int currenth = obj.getsize()[1];

        char key = e.getKeyChar();
        switch(key){
            case 'w':
                obj.setposition(currentx,currenty - currenth);
                break;
            case 'a':
                obj.setposition(currentx - currentw,currenty);
                break;
            case 's':
                obj.setposition(currentx,currenty + currenth);
                break;
            case 'd':
                obj.setposition(currentx + currentw,currenty);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        return;
    }

    @Override
    public void keyTyped(KeyEvent e) { //super duper useless unless you start throwing in dumb unicode characters
        return;
    }
}
