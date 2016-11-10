package com.game.services;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.models.LevelObject;


public class BodyService {

    public static Shape getShape(LevelObject.Type type) {
        if (type == LevelObject.Type.LEFT_DOOR || type == LevelObject.Type.RIGHT_DOOR) {
            EdgeShape shape = new EdgeShape();
            shape.set(new Vector2(-3 * ConstantsService.PIXELS_TO_METERS, -(AssetsService.getAnimatedSprites(type).get(0).getHeight() / 2) * ConstantsService.PIXELS_TO_METERS),
                    new Vector2(-3 * ConstantsService.PIXELS_TO_METERS, (AssetsService.getAnimatedSprites(type).get(0).getHeight() / 2) * ConstantsService.PIXELS_TO_METERS)); //Estos magic numbers son para que quede exactamente sobre la pared.
            return shape;
        }
        else {
            PolygonShape shape = new PolygonShape();
            shape.setAsBox( ConstantsService.getWidth(type) /2,
                            ConstantsService.getHeight(type)/2 );
            return shape;
        }
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
        fixtureDef.restitution= 0f;
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
            case LEFT_DOOR:
            case RIGHT_DOOR:
                return BodyDef.BodyType.StaticBody;
            //case PLATFORM:
            //    return BodyDef.BodyType.KinematicBody;
            default:
                return null;
        }
    }

    public static PolygonShape getPlayerSensorShape() {
        {
            float body_x=ConstantsService.PLAYER_WIDTH/2*0.8f, body_y=ConstantsService.PLAYER_HEIGHT/2;
            Vector2[] vec ={
                    new Vector2(-body_x-1f, -body_y),
                    new Vector2(-body_x-1f, body_y),
                    new Vector2(body_x+1f, -body_y),
                    new Vector2(body_x+1f, body_y)
            };
            PolygonShape shape = new PolygonShape();
            shape.set(vec);
            return shape;
        }
    }

    public static PolygonShape getPlayerFootSensor() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.435f, 0.2f, new Vector2(0,-0.85f),0); //el numero 0.435 es clave para que no crashee, no lo toquen.
        return shape;
    }

    public static PolygonShape getButtonSensorShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ConstantsService.getWidth(LevelObject.Type.BUTTON)/2, ConstantsService.getHeight(LevelObject.Type.BUTTON)/2, new Vector2(0,0.2f),0);
        return shape;
    }

    public static Shape getButtonSensorShape2() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((ConstantsService.getWidth(LevelObject.Type.BUTTON)+1)/2, (ConstantsService.getHeight(LevelObject.Type.BUTTON)+1)/2, new Vector2(0,0.2f),0);
        return shape;
    }
}
