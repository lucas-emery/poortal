package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Button is a class in which the physical
 * information of the LevelObject button
 * is created and stored.
 * @author Pablo Radnic
 */
public class Button extends LevelObject{

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef
     * @param position Vector2 representing the physical
     *                 position of the Button
     */
    public Button(Vector2 position) { //Concept

        this.position = position;
        this.type = Type.BUTTON;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
    @Override
    public void createFixtureDef() {

        PolygonShape shape;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor=true;

        fixtureDef.shape=(shape=BodyService.getButtonSensorShape());
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.BUTTONSENSOR.val());
        shape.dispose();

        fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=(shape = BodyService.getButtonShape());
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.BUTTON.val());
        shape.dispose();


    }
}
