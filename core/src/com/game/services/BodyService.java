package com.game.services;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.models.LevelObject;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;


public class BodyService {

    public static Shape getShape(LevelObject.Type type){
        if(type == LevelObject.Type.LEFT_DOOR || type == LevelObject.Type.RIGHT_DOOR){
            EdgeShape shape = new EdgeShape();
            shape.set(new Vector2(-3*ConstantsService.PIXELS_TO_METERS,-(AssetsService.getAnimatedSprites(type).get(0).getHeight()/2) * ConstantsService.PIXELS_TO_METERS),
                    new Vector2(-3*ConstantsService.PIXELS_TO_METERS,(AssetsService.getAnimatedSprites(type).get(0).getHeight()/2) * ConstantsService.PIXELS_TO_METERS) ); //Estos magic numbers son para que quede exactamente sobre la pared.
            return shape;
        }
        else {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox((AssetsService.getStaticSprite(type).getHeight() / 2) * ConstantsService.PIXELS_TO_METERS,
                    (AssetsService.getStaticSprite(type).getWidth() / 2) * ConstantsService.PIXELS_TO_METERS);
            return shape;
        }
    }

    public static PolygonShape getButtonShape(){
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ConstantsService.BUTTON_WIDTH , ConstantsService.BUTTON_HEIGHT );
        return shape;
    }

    public static FixtureDef getFixtureDef(LevelObject.Type type) {
        float friction,density,restitution;

        switch(type) {
            case CUBE:
                friction = 0.7f;  density = 1f;   restitution = 0.2f;
                break;
            case BUTTON:
                friction = 1f;  density = 0f;   restitution = 0f;
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
        fixtureDef.friction= 1f;
        fixtureDef.density = 0.5f;
        fixtureDef.restitution= 0.3f;
        return fixtureDef;
    }
    public static BodyDef.BodyType getBodyType(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return BodyDef.BodyType.DynamicBody;
            case BUTTON:
                return BodyDef.BodyType.StaticBody;
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
            case WALL:
            case LEFT_DOOR:
            case RIGHT_DOOR:
                return BodyDef.BodyType.StaticBody;
            //case PLATFORM:
            //    return BodyDef.BodyType.KinematicBody;
            default:
                return null;
        }
    }

    public static PolygonShape getPlayerSensorShape(boolean isRight) {
        {
            float body_x=0.5f, body_y=0.95f, constant=(isRight)?1.0f:-1.0f;
            Vector2[] vec ={
                    new Vector2(-body_x+constant*1f, -body_y),
                    new Vector2(-body_x+constant*1f, body_y),
                    new Vector2(body_x+constant*1f, -body_y),
                    new Vector2(body_x+constant*1f, body_y)
            };
            PolygonShape shape = new PolygonShape();
            shape.set(vec);
            return shape;
        }
    }

    public static PolygonShape getPlayerFootSensor() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.4f, 0.2f, new Vector2(0,-0.85f),0);
        return shape;
    }

    public static PolygonShape getButtonSensorShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ConstantsService.BUTTON_WIDTH, ConstantsService.BUTTON_HEIGHT, new Vector2(0,0.2f),0);
        return shape;
    }
}
