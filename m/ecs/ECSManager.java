package m.ecs;

import java.util.ArrayList;

import java.awt.*;

public class ECSManager { // expanded past ecs manager and also handles scene management
    public ArrayList<system> systems = new ArrayList<>();
    public ArrayList<entity> entities = new ArrayList<>();

    
    public void addSystem(system s){
        systems.add(s);
    }
    public void removeSystem(system s){
        systems.remove(s);
    }

    public void addEntity(entity e){
        entities.add(e.getID(), e); // position @ id, makes getentity easy
    }

    public void removeEntity(entity e){
        entities.remove(e);
    }
    public void removeEntity(int id){ // overload
        entities.remove(id);
    }

    public entity getEntity(int id){
        return entities.get(id);
    }
    public ArrayList<entity> getTaggedEntities(String tag){
        ArrayList<entity> list = new ArrayList<>();
        for(entity e : entities){
            if(e.getTag()==tag){
                list.add(e);
            }
        }
        if(list.size()>0){
            return list;
        }
        return null;
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
