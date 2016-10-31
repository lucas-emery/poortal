package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;




public class Portal extends LevelObject{

    private Vector2 normal;

    public Portal(Vector2 position, Vector2 normal, LevelObject.Type type) {
        this.position = position;
        this.normal = normal;
        this.type = type;

        createBodyDef();
        bodyDef.angle = normal.angleRad();
    }

    @Override
    public void createFixtureDef() {
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
