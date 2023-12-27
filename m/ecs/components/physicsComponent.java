package m.ecs.components;

import m.ecs.component;

public class physicsComponent extends component{
    
    public float[] velocity; // x, y
    public float acceleration, decceleration; // multipliers
    public float maxSpeed;

    // dont need constuctor these values are just set through system i lied init to 0

    
    public physicsComponent(float[] velocity, float acceleration, float decceleration, float  maxSpeed){
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.decceleration = decceleration;
        this.maxSpeed = maxSpeed;
    } 
}
