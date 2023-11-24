package components;

import ecs.component;

public class transformComponent extends component{

    public float x,y;
    public float d; // degrees

    public transformComponent(int x, int y, float d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

    public transformComponent(int x, int y){ // method overload, dont want to force rotation on the populace
        this.x = x;
        this.y = y;
        this.d = 0.0f;
    }
}
