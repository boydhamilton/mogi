package systems;

import java.awt.Graphics;
import java.util.List;
import java.util.stream.IntStream;

import ecs.*;
import components.*;

public class physicsSystem implements system{ // for velocity acceleration movement stuff, calling it physics way overstates what itll do lols

    @Override
    public void init(List<entity> entities) {
        return;
    }

    @Override
    public void update(List<entity> entities) {

        IntStream.range(0, entities.size())
        .parallel()
        .forEach(i -> {
            entity e = entities.get(i);
            if(e.hasComponent(transformComponent.class) && e.hasComponent(physicsComponent.class)){
                transformComponent t = e.getComponent(transformComponent.class);
                physicsComponent p = e.getComponent(physicsComponent.class);

                float vx = p.velocity[0];
                float vy = p.velocity[1];

                // velocity is added in perpetuity
                t.x+=vx;
                t.y+=vy;
            }
            
            // do acceleration pls
            /*
                if(v < 1){
                v = 1
                }elseif(v<maxv){
                v*=accel
                }elseif(v>maxv){
                v = maxv
                }
                */
        });
    }

    @Override
    public void draw(List<entity> entities, Graphics g) {
        return;
    }
    
}
