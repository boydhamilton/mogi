package app;

// engine dependancies
import java.awt.Graphics;

// engine
import m.ecs.*;
import m.ecs.systems.*;
import m.ecs.components.*;
import m.engine.*;

// game specific
import java.util.Random;

public class examplescene3 extends scene{
    ECSManager world = new ECSManager();

    renderSystem renderSystem = new renderSystem();
    colliderSystem colliderSystem = new colliderSystem();
    
    int score = 0;

    // player + variabels
    entity player = new entity(0,"Player");
    boolean depressed = false;

    // fruit
    entity fruit = new entity(1, "Fruit");

    @Override
    public void init(){

        // player
        player.addComponent(new transformComponent(50, 50));
        player.addComponent(new renderComponent("app/resources/plane.png"));
        player.addComponent(new colliderComponent(25, 25));
        world.addEntity(player);

        // fruit
        fruit.addComponent(new transformComponent(250, 250));
        fruit.addComponent(new renderComponent("app/resources/bullet.png"));
        fruit.addComponent(new colliderComponent(25, 25));
        world.addEntity(fruit);

        world.addSystem(colliderSystem);
        world.addSystem(renderSystem);

        world.init();
    }

    @Override
    public void update(){
        
        transformComponent playerTransformComponent = player.getComponent(transformComponent.class);
        if(scene.keyDown('w') && !depressed){
            playerTransformComponent.y-=25;
        }else if(scene.keyDown('s') && !depressed){
            playerTransformComponent.y+=25;
        }
        if(scene.keyDown('a') && !depressed){
            playerTransformComponent.x-=25;
        }else if(scene.keyDown('d') && !depressed){
            playerTransformComponent.x+=25;
        }
        if(keyDown()){
            depressed=true;
        }else{
            depressed=false;
        }
       
        if(colliderSystem.AABBisColliding(player, fruit)){
            transformComponent fruitTransformComponent = fruit.getComponent(transformComponent.class);
            Random rand = new Random();
            fruitTransformComponent.x = rand.nextInt(400);
            fruitTransformComponent.y = rand.nextInt(400);
            score++;
        }

        world.update();
    }

    @Override
    public void draw(Graphics g){
        g.drawString(Integer.toString(score), 50, 50);
        world.draw(g);
    }
    
}
