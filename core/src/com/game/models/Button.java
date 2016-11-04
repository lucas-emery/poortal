package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Created by pradnic on 03/11/16.
 */
public class Button extends LevelObject{

    public Button(Vector2 position) { //Concept

        this.position = position;
        this.type = Type.BUTTON;
        createBodyDef();
    }
    @Override
    public void createFixtureDef() {

        PolygonShape shape;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor=true;

        fixtureDef.shape=(shape=BodyService.getButtonSensorShape());
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.BUTTONSENSOR));
        shape.dispose();

        fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=(shape = BodyService.getButtonShape());
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.BUTTON));
        shape.dispose();


    }
}
