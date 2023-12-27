package m.ecs.components;

import m.ecs.component;

public class transformComponent extends component{

    public float x,y;
    public float r; // degrees

    public transformComponent(int x, int y, float r){
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public transformComponent(int x, int y){ // method overload, dont want to force rotation on the populace
        this.x = x;
        this.y = y;
        this.r = 0.0f;
    }
}
