package app;


import java.awt.Graphics;

import ecs.*;
import exec.*;
import components.*;
import systems.*;

// run creates a new instance of app.launch, so file structure is enforced

public class launch extends scene{

    boolean[] keys = scene.keys; // can do this technically. use keyDown(keyCode) function

    // ecs
    ecs.manager world = new ecs.manager();
    renderSystem renderSystem = new renderSystem();
    colliderSystem colliderSystem = new colliderSystem();
    physicsSystem physicsSystem = new physicsSystem();

    entity plane = new entity(0);
    entity square = new entity(1, "square");

    public launch(){
        plane.addComponent(new transformComponent(50, 50));
        plane.addComponent(new renderComponent("app/plane.png"));
        plane.addComponent(new colliderComponent(25, 25, 2, 1));
        world.addEntity(plane);

        square.addComponent(new transformComponent(100, 50));
        square.addComponent(new renderComponent("app/plane.png"));
        square.addComponent(new colliderComponent(25, 25, 20, 1));
        world.addEntity(square);

        // ecs
        world.addSystem(renderSystem);
        world.addSystem(colliderSystem);
        world.addSystem(physicsSystem);

        world.init();
    }

    int speed = 10;
    @Override
    public void update(){

        transformComponent t = plane.getComponent(transformComponent.class);

        if(scene.keyDown(87)){ // w and s are weird, tested with other buttons and it worked fine
            t.y-=speed;
        }else if(scene.keyDown(83)){
            t.y+=speed;
        }
        if(scene.keyDown(65)){
            t.x-=speed;
        }else if(scene.keyDown(68)){
            t.x+=speed;
        }
        if(scene.keyDown(81)){
            t.d-=Math.PI/8;
        }else if(scene.keyDown(69)){
            t.d+=Math.PI/8;
        }
        if(keys[88]){ // alternative to keyDown, initialize above
            entity bullet = new entity(world.entities.size(), "bullet");
            transformComponent pt = plane.getComponent(transformComponent.class);
            bullet.addComponent(new transformComponent((int)pt.x, (int)pt.y));
            bullet.addComponent(new renderComponent("app/plane.png"));
            bullet.addComponent(new colliderComponent(5, 5, 15, 15));

            float vx = (float)Math.cos(pt.d - Math.PI/2) * 10;
            float vy = (float)Math.sin(pt.d - Math.PI/2) * 10;

            bullet.addComponent(new physicsComponent(new float[] {vx,vy}, 1, 1, 10));
            world.addEntity(bullet);
        }
        if(scene.keyDown(77)){
            exec.manager.switchScene(run.surface, new examplescene2());
        }


        if(colliderSystem.AABBisColliding(square.tag, "bullet", world)){
            world.removeEntity(square);
        }

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