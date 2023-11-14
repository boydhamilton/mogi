import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ecs.*;
import components.*;
import systems.*;

// really just whipped this up to test everything, it is beyond jank lol

public class scene extends JFrame implements KeyListener{

    // to make input access global need to do key states
    public char keyDown;
    public char keyUp;

    // ecs
    manager ECSmanager = new manager();
    renderSystem renderSystem = new renderSystem();
    colliderSystem colliderSystem = new colliderSystem();
    physicsSystem physicsSystem = new physicsSystem();

    // enities
    entity[] entities = new entity[500]; // if you need more entities than that per scene you are doing too big a project lol. should half it tbh
    entity box = new entity(0);
    entity box2 = new entity(1);


    // init
    public scene() {

        // box
        box.addComponent(new transformComponent(10, 10));
        box.addComponent(new renderComponent(10,10, Color.RED));
        box.addComponent(new colliderComponent(10, 10));
        box.addComponent(new physicsComponent(new float[] {0,0}, 1.6f, 1.8f, 5));
        ECSmanager.addEntity(box);

        // box2
        box2.addComponent(new transformComponent(50, 15));
        box2.addComponent(new renderComponent(10, 10, Color.BLUE));
        box2.addComponent(new colliderComponent(10, 10));
        ECSmanager.addEntity(box2);

        // ecs
        ECSmanager.addSystem(renderSystem);
        ECSmanager.addSystem(colliderSystem);
        ECSmanager.addSystem(physicsSystem);

        ECSmanager.init();
    }

    // update
    public void update() {

        // transformComponent t = box.getComponent(transformComponent.class);
        physicsComponent p = box.getComponent(physicsComponent.class);

        // need to resolve collision with respect to direction, maybe a collisionsystem thing?
        if(keyDown=='a'){
            p.velocity[0] = -1;
        }else if(keyDown=='d'){
            p.velocity[0] = 1;
        }else if(keyDown=='s'){
           p.velocity[1] = 1;
        }else if(keyDown=='w'){
             p.velocity[1] = -1;
        }
        if(keyUp=='a' || keyUp=='d'){
            p.velocity[0]=0;
        }
        if(keyUp=='w' || keyUp=='s'){
            p.velocity[1]=0;
        }

        
        if(keyDown=='x'){
            entity bullet = new entity(2);
            entities[bullet.getID()] = bullet;
            bullet.addComponent(new renderComponent(5, 5, java.awt.Color.black));
            bullet.addComponent(new transformComponent(50, 100));
            bullet.addComponent(new colliderComponent(5, 5));
            ECSmanager.addEntity(bullet);
        }
        if(entities[2]!=null){
            if(colliderSystem.AABBisColliding(box, entities[2])){
                p.velocity[0] = 5;
            }
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
