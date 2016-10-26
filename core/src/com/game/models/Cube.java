package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.game.services.BodyService;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;

/**
 * Created by Usuario on 26/10/2016.
 */
public class Cube extends LevelObject {

    public Cube(Vector2 position) { //Concept

        this.position = position;
        this.type = Type.CUBE;
        createBodyDef(BodyService.isDynamic(type));
    }

    public void createFixtureDef(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(AssetsService.getSprite(type).getHeight()/2,AssetsService.getSprite(type).getWidth()/2);//getShape(type)???

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; //getDensity(type)
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
