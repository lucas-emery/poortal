package com.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;
import com.game.services.BodyService;

/**
 * Abstract class which is super class of all physical objects
 * within the level.
 * @author Lucas Emery
 */

public abstract class LevelObject {
    public enum Type {
        CUBE, BUTTON, PORTAL_BLUE, PORTAL_ORANGE, WALL, DOOR, CLOSED_DOOR, OPENED_DOOR
    }
    protected Type type;
    protected Body body;
    protected BodyDef bodyDef;
    protected Vector2 position;

    /**
     * Method which creates bodyDef for the level object.
     * Also sets physical position of the object within the level.
     */
    public void createBodyDef() {
        bodyDef = new BodyDef();

        bodyDef.type = BodyService.getBodyType(type);

        bodyDef.position.set(position.x, position.y);
    }

    /**
     * Method which returns BodyDef of the LevelObject
     * @return BodyDef bodyDef
     */
    public BodyDef getBodyDef(){
        return bodyDef;
    }


    /**
     * Method which sets the body for the LevelObject
     * @param body Body to be set
     */
    public void setBody(Body body){

        this.body = body;

        createFixtureDef();
    }

    public abstract void createFixtureDef();

    /**
     * Method which gets body for the LevelObject
     * @return Body body
     */
    public Body getBody(){
        return body;
    }

    /**
     * Method which gets the Type attribute of the LevelObject
     * @return Type enum which represents the "type" of the LevelObject
     */
    public Type getType() {
        return type;
    }

    /**
     * Method which gets the physical position of the LevelObject
     * @return Vector2 two dimensional vector with the objects coordinates
     */
    public Vector2 getPosition() {
        return body.getPosition().cpy();
    }

}
