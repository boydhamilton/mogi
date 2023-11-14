package systems;

// frame
import java.awt.Graphics;

import java.util.*;
import java.util.stream.IntStream; // parallization

import ecs.*;
import components.colliderComponent;
import components.transformComponent;

public class colliderSystem implements system{
    

    @Override
    public void init(List<entity> entities) {
        return;
    }

    @Override
    public void update(List<entity> entities) {

        // add screen segmentation (only check other entities in that quadrant)
        // nvm i dont need this at all i just need to check a against b for my purposes
        
        IntStream.range(0,entities.size())
            .parallel()
            .forEach(i -> {
                entity e = entities.get(i);

            if(e.hasComponent(colliderComponent.class) && e.hasComponent(transformComponent.class)){
                colliderComponent a = e.getComponent(colliderComponent.class);
                transformComponent at = e.getComponent(transformComponent.class);

                IntStream.range(i+1,entities.size())
                    .parallel()
                    .forEach(j -> {
                        
                        entity eb = entities.get(j);
                        colliderComponent b = eb.getComponent(colliderComponent.class);
                        transformComponent bt = eb.getComponent(transformComponent.class);

                        if( (at.x <= (bt.x+b.w) && (at.x+a.w) >= bt.x) && (at.y <= (bt.y+b.h) && (at.y+a.h) >= bt.y) ){
                            System.out.println(Integer.toString(e.id) + " collided with " + Integer.toString(eb.id) );
                            a.isColliding = true;
                            b.isColliding = true;
                        }
                });
            }
        });
        return;
    }

    @Override
    public void draw(List<entity> entities, Graphics g) {
        return;
    }

    // aabb a against b check
    public boolean AABBisColliding(entity a, entity b){
        colliderComponent ac = a.getComponent(colliderComponent.class);
        transformComponent at = a.getComponent(transformComponent.class);
        colliderComponent bc = b.getComponent(colliderComponent.class);
        transformComponent bt = b.getComponent(transformComponent.class);

        if( (at.x <= (bt.x+bc.w) && (at.x+ac.w) >= bt.x) && (at.y <= (bt.y+bc.h) && (at.y+ac.h) >= bt.y) ){
            System.out.println(Integer.toString(a.id) + " collided with " + Integer.toString(b.id) );
            ac.isColliding = true;
            bc.isColliding = true;

            return true;
        }

        return false;
    }


    // colliders no longer support rotation, keeping these methods on the off chance i implement in future. short explanation in notes.md
    public boolean linearIntersection(transformComponent at, transformComponent bt, colliderComponent ac, colliderComponent bc){

        float[][] linesA = getRectangleLines(ac, at);
        float[][] linesB = getRectangleLines(bc, bt);


        for(float[] lineA: linesA){
            for(float[] lineB : linesB){
                float px = getLinearIntersect(lineA, lineB)[0];
                float py = getLinearIntersect(lineA, lineB)[1];
                
                if(at.x<=px && px<=(at.x + ac.w) && at.y<=py && py<=(at.y + ac.h)){   //x <= px <= x + w and y <= py <= y + h
                    return true;
                }

            }
        }

        return false; 
    }

    public float[] getLinearEquation(float x1, float y1, float x2, float y2){
        // y = mx + b

        float m = (y2 - y1) / (x2 - x1);
        float b = y1 - (m*x1);

        return new float[] {m, b};
    }

    public float[][] getRectangleLines(colliderComponent c, transformComponent t){

        float [][] lines = new float[4][];

        lines[0] = getLinearEquation(t.x, t.y, t.x + c.w, t.y); // top edge
        lines[1] = getLinearEquation(t.x + c.w, t.y, t.x + c.w, t.y + c.h); // right edge
        lines[2] = getLinearEquation(t.x, t.y + c.h, t.x + c.w, t.y + c.h); // left edge
        lines[3] = getLinearEquation(t.x, t.y, t.x + c.w, t.y + c.h); // bottom edge

        return lines;
    }

    public float[] getLinearIntersect(float[] lineA, float[] lineB){

        float x = (lineA[1] - lineB[1]) / (lineA[0] - lineB[0]);
        float y = lineA[1] * x + lineA[0];

        return new float[] {x,y};
    }
}
