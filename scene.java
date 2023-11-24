import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import ecs.*;
import components.*;
import systems.*;

public class scene extends JFrame implements KeyListener{

    // ecs
    manager world = new manager();
    renderSystem renderSystem = new renderSystem();
    colliderSystem colliderSystem = new colliderSystem();
    physicsSystem physicsSystem = new physicsSystem();

    entity plane = new entity(0);
    entity square = new entity(1, "square");

    boolean[] keys = new boolean[100]; // arbitrary, check https://stackoverflow.com/questions/15313469/java-keyboard-keycodes-list if u want more

    // init
    public scene() {
        plane.addComponent(new transformComponent(50, 50));
        plane.addComponent(new renderComponent("plane.png"));
        plane.addComponent(new colliderComponent(25, 25, 2, 1));
        world.addEntity(plane);

        square.addComponent(new transformComponent(100, 50));
        square.addComponent(new renderComponent("plane.png"));
        square.addComponent(new colliderComponent(25, 25, 20, 1));
        world.addEntity(square);

        // ecs
        world.addSystem(renderSystem);
        world.addSystem(colliderSystem);
        world.addSystem(physicsSystem);

        world.init();
    }

    // update
    public int speed = 10;
    public void update() {

        transformComponent t = plane.getComponent(transformComponent.class);

        if(keys[87]){ // w and s are weird, tested with other buttons and it worked fine
            t.y-=speed;
        }else if(keys[83]){
            t.y+=speed;
        }
        if(keys[65]){
            t.x-=speed;
        }else if(keys[68]){
            t.x+=speed;
        }
        if(keys[81]){
            t.d-=Math.PI/12;
        }else if(keys[69]){
            t.d+=Math.PI/12;
        }
        if(keys[88]){
            entity bullet = new entity(world.entities.size(), "bullet");
            transformComponent pt = plane.getComponent(transformComponent.class);
            bullet.addComponent(new transformComponent((int)pt.x, (int)pt.y));
            bullet.addComponent(new renderComponent("plane.png"));
            bullet.addComponent(new colliderComponent(5, 5, 15, 15));

            float vx = (float)Math.cos(pt.d - Math.PI/2) * 10;
            float vy = (float)Math.sin(pt.d - Math.PI/2) * 10;

            bullet.addComponent(new physicsComponent(new float[] {vx,vy}, 1, 1, 10));
            world.addEntity(bullet);
        }


        if(colliderSystem.AABBisColliding(square.tag, "bullet", world)){
            world.removeEntity(square);
        }

        world.update();
    }

    // draw
    public void draw(Graphics g) {
        
        world.draw(g);
    }


    // input
    @Override
    public void keyPressed(KeyEvent e) {
        if(!keys[e.getKeyCode()]){
            keys[e.getKeyCode()] = true;
            System.out.println(e.getKeyCode() + " " + (char)e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(keys[e.getKeyCode()]){
            keys[e.getKeyCode()] = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) { //super duper useless unless you start throwing in dumb unicode characters
        return;
    }
}
