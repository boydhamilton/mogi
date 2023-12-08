package app;


import java.awt.Graphics;

import exec.*;
import ecs.*;
import ecs.manager;
import components.*;
import systems.*;

// TODO: Write about systems in readme, could do a quick thing about creating entity objects?

public class examplescene2 extends scene{
    public ecs.manager world = new manager();

    entity plane;

    @Override
    public void init(){


        plane = new app.objects.plane(0, "Plane");
        world.addEntity(plane);

        world.addSystem(new colliderSystem());
        world.addSystem(new renderSystem());
    }

    @Override
    public void update(){
        if(scene.keyDown('s')){
            plane.getComponent(transformComponent.class).y -= 1;
        }

        world.update();
    }

    @Override
    public void draw(Graphics g){
        world.draw(g);
    }
}
