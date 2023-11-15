import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ecs.*;
import components.*;
import systems.*;

public class scene extends JFrame implements KeyListener{

    // to make input access global need to do key states
    public char keyDown;
    public char keyUp;

    // ecs
    manager ECSmanager = new manager();
    renderSystem renderSystem = new renderSystem();
    colliderSystem colliderSystem = new colliderSystem();

    entity plane = new entity(0);
    entity square = new entity(1);

    // init
    public scene() {
        plane.addComponent(new transformComponent(50, 50));
        plane.addComponent(new renderComponent("plane.png"));
        plane.addComponent(new colliderComponent(25, 25));
        ECSmanager.addEntity(plane);

        square.addComponent(new transformComponent(100, 50));
        square.addComponent(new renderComponent("plane.png"));
        square.addComponent(new colliderComponent(25, 25));
        ECSmanager.addEntity(square);

        // ecs
        ECSmanager.addSystem(renderSystem);
        ECSmanager.addSystem(colliderSystem);

        ECSmanager.init();
    }

    // update
    public void update() {

        transformComponent t=plane.getComponent(transformComponent.class);
        if(keyDown=='a'){
            t.x-=1;
        }else if(keyDown=='d'){
            t.x+=1;
        }
        if(keyDown=='w'){
            t.y-=1;
        }else if(keyDown=='s'){
            t.y+=1;
        }

        if(keyDown=='q'){
            t.d+=0.5;
        }else if(keyDown=='e'){
            t.d-=0.5;
        }

        ECSmanager.update();
    }

    // draw
    public void draw(Graphics g) {
        
        ECSmanager.draw(g);
    }




    // input
    @Override
    public void keyPressed(KeyEvent e) {
        keyDown = e.getKeyChar();

        if(keyDown == keyUp){
            keyUp = '|';
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyUp = e.getKeyChar();

        if(keyUp == keyDown){
            keyDown = '|';
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { //super duper useless unless you start throwing in dumb unicode characters
        return;
    }
}
