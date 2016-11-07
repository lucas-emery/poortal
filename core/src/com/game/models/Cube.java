package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.game.services.BodyService;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;

/**
 * Cube is a class in which the physical properties of the
 * Cube LevelObject are set
 * @author Francisco Delgado
 */
public class Cube extends LevelObject implements Teleportable{

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef
     * @param position Vector2 representing the physical
     *                 position of the Cube
     */
    public Cube(Vector2 position) { //Concept

        this.position = position;
        this.type = Type.CUBE;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
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