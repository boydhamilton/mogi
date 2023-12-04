package app;


import java.awt.Graphics;

import exec.*;


public class examplescene2 extends scene{

    public examplescene2(){
        System.out.println("hello from examplescene2");
    }

    @Override
    public void update(){
        System.out.println("updatin 2");
    }

    @Override
    public void draw(Graphics g){
        System.out.println("drawn 2");
    }
}
