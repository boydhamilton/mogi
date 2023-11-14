package ecs;

import java.util.ArrayList;
import java.awt.*;

public class manager {
    public ArrayList<system> systems = new ArrayList<>();
    public ArrayList<entity> entities = new ArrayList<>();

    public void addSystem(system s){
        systems.add(s);
    }
    public void removeSystem(system s){
        systems.remove(s);
    }
    public void addEntity(entity e){
        entities.add(e);
    }
    public void removeEntity(entity e){
        entities.remove(e);
    }

    public void init(){
        for(system s : systems){
            s.init(entities);
        }
    }

    public void update(){
        for(system s : systems){
            s.update(entities);
        }
    }
    public void draw(Graphics g){
        for(system s : systems){
            s.draw(entities, g);
        }
    }

}
