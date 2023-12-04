package components;

import ecs.component;

public class colliderComponent extends component{

    public int w,h;
    public int x,y; // offset from transform
    public boolean isColliding; // set in system

    public int netPositionX, netPositionY;

    public colliderComponent(int w, int h){
        this.w = w;
        this.h = h;
        this.x = 0;
        this.y = 0;
    }

    // overload
    public colliderComponent(int w, int h, int x, int y){
        this.w = w;
        this.h = h;
        this.x = x;
        this.y = y;
    }
}
