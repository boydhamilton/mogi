package components;

import ecs.component;

public class colliderComponent extends component{

    public int w,h;
    public boolean isColliding; // set in system

    public colliderComponent(int w, int h){
        this.w = w;
        this.h = h;
    }
}
