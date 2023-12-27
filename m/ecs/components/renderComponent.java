package m.ecs.components;

import java.io.*;
import javax.imageio.ImageIO;

import m.ecs.component;

import java.awt.image.BufferedImage;

public class renderComponent extends component{

    public int w,h; // need to pass these values for rotation

    public String path;

    // TODO: add zorder function and then parallelize
    public renderComponent(String imagePath){

        File file = new File(imagePath);
        BufferedImage image_t;
        try{
            this.path = imagePath;
            image_t = ImageIO.read(file);
            this.w = image_t.getWidth();
            this.h = image_t.getHeight();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    // overload
    public renderComponent(String imagePath, int w, int h){
        this.w = w;
        this.h = h;
        this.path = imagePath;
    }
    
}
