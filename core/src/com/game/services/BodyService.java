package com.game.services;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.models.LevelObject;


public class BodyService {

    public static Shape getShape(LevelObject.Type type){
        PolygonShape shape = new PolygonShape();
        float height;
        float width;
        switch (type){
            case CUBE:
                break;
            case BUTTON:
                break;
            case PORTAL_BLUE:
                break;
            case PORTAL_ORANGE:
                break;
            default:
                break;

        }
        shape.setAsBox( (AssetsService.getSprite(type).getHeight()/2) * ConstantsService.PIXELS_TO_METERS,
                (AssetsService.getSprite(type).getWidth()/2) * ConstantsService.PIXELS_TO_METERS );
        return shape;
    }

    public static FixtureDef getFixtureDef(LevelObject.Type type) {
        float friction,density,restitution;

        switch(type) {
            case CUBE:
                friction = 0.7f;  density = 1f;   restitution = 0.2f;
                break;
            case BUTTON:
                friction = 0f;  density = 0f;   restitution = 0f;
                break;
            case PORTAL_BLUE:
                friction = 0f;  density = 0f; restitution = 0f;
                break;
            case PORTAL_ORANGE:
                friction = 0f;  density = 0f;   restitution = 0f;
            default:
                friction = 0f;  density = 0f;   restitution = 0f;
                break;
        }
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction=friction;
        fixtureDef.density=density;
        fixtureDef.restitution=restitution;
        return fixtureDef;
    }
    public static PolygonShape getPlayerShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ConstantsService.PLAYER_HEIGHT * AssetsService.getPlayerDimensions().getAspectRatio() /2, ConstantsService.PLAYER_HEIGHT /2);
        return shape;
    }
    public static FixtureDef getPlayerFixtureDef() {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.friction= 0.3f;
        fixtureDef.density = 0.5f;
        fixtureDef.restitution= 0.3f;
        return fixtureDef;
    }
    public static BodyDef.BodyType getBodyType(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return BodyDef.BodyType.DynamicBody;
            case BUTTON:
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
            case WALL:
                return BodyDef.BodyType.StaticBody;
            case PLATFORM:
                return BodyDef.BodyType.KinematicBody;
            default:
                return null;
        }
    }
}
