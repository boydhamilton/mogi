package components;

import ecs.component;

public class transformComponent extends component{

    public float x,y;
    public float d; // radians

    // could add transform system to just loop degrees ( t.d = t.d%(2*Math.PI) )

    public transformComponent(int x, int y, float d){
        this.x = x;
        this.y = y;
        this.d = d;
    }

    // overload
    public transformComponent(int x, int y){ 
        this.x = x;
        this.y = y;
        this.d = 0.0f;
    }
}
