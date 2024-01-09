package app;


import java.awt.Graphics;

import m.ecs.*;
import m.ecs.components.*;
import m.ecs.systems.*;
import m.engine.*;
import m.exec.*;

// run creates a new instance of app.launch, so file structure is enforced

public class launch extends scene{

    boolean[] keys = scene.keys; // can do this technically. use keyDown(keyCode) or keyDown(keyChar) function

    // ecs
    ECSManager world = new ECSManager();
    colliderSystem colliderSystem = new colliderSystem(); // reference collider system for collision methods, nothing similar exists with other systems so just create new ones below

    audio audio = new audio();

    entity plane = new entity(0);
    entity square = new entity(1, "square");

    int speed = 10;

    @Override
    public void init(){
        gameManager.setWindowDimensions(500,500);
        gameManager.setWindowTitle("game");

        plane.addComponent(new transformComponent(50, 50));
        plane.addComponent(new renderComponent("app/resources/plane.png"));
        plane.addComponent(new colliderComponent(25, 25, 2, 1));
        world.addEntity(plane);

        square.addComponent(new transformComponent(100, 50));
        square.addComponent(new renderComponent("app/resources/plane.png"));
        square.addComponent(new colliderComponent(25, 25, 20, 1));
        world.addEntity(square);

        // ecs
        world.addSystem(new renderSystem());
        world.addSystem(colliderSystem);
        world.addSystem(new physicsSystem());

        world.init();
    }

    
    @Override
    public void update(int dt){

        transformComponent t = plane.getComponent(transformComponent.class);

        if(scene.keyDown('w')){
            t.y-=speed;
        }else if(scene.keyDown('s')){
            t.y+=speed;
        }
        if(scene.keyDown('a')){
            t.x-=speed;
        }else if(scene.keyDown('d')){
            t.x+=speed;
        }
        if(scene.keyDown('q')){
            t.r-=Math.PI/8;
        }else if(scene.keyDown('e')){
            t.r+=Math.PI/8;
        }
        if(keys[88]){ // alternative to keyDown, initialize keys above
            audio.setClip("app/resources/shot.wav");
            audio.playClip();
            // instantiate new entity into scene
            entity bullet = new entity(world.entities.size(), "bullet"); 
            transformComponent pt = plane.getComponent(transformComponent.class);
            bullet.addComponent(new transformComponent((int)pt.x, (int)pt.y));
            bullet.addComponent(new renderComponent("app/resources/bullet.png"));
            bullet.addComponent(new colliderComponent(5, 5, 15, 15));
            float vx = (float)Math.cos(pt.r - Math.PI/2) * 10;
            float vy = (float)Math.sin(pt.r - Math.PI/2) * 10;
            bullet.getComponent(transformComponent.class).r = (float)(pt.r); // rotation so rendering can be accurate

            bullet.addComponent(new physicsComponent(new float[] {vx,vy}, 1, 1, 10));
            world.addEntity(bullet);
        }
        if(scene.keyDown('m')){
            gameManager.loadScene(new launch());
        }
        if(scene.keyDown('n')){
            gameManager.loadScene(new examplescene2());
        }
        if(scene.keyDown('p')){
            gameManager.loadScene(new examplescene3());
        }
        
        if(colliderSystem.AABBisColliding(square, "bullet", world)){
            world.removeEntity(square);
            
        }
        gameManager.writeToLog(Boolean.toString(colliderSystem.AABBisColliding(square.getTag(), "bullet",world)));

        if(world.getTaggedEntities("bullet") != null && world.getTaggedEntities("bullet").size() > 0){
            for(entity e : world.getTaggedEntities("bullet")){
                transformComponent et = e.getComponent(transformComponent.class);
                if(et.x < 0 || et.x > 400 || et.y < 0 || et.y > 400){
                    world.entities.remove(e);
                }
            }
        }

        world.update();
    }

    @Override
    public void draw(Graphics g){
        world.draw(g);
    }
}
