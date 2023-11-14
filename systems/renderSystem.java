package systems;

import java.util.List;
import java.util.stream.IntStream;
import java.awt.*;

import components.*;

import components.transformComponent;
import ecs.*;

public class renderSystem implements system{
    
    @Override
    public void init(List<entity> entities) {
        return;
    }


    @Override
    public void update(List<entity> entities) {
        return;
    }

    @Override
    public void draw(List<entity> entities, Graphics g) {


        IntStream.range(0,entities.size())
            .parallel()
            .forEach(i -> {

            entity e = entities.get(i);
            if(e.hasComponent(renderComponent.class) && e.hasComponent(transformComponent.class)){
                renderComponent r = e.getComponent(renderComponent.class);
                transformComponent t = e.getComponent(transformComponent.class);
                
                float x = t.x;
                float y = t.y;

                int w = r.w;
                int h = r.h;

                java.awt.Color c = r.c;

                Graphics2D g2d = (Graphics2D) g;

                g2d.setColor(c);
                g2d.fillRect((int)x, (int)y, w, h);
            }
        });


        /* IntStream.range(0,entitiesToCheck.size()) // BIG NOTE: DOING THIS RANDOMIZES RENDER DEPTH EACH FRAME. MAYBE ADD AN ARGUMENT FOR THAT
        // or dont parallize idk
            .parallel()
            .forEach(i -> {
                entity e = entitiesToCheck.get(i);
                r r = e.getComponent(r.class);
                t t = e.getComponent(t.class);

                int x = t.x;
                int y = t.y;
                int w = r.w; 
                int h = r.h;
                java.awt.Color c = r.c;

                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(c);
                g2d.fillRect(x, y, w, h);
            }); */
    }
    
}
