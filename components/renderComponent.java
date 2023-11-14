package components;

import ecs.component;

public class renderComponent extends component{

    public int w,h; // will do other stuff later, for now i just render squares

    public java.awt.Color c;

    public renderComponent(int w, int h, java.awt.Color c){
        // temporary
        this.w = w;
        this.h = h;
        // -temporary

        this.c = c;
    }
    
}
