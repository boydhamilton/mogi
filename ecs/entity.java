package ecs;
import java.util.ArrayList;


public class entity {
    public int id; // maybe do string tags or something later?

    public ArrayList<component> components = new ArrayList<>();

    public entity(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }

    public void addComponent(component c){
        components.add(c);
    }

    public void removeComponent(component c){
        components.remove(c);
    }

    public boolean hasComponent(Class<? extends component> c){
        for(component comp : components){
            if(c.isInstance(comp)){
                return true;
            }
        }
        return false;
    }

    public <T extends component> T getComponent(Class<T> c){
        for(component comp : components){
            if(c.isInstance(comp)){
                return c.cast(comp);
            }
        }

        return null;
    }
}