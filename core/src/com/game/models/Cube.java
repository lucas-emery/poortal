package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.game.services.BodyService;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;

/**
 * Created by Usuario on 26/10/2016.
 */
public class Cube extends LevelObject implements Teleportable{

    public Cube(Vector2 position) { //Concept

        this.position = position;
        this.type = Type.CUBE;
        createBodyDef();
    }

    @Override
    public void createFixtureDef(){
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.CUBE));
        shape.dispose();
    }

    @Override
    public void setTransform(Vector2 position, float angle) {
        body.setTransform(position, angle);
    }
}