package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Door is a class in which the physical
 * information of the LevelObject Door
 * is created
 * @author Francisco Delgado
 */
public class Door extends LevelObject implements AnimatedObject {

    private Fixture closedFixture;
    private Fixture openedFixture;
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
        type = Type.DOOR;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata
     * for the LevelObject
     */
    public void createFixtureDef(){
        Shape closedShape = BodyService.getShape(Type.CLOSED_DOOR);
        FixtureDef closedFixtureDef = BodyService.getFixtureDef(type.CLOSED_DOOR);
        closedFixtureDef.shape = closedShape;
        closedFixture = body.createFixture(closedFixtureDef);
        closedFixture.setUserData(new Collider(Collider.Type.DOOR));
        closedShape.dispose();

        Shape openedShape = BodyService.getShape(Type.OPENED_DOOR);
        FixtureDef openedFixtureDef = BodyService.getFixtureDef(Type.OPENED_DOOR);
        openedFixtureDef.isSensor = true;
        openedFixtureDef.shape = openedShape;
        openedFixture = body.createFixture(openedFixtureDef);
        openedFixture.setUserData(new Collider(Collider.Type.DOOR));
        openedShape.dispose();

    }
    @Override
    public boolean isActive() {
        return !isClosed;
    }

    /**
     * Method which sets isClosed value
     * @param value Boolean to set isClosed to
     */
    public void setClosed(boolean value){
        if(isClosed != value){
           isClosed = value;
            if(value == false){

                closedFixture.setSensor(true);
                ((Collider)closedFixture.getUserData()).ignore(true);
                openedFixture.setSensor(false);

            }
            if(value == true){
                closedFixture.setSensor(false);
                openedFixture.setSensor(true);
                ((Collider)closedFixture.getUserData()).ignore(false);
            }
        }
    }
}
