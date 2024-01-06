package app;

import java.awt.Graphics;
import java.util.stream.IntStream;

import javax.swing.text.html.parser.Entity;

import m.ecs.*;
import m.ecs.components.*;
import m.ecs.systems.*;
import m.engine.*;
import m.exec.*;


public class examplescene2 extends scene{

    entity plane = new app.objects.plane(0, "plane"); // object oriented woohoo
    int speed = 10;
    entity[] enemy_planes = new entity[]{
        new app.objects.plane(1, "bad"),
        new app.objects.plane(2, "bad"),
        new app.objects.plane(3, "bad")};

    ECSManager ECSManager = new ECSManager();
    colliderSystem colliderSystem = new colliderSystem();
    renderSystem renderSystem = new renderSystem();
    audio audio = new audio();
    
    @Override
    public void init(){
        ECSManager.addEntity(plane);

        for(int i=0; i<enemy_planes.length; i++){
            entity e = enemy_planes[i];
            ECSManager.addEntity(e);
            e.getComponent(transformComponent.class).x = 100*i;
        }
        
        ECSManager.addSystem(colliderSystem);
        ECSManager.addSystem(renderSystem);
        ECSManager.addSystem(new physicsSystem());

        
        
        ECSManager.init();
    }

    @Override
    public void update(){
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
            entity bullet = new entity(ECSManager.entities.size(), "bullet"); 
            transformComponent pt = plane.getComponent(transformComponent.class);
            bullet.addComponent(new transformComponent((int)pt.x, (int)pt.y));
            bullet.addComponent(new renderComponent("app/resources/bullet.png"));
            bullet.addComponent(new colliderComponent(5, 5, 15, 15));
            float vx = (float)Math.cos(pt.r - Math.PI/2) * 10;
            float vy = (float)Math.sin(pt.r - Math.PI/2) * 10;
            bullet.getComponent(transformComponent.class).r = (float)(pt.r); // rotation so rendering can be accurate

            bullet.addComponent(new physicsComponent(new float[] {vx,vy}, 1, 1, 10));
            ECSManager.addEntity(bullet);
        }

        if(scene.keyDown('m')){
            gameManager.loadScene(new launch());
        }
        if(scene.keyDown('n')){
            gameManager.loadScene(new examplescene2());
        }

        // lowk should be a way to return the mentioned entity in tagged functions cause now i have to like iterate
        // cause this is not it
        // works but gay as shit
        if(ECSManager.getTaggedEntities("bad")!=null){
            IntStream.range(0,ECSManager.getTaggedEntities("bad").size())
            .parallel()
            .forEach(i -> { 
                entity e = ECSManager.getTaggedEntities("bad").get(i);
                if(colliderSystem.AABBisColliding(e, "bullet", ECSManager)){
                    ECSManager.removeEntity(e);
                }
                // gameManager.writeToLog(Boolean.toString(colliderSystem.AABBisColliding(e.getTag(), "bullet",ECSManager)));
            });
        }
        

        if(ECSManager.getTaggedEntities("bullet") != null && ECSManager.getTaggedEntities("bullet").size() > 0){
            for(entity e : ECSManager.getTaggedEntities("bullet")){
                transformComponent et = e.getComponent(transformComponent.class);
                if(et.x < 0 || et.x > 400 || et.y < 0 || et.y > 400){
                    ECSManager.entities.remove(e);
                }
            }
        }

        ECSManager.update();
    }

    @Override
    public void draw(Graphics g){
        ECSManager.draw(g);
    }
}
