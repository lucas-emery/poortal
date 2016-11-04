package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.BodyService;
import com.game.services.ConstantsService;


public class Portal extends LevelObject{

    private Vector2 normal;
    private Vector2 primary;
    private Fixture fixture;


    public Portal(Vector2 position, Vector2 normal, LevelObject.Type type) {
        this.position = position;
        this.normal = normal;
        this.primary = normal.cpy().rotate90(1); //Counter-clockwise
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
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Collider(Collider.Type.PORTAL));
        shape.dispose();

        fixtureDef.isSensor = false;
        CircleShape rim = new CircleShape();
        rim.setRadius(0.02f);
        fixtureDef.shape = rim;

        rim.setPosition(new Vector2(0, ConstantsService.getHeight(type) /2));
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.PORTALRIM));

        rim.setPosition(new Vector2(0, - ConstantsService.getHeight(type) /2));
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.PORTALRIM));

        rim.dispose();

    }

    public boolean compareFixture(Fixture otherFixture) {
        return fixture.equals(otherFixture);
    }

    public Vector2 getNormal() {
        return normal.cpy();
    }

    public Vector2 getPrimary() {
        return primary.cpy();
    }
}
