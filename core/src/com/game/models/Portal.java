package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Class which represents the physical portal object
 * @author Lucas Emery
 */
public class Portal extends LevelObject{

    private Vector2 normal;

    /**
     * Constructor for the Portal LevelObject, sets position, normal vector and type
     * @param position Vector2 representing the position of the portal in the level
     * @param normal Vector2 normal(90°) vector to the portal orientation
     * @param type Type enum value representing the Portal LevelObject
     */
    public Portal(Vector2 position, Vector2 normal, LevelObject.Type type) {
        this.position = position;
        this.normal = normal;
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
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.PORTAL.val());
        shape.dispose();
    }
}
