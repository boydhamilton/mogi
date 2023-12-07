package ecs;
import java.util.ArrayList;



public class entity {
    //scene.manager s = new scene.manager();
    public int id;
    public String tag;

    public ArrayList<component> components = new ArrayList<>();

    public entity(){ // constructor for when used as extension, can set id and tag in new object
        this.id = Integer.MIN_VALUE + 1; // chosen as it's doubtful there will be conflicts
        this.tag = null;
    }

    public entity(int id){
        this.id = id;
        this.tag = null;
    }

    // overload
    public entity(int id, String tag){
        this.id = id;
        this.tag = tag;
    }

    public int getID(){
        return id;
    }

    public String getTag(){
        return tag;
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