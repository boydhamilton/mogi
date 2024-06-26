
# mogi

## ECS (breakdown)

An ECS (Entity Component System) operates by having arrays of entities, each of which are composed of components, each of which operate on their own system.
Entities contain components which contain data. It is then up to the systems (each of which have their own update/draw loops), to do something with that data. A rendering component may contain the file for an image, but it is up to the rendering system to then paint it to the surface (of which there is only one in mogi).


## APP CREATION
There is an enforced file structure, any application you make must go in the app folder. The main class must be launch.java. This is because we must pass a scene on init to the run file. There are ways around it certainly if you truly wish, but this is the most straightforward way to use mogi.
Once you have created launch.java, create the scene object.

```java
package app;

import java.awt.Graphics;

import m.exec.*;
import m.engine.*;
import m.ecs.*;
import m.ecs.components.*;
import m.ecs.systems.*;

public class launch extends scene{

    ECSManager world = new ECSManager();

    @Override
    public void init(){
        world.init();
    }

    @Override
    public void update(int dt){
        world.update();
    }

    @Override
    public void draw(Graphics g){
        world.draw(g);
    }
}
```

You can use functions within the execution manager such as `setWindowDimensions(int w, int h)` and `setWindowTitle(String title)` can be used for customizing the window on launch.
```java
    @Override // in launch.java (need to call on boot)
    public void init(){
        exec.manager.setWindowDimensions(500,500);
        exec.manager.setWindowTitle("game");

        // other init stuff...
    }
```
If needed, can edit exec.run to fulfill any other window requirements you may have.

## USING THE ECS
To initialize the ECS, you must create an ECS manager to handle all system and entity addition, removal, updating, and drawing.

```java
    ecs.manager world = new ecs.manager();
```

Within the overridden update and draw methods from before, add

```java
    world.update(); // in update
    world.draw(g); // in draw
```

Adding components [below](#ENTITIES):
```java
    entity.addComponent(new component());
```

Entities are  not added automatically to the ECS. After defining the entity, call the ```addEntity``` method from the manager object.

```java
    world.addEntity(entity);
```

In order for the components to be of use, we must utilize systems. This is spoken about more in the ECS breakdown. The systems, however, are not added automatically.

```java
    world.addSystem(new system()); // add at end of init function
```

## ENTITIES

New entity demo:

```java
ecs.manager world = new ecs.manager();
entity plane = new entity();

plane.addComponent(new transformComponent(50, 50));
plane.addComponent(new renderComponent("app/plane.png"));

world.addEntity(plane);
```

To create an independent entity object with constant components, simply extend the entity class. *MAKE SURE YOU ADD AN ID*, it is essential for ECS operation.
You can elaborate on this structure by passing more arguments to the constructor and allowing for further component customization.

```java
public class plane extends entity{

    public plane(int id, String tag){ // tag is not essential, though id is
        this.id = id;
        this.tag = tag;
        this.addComponent(new transformComponent(10, 10, 0));
        this.addComponent(new renderComponent("app/resources/plane.png"));
        this.addComponent(new colliderComponent(10, 10));
    }
}
public class thisIsPlaneScene extends scene{
    entity player;

    @Override
    public void init(){
        player = new plane();
    }
}
```

### TRANSFORM:

Add transform component to your entity like so:
```java
bullet.addComponent(new transformComponent(int x, int y, float radians (overload)));
```

Required for most other systems. Position is uniform throughout entity, unless specifically stated all other systems will inherit from transform (with offsets etc. if applicable)
No system.


### RENDERING:

Add rendering component to your entity like so:

```java
entity.addComponent(new renderComponent(String filePath));
```

The file path should point to an image that java is able to parse into a [Buffered Image](https://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferedImage.html) (PEG, PNG, BMP, WEBMP, GIF). The render system requires a transform component to be added.
Rendering system, renders the images.


### COLLISION:

Add collider component to your entity like so:

```java
entity.addComponent(new colliderComponent(int width, int height, int offsetx (overload), int offsety (overload)));
```

Check for collision:
```java
if(entity.isColliding){
    // colliding with anything
}

if(AABBisColliding(entity A, entity B)){
    // A and B are colliding
}

if(AABBisColliding(String tagA, String tagB)){
    // Entity with tagA is colliding with entity 
}
```

Collision system, checks collisions and provides functions.

#### ROTATING Axis Aligned Bounding Boxes COLLISION BREAKDOWN

Quick unadded feature that is useful for many games so I'll write a quick theory breakdown here:

+ how to collide rectangles at any angle
+ no longer rectangle, they are 4 lines
+ find equation of each line for each rectangle (albeta math 10 time)
+ find intersection points of all lines
+ if intersection point is within the bounds of one of the rectangles, they are colliding

code is in collider system, but doesn't work and so is not used in any demos

## INPUT

Because mogi was originally made to build games that operate without a mouse, there is no native support for mouse or mousebutton input yet. 

There are a few options for processing input, scene.keys is a boolean array with index corresponding to key code. Use 

```java
scene.keys[int keyCode] // returns a boolean value, true if keycode down otherwise false
```

More conventional options are:

```java
scene.keyDown(char keyChar)
scene.keyDown(int keyCode)
```

Both of which return a boolean that is true if the key is depressed and false otherwise.

## SCENES

Scenes are objects extending the exec.scene class. There is an `init()` function that is called when the scene is initialized (either on launch or on load), `update()` function that is called periodically every frame, and a `draw()` function that is called immediately after the update.

Scene structure explained [here](#app-creation).

To load other scenes, call the `loadScene(scene scene)` from the execution manager.
To reload current scene, you cannot use the `this` operator, you must use `new scenename()`.

Example:

```java
    if(scene.keyDown('m')){
        exec.manager.loadScene(new launch());
    }
    if(scene.keyDown('n')){
        exec.manager.loadScene(new examplescene2());
    }
```


