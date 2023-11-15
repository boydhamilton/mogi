package components;

import ecs.component;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class renderComponent extends component{

    public int w,h; // need to pass these values for rotation

    public String path;

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
