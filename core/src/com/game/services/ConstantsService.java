package com.game.services;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;

public class ConstantsService {

    public static final float FORCEINGROUND = 1;

    public static float FORCEINAIR = 0.1f;

    //put the width in meters and it will convert to pixels in getSprite()
    public static Float getWidth(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return 1f;
            case BUTTON:
                return 68*PIXELS_TO_METERS;
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
                return 2.5f;
            case OPENED_DOOR:
            case CLOSED_DOOR:
                return 1.6f;
            case FINISH:
                return 0.5f;
            default:
                return 1f;
        }
    }

    //put the height in meters and it will convert to pixels in getSprite()
    public static Float getHeight(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return 1f;
            case BUTTON:
                return 20*PIXELS_TO_METERS;
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
                return 2.5f;
            case OPENED_DOOR:
            case CLOSED_DOOR:
                return 3.4f;
            case FINISH:
                return 2.2f;
            default:
                return 1f;
        }
    }

    public static int getCantSprites(LevelObject.Type type){
        switch (type) {
            case BUTTON:
            case OPENED_DOOR:
            case CLOSED_DOOR:
                return 1;
            //case PLATFORM:
            //    return BodyDef.BodyType.KinematicBody;
            default:
                return 1;
        }

    }

    public static final Vector2 CARTESIAN_VERSOR_Y = new Vector2(0, 1);

    public static final float JUMPCONSTANT = 8f;

    public static final float METERS_TO_PIXELS = 25f;

    public static final float PLAYER_RUN_CAP = 6.0f;

    public static final float FORCE = 50f;

    public static final float PIXELS_TO_METERS = 1/METERS_TO_PIXELS;

    public static final float RAD_TO_DEG = (float)(180/ Math.PI);

    public static final float PLAYER_HEIGHT = 1.9f;

    public static final float PLAYER_WIDTH = 1f;

    public static final float WORLD_STEP = 1/60f;

    public static final int GRAPHICS_HEIGHT = 550;

    public static final int GRAPHICS_WIDTH = 977;

    public static final int WORLD_HEIGHT = MathUtils.ceil(GRAPHICS_HEIGHT * PIXELS_TO_METERS);

    public static final int WORLD_WIDTH = MathUtils.ceil(GRAPHICS_WIDTH * PIXELS_TO_METERS);

    public static final float BUTTON_WIDTH = 1.0f;

    public static final  float BUTTON_HEIGHT = 0.4f;

    public static final float DOOR_WIDTH = 40f;
    public static final float DOOR_HEIGHT = 1.0f;

    public static final int MAX_LEVEL = 2;

}