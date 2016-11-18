package com.game.services;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.models.LevelObject;


public class BodyService {
        public static Shape getShape(LevelObject.Type type){
            PolygonShape shape = new PolygonShape();
            switch (type){
                case OPENED_DOOR:

                    float body_x=ConstantsService.getWidth(LevelObject.Type.OPENED_DOOR)/2, body_y=ConstantsService.getHeight(LevelObject.Type.OPENED_DOOR);
                    Vector2[] vec ={
                            new Vector2(-body_x, -body_y + ConstantsService.getHeight(LevelObject.Type.CLOSED_DOOR)/2),
                            new Vector2(-body_x,  ConstantsService.getHeight(LevelObject.Type.CLOSED_DOOR)/2),
                            new Vector2(body_x, -body_y + ConstantsService.getHeight(LevelObject.Type.CLOSED_DOOR)/2),
                            new Vector2(body_x, ConstantsService.getHeight(LevelObject.Type.CLOSED_DOOR)/2)
                    };

                    shape.set(vec);

                    return shape;
                case CLOSED_DOOR:

                    shape.setAsBox(ConstantsService.getWidth(LevelObject.Type.CLOSED_DOOR)/2,
                            ConstantsService.getHeight(LevelObject.Type.CLOSED_DOOR)/2);

                    return shape;
                case BUTTON:
                    shape = new PolygonShape();
                    shape.setAsBox( ConstantsService.getWidth(type) / 2,
                            ConstantsService.getHeight(type) / 3f);
                    return shape;
            default:
                shape = new PolygonShape();
                shape.setAsBox( ConstantsService.getWidth(type) / 2,
                                ConstantsService.getHeight(type) / 2);
                return shape;
        }
    }
    public static Shape getButtonSensorShape() {
        PolygonShape shape = new PolygonShape();
        shape.setAsBox( ConstantsService.getWidth(LevelObject.Type.BUTTON) / 2.5f,
                ConstantsService.getHeight(LevelObject.Type.BUTTON) / 2.5f);
        return shape;
    }

    public static FixtureDef getFixtureDef(LevelObject.Type type) {
        float friction,density,restitution;

        switch(type) {
            case CUBE:
                friction = 1f;  density = 0.15f;   restitution = 0.1f;
                break;
            case BUTTON:
                friction = 1f;  density = 100f;   restitution = 0f;
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
        fixtureDef.density = 0.70f;
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
            case OPENED_DOOR:
            case CLOSED_DOOR:
            case DOOR:
            case FINISH:
                return BodyDef.BodyType.StaticBody;
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
        shape.setAsBox(ConstantsService.PF_SENXORX, ConstantsService.PF_SENSORY, new Vector2(0,ConstantsService.PF_SENSOR_VECY),0);
        return shape;
    }

}
