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

    @Override
    public void createFixtureDef(){
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
