package com.game.models;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;
import com.game.services.BodyService;

public class LevelObject {
    public enum Type {
        CUBE, BUTTON, PORTAL_BLUE, PORTAL_ORANGE, WALL
    }
    private Type type;
    private Body body;
    private BodyDef bodyDef;
    private Vector2 position;

    public LevelObject(Type type, Vector2 position) { //Concept
        this.type = type;
        this.position = position;
        createBodyDef(BodyService.isDynamic(type));
    }
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

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(AssetsService.getSprite(type).getHeight()/2,AssetsService.getSprite(type).getWidth()/2);//getShape(type)???

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; //getDensity(type)
        fixtureDef.restitution = 0.5f;

        body.createFixture(fixtureDef);

        shape.dispose();
    }
    public void setWall(Body wall, Vector2 beginning, Vector2 end){
        this.body = wall;

        EdgeShape shape = new EdgeShape();
        shape.set(beginning,end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

    }

    public Body getBody(){
        return body;
    }

    public Type getType() {
        return type;
    }


}
