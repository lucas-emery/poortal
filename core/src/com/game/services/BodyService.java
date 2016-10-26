package com.game.services;

import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.models.LevelObject;


public class BodyService {
    public static Float getDensity(LevelObject.Type type){
        switch (type){
            case CUBE:
                return 1f;
            case BUTTON:
                return 0f;
            case PORTAL_BLUE:
                return 0f;
            case PORTAL_ORANGE:
                return 0f;
            case WALL:
                return 10f;
            default:
                return 1f;
        }
    }
    public static Shape getShape(LevelObject.Type type){
        switch (type){
            /*case CUBE:
                PolygonShape shape = new PolygonShape();
                shape.setAsBox(AssetsService.getSprite(type).getHeight()/2,AssetsService.getSprite(type).getWidth()/2);
                return shape;
            case case BUTTON:
                break;
            case PORTAL_BLUE:
                break;
            case PORTAL_ORANGE:
                break;
            case WALL:
                break;*/
            default:
                PolygonShape shape = new PolygonShape();
                shape.setAsBox( (AssetsService.getSprite(type).getHeight()/2) * ConstantsService.PIXELS_TO_METERS,
                                (AssetsService.getSprite(type).getWidth()/2) * ConstantsService.PIXELS_TO_METERS );

                return shape;
        }
    }
    public static Float getRestitution(LevelObject.Type type){
        switch(type){
            case CUBE:
                return 0f;
            case BUTTON:
                return 0f;
            case PORTAL_BLUE:
                return 0f;
            case PORTAL_ORANGE:
                return 0f;
            /*case WALL:
                break;*/
            default:
                return 0f;
        }
    }

    public static boolean isDynamic(LevelObject.Type type){
        switch(type) {
            case CUBE:
                return true;
            case BUTTON:
                return false;
            case PORTAL_BLUE:
                return false;
            case PORTAL_ORANGE:
                return false;
            /*case WALL:
                break;*/
            default:
                return true;
        }
    }
}
