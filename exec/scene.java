package exec;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class scene extends JFrame implements KeyListener{

    public static boolean[] keys = new boolean[100];
    // keys size is arbitrary, check https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list if u want more

    /*public scene(){
        init();
    }

    public void init(){
        return;
    }*/

    public void update(){
        return;
    }

    public void draw(Graphics g){
        return;
    }

    public static boolean keyDown(int keyCode){
        return keys[keyCode];
    }


    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());

        if(!keys[e.getKeyCode()]){
            keys[e.getKeyCode()] = true;        
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(keys[e.getKeyCode()]){
            keys[e.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        return;
    }
    
}
