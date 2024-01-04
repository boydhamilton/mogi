package app.objects;

import m.ecs.*;
import m.ecs.components.*;

public class plane extends entity{

    public plane(int id, String tag){
        this.id = id;
        this.tag = tag;
        this.addComponent(new transformComponent(10, 10, 0));
        this.addComponent(new renderComponent("app/resources/plane.png")); // want basic entity constructor can add constructor field
        this.addComponent(new colliderComponent(10, 10));
    }

}
