package com.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;
import com.game.services.BodyService;

public abstract class LevelObject {
    public enum Type {
        CUBE, BUTTON, PORTAL_BLUE, PORTAL_ORANGE, WALL
    }
    protected static Type type;
    protected Body body;
    protected BodyDef bodyDef;
    protected Vector2 position;

    public void createBodyDef(Boolean isDynamic) {
        bodyDef = new BodyDef();

        if (isDynamic) bodyDef.type = BodyDef.BodyType.DynamicBody;
        else bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(position.x, position.y);
    }

    public BodyDef getBodyDef(){
        return bodyDef;
    }

    public void setBody(Body body){

        this.body = body;

        createFixtureDef();
    }

    public abstract void createFixtureDef();

    public Body getBody(){
        return body;
    }

    public Type getType() {
        return type;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

}
