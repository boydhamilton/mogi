import java.awt.*;
import java.awt.geom.Rectangle2D;

public class rectangle{
    // class variable initialization
    static int x,y,w,h;

    // class functions
    public void init(){
        x = 0;
        y = 0;
        w = 25;
        h = 25;
    }
    public void update(){ // do things like gravity
        return;
    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Rectangle2D shape = new Rectangle2D.Double(x, y, w, h);

        g2d.draw(shape);
    }

    // getter and setter functions
    public int[] getposition(){
        return new int[] {x,y};
    }
    public int[] getsize(){
        return new int[] {w,h};
    }
    public void setposition(int xn, int yn){
        x=xn;
        y=yn;
    }
    public void setsize(int wn, int hn){
        w=wn;
        h=hn;
    }
}