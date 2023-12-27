package m.ecs.systems;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;
import java.util.stream.IntStream;

import m.ecs.*;
import m.ecs.components.*;

public class colliderSystem implements system{
    
    boolean debugDrawColliders = m.exec.gameManager.DEBUG;

    @Override
    public void init(List<entity> entities) {
        return;
    }

    @Override
    public void update(List<entity> entities) {

        // add screen segmentation (only check other entities in that quadrant)
        
        IntStream.range(0,entities.size())
            .parallel()
            .forEach(i -> {
                entity e = entities.get(i);

            if(e!=null && e.hasComponent(colliderComponent.class) && e.hasComponent(transformComponent.class)){
                colliderComponent a = e.getComponent(colliderComponent.class);
                transformComponent at = e.getComponent(transformComponent.class);

                a.netPositionX = (int)at.x + a.x;
                a.netPositionY = (int)at.y + a.y;

                IntStream.range(i+1,entities.size())
                    .parallel()
                    .forEach(j -> {
                        
                        entity eb = entities.get(j);

                        if(eb!=null && eb.hasComponent(transformComponent.class) && eb.hasComponent(colliderComponent.class)){
                            colliderComponent b = eb.getComponent(colliderComponent.class);
                            transformComponent bt = eb.getComponent(transformComponent.class);

                            b.netPositionX = (int)bt.x + b.x;
                            b.netPositionY = (int)bt.y + b.y;

                            //if( (at.x <= (bt.x+b.w) && (at.x+a.w) >= bt.x) && (at.y <= (bt.y+b.h) && (at.y+a.h) >= bt.y) ){
                            int ax = a.netPositionX;
                            int ay = a.netPositionY;
                            int bx = b.netPositionX;
                            int by = b.netPositionY;

                            if( (ax <= (bx+b.w) && (ax+a.w) >= bx) && (ay <= (by+b.h) && (ay+a.h) >= by) ){
                                m.exec.gameManager.writeToLog(Integer.toString(e.id) + "collided with " + Integer.toString(eb.id));
                                a.isColliding = true;
                                b.isColliding = true;
                            }
                        }
                        
                });
            }
        });
    }

    @Override
    public void draw(List<entity> entities, Graphics g) {
        if(debugDrawColliders){
            for(entity e : entities){
                if(e!=null && e.hasComponent(colliderComponent.class) && e.hasComponent(transformComponent.class)){
                    Graphics2D g2d = (Graphics2D) g;
                    // transformComponent t = e.getComponent(transformComponent.class);

                    colliderComponent c = e.getComponent(colliderComponent.class);
                    g2d.drawRect(c.netPositionX, c.netPositionY, c.w, c.h);
                }
            }
        }

    }

    // aabb a against b check
    public boolean AABBisColliding(entity a, entity b){
        colliderComponent ac = a.getComponent(colliderComponent.class);
        transformComponent at = a.getComponent(transformComponent.class);
        colliderComponent bc = b.getComponent(colliderComponent.class);
        transformComponent bt = b.getComponent(transformComponent.class);

        ac.netPositionX = (int)at.x + ac.x;
        ac.netPositionY = (int)at.y + ac.y;
        bc.netPositionX = (int)bt.x + bc.x;
        bc.netPositionY = (int)bt.y + bc.y;

        int ax = ac.netPositionX;
        int ay = ac.netPositionY;
        int bx = bc.netPositionX;
        int by = bc.netPositionY;

        if( (ax <= (bx+bc.w) && (ax+ac.w) >= bx) && (ay <= (by+bc.h) && (ay+ac.h) >= by) ){
            System.out.println(Integer.toString(a.id) + " collided with " + Integer.toString(b.id) );
            ac.isColliding = true;
            bc.isColliding = true;

            return true;
        }

        return false;
    }

    public boolean AABBisColliding(String tagA, String tagB, ECSManager manager){
        ArrayList<entity> entitiesA = manager.getTaggedEntities(tagA);
        ArrayList<entity> entitiesB = manager.getTaggedEntities(tagB);

        ArrayList<Boolean> results = new ArrayList<>();

        if(entitiesA!=null && entitiesB!=null){
            IntStream.range(0,entitiesA.size())
                .parallel()
                .forEach(i -> {
                    entity e = entitiesA.get(i);

                if(e!=null && e.hasComponent(colliderComponent.class) && e.hasComponent(transformComponent.class)){
                    colliderComponent a = e.getComponent(colliderComponent.class);
                    transformComponent at = e.getComponent(transformComponent.class);

                    a.netPositionX = (int)at.x + a.x;
                    a.netPositionY = (int)at.y + a.y;

                    IntStream.range(i+1,entitiesB.size())
                        .parallel()
                        .forEach(j -> {
                            
                            entity eb = entitiesB.get(j);

                            if(eb!=null && eb.hasComponent(colliderComponent.class) && eb.hasComponent(transformComponent.class)){
                                colliderComponent b = eb.getComponent(colliderComponent.class);
                                transformComponent bt = eb.getComponent(transformComponent.class);

                                b.netPositionX = (int)bt.x + b.x;
                                b.netPositionY = (int)bt.y + b.y;

                                int ax = a.netPositionX;
                                int ay = a.netPositionY;
                                int bx = b.netPositionX;
                                int by = b.netPositionY;

                                if( (ax <= (bx+b.w) && (ax+a.w) >= bx) && (ay <= (by+b.h) && (ay+a.h) >= by) ){
                                    results.add(true);
                                }
                            }
                            
                    });
                }
            });
        }
        

        return results.size()>0;
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