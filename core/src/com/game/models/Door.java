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
public class Door extends LevelObject implements AnimatedObject {

    private boolean isClosed;

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef.
     * By default Door is initialized as closed.
     * @param position Vector2 which represents the physical
     *                 position of the LevelObject
     */
    public Door(Vector2 position){
        this.position = position;
        isClosed = true;
        type = Type.CLOSED_DOOR;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
    public void createFixtureDef(){
            Shape closedShape = BodyService.getShape(type);
        FixtureDef closedFixtureDef = BodyService.getFixtureDef(type);
        closedFixtureDef.shape=closedShape;
        body.createFixture(closedFixtureDef).setUserData(ConstantsService.ColliderType.CUBE.val());
        closedShape.dispose();

        Shape shape = BodyService.getShape(Type.OPENED_DOOR);
        FixtureDef openedFixtureDef = BodyService.getFixtureDef(Type.OPENED_DOOR);
        openedFixtureDef.isSensor = true;
        openedFixtureDef.shape=shape;
        body.createFixture(openedFixtureDef).setUserData(ConstantsService.ColliderType.CUBE.val());
        shape.dispose();

    }
    @Override
    public boolean isActive() {
        return !isClosed;
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
        if(isClosed != value){
           isClosed = value;
            if(value == true){

                body.getFixtureList().get(0).setSensor(true);
                body.getFixtureList().get(1).setSensor(false);

            }
            if(value == false){
                body.getFixtureList().get(0).setSensor(false);
                body.getFixtureList().get(1).setSensor(true);
            }
        }
    }
}
