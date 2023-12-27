package m.engine;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import m.exec.*;

public class scene extends JFrame implements KeyListener{

    public static boolean[] keys = new boolean[100];
    // keys size is arbitrary, check https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list if u want more

    /*public scene(){
        init();
    }

    public void init(){
        return;
    }*/

    public void init(){
        return;
    }

    public void update(){
        return;
    }

    public void draw(Graphics g){
        return;
    }

    public static boolean keyDown(int keyCode){
        return keys[keyCode];
    }
    public static boolean keyDown(char keyCode){
        return keys[charToKeyCode(keyCode)];
    }
    // good to have? idk
    public static int charToKeyCode(char c){
        return java.awt.event.KeyEvent.getExtendedKeyCodeForChar(c);
    }


    @Override
    public void keyPressed(KeyEvent e) {
        gameManager.writeToLog(e.getKeyChar() + " " + Integer.toString(e.getKeyCode())); // could remove

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
