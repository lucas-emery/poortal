package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Class which represents the physical portal object
 * @author Lucas Emery
 */
public class Portal extends LevelObject{

    private Vector2 normal;
    private Vector2 primary;
    private Fixture fixture;


    /**
     * Constructor for the Portal LevelObject, sets position, normal vector and type
     * @param position Vector2 representing the position of the portal in the level
     * @param normal Vector2 normal(90°) vector to the portal orientation
     * @param type Type enum value representing the Portal LevelObject
     */
    public Portal(Vector2 position, Vector2 normal, LevelObject.Type type) {
        this.position = position;
        this.normal = normal;
        this.primary = normal.cpy().rotate90(1); //Counter-clockwise
        this.type = type;

        createBodyDef();
        bodyDef.angle = normal.angleRad();
    }

    /**
     * Method which creates fixture for the Portal LevelObject
     */
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
        rim.setRadius(ConstantsService.PORTAL_RIM_RADIUS);

        fixtureDef.shape = rim;

        rim.setPosition(new Vector2(0, ConstantsService.getHeight(type) /2));
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.PORTALRIM));

        rim.setPosition(new Vector2(0, - ConstantsService.getHeight(type) /2));
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.PORTALRIM));

        rim.dispose();

    }

    /**
     * method which indicates weather two fixtures are equal.
     * @param otherFixture the fixture to which self compares to
     * @return true if they are equal, false otherwise
     */
    public boolean compareFixture(Fixture otherFixture) {
        return fixture.equals(otherFixture);
    }

    /**
     * @return vector normal to the portal
     */
    public Vector2 getNormal() {
        return normal.cpy();
    }

    /**
     * @return portal's primary vector
     */
    public Vector2 getPrimary() {
        return primary.cpy();
    }
}
