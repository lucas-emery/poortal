package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Button is a class in which the physical
 * information of the LevelObject Door
 * is created
 * @author Francisco Delgado
 */
public class Door extends LevelObject {

    private boolean isClosed;

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef.
     * By default Door is initialized as closed.
     * @param position Vector2 which represents the physical
     *                 position of the LevelObject
     * @param pointsRight Boolean which represents the direction
     *                    in which the door is facing
     */
    public Door(Vector2 position,boolean pointsRight){
        this.position = position;
        isClosed = true;
        if(pointsRight)
            type = Type.RIGHT_DOOR;
        else
            type = Type.LEFT_DOOR;

        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
    public void createFixtureDef(){
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.CUBE.val());
        shape.dispose();
    }

    /**
     * Method which is used to get isClosed
     * @return Boolean which represents the open state of the door
     */
    public boolean isClosed(){
        return isClosed;
    }

    /**
     * Method which sets isClosed value
     * @param value Boolean to set isClosed to
     */
    public void setClosed(boolean value){
        isClosed = value;
    }

}
