package systems;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.io.*;
import javax.imageio.ImageIO;

import ecs.*;
import components.*;

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
        
        for(entity e : entities){
            if(e!=null && e.hasComponent(transformComponent.class) && e.hasComponent(renderComponent.class)){
                Graphics2D g2d = (Graphics2D) g;

                transformComponent t = e.getComponent(transformComponent.class);
                renderComponent r = e.getComponent(renderComponent.class);
        
                int x = (int)t.x;
                int y = (int)t.y;
                float d = t.r;
                int w = r.w;
                int h = r.h;

                try{
                    BufferedImage img = ImageIO.read(new File(r.path));

                    if(d!=0){
                        g2d.rotate(d, x+w/2, y+h/2);
                        g2d.drawImage(img, x, y, null);
                        g2d.rotate(-d, x+w/2, y+h/2);
                    }else{
                        g2d.drawImage(img, x, y, null); 
                    }
                    
                }catch(IOException err){
                    err.getStackTrace();
                }
                
            }
        }


    }
    
}
