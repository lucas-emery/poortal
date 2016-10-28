package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;




public class Portal extends LevelObject{

    private String color;
    private LevelObject.Type type;
    private Vector2 position;

    public Portal(Vector2 position, String color){

        if(color == "BLUE")
            type = Type.PORTAL_BLUE;
        else if(color == "ORANGE")
            type = Type.PORTAL_ORANGE;
        else
            throw new IllegalArgumentException("Portal color must be either blue or orange");

        this.position = position;
        this.createBodyDef(BodyService.isDynamic(type));

    }

    @Override
    public void createFixtureDef() {
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

        
    }


    public String getColor(){
        return color;
    }
}
