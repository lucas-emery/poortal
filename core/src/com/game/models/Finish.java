package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;

/**
 * Created by lemery on 09/11/16.
 */
public class Finish extends LevelObject{

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef
     * @param position Vector2 representing the physical
     *                 position of the Finish
     */
    public Finish(Vector2 position) {

        this.position = position;
        this.type = Type.FINISH;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
    @Override
    public void createFixtureDef() {
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.FINISH));
        shape.dispose();
    }
}
