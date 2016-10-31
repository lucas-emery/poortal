package com.game.services;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;

public class ConstantsService {

    public static final float FORCEINGROUND = 1;
    public static float FORCEINAIR = 0.2f;

    //put the width in meters and it will convert to pixels in getSprite()
    public static Float getWidth(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return 1f;
            case BUTTON:
                return 0.25f;
            case PORTAL_BLUE:
                return 1f;
            case PORTAL_ORANGE:
                return 1f;
            /*case WALL:
                break;*/
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
                return 0.25f;
            case PORTAL_BLUE:
                return 1f;
            case PORTAL_ORANGE:
                return 1f;
            /*case WALL:
                break;*/
            default:
                return 1f;
        }
    }

    public static final Vector2 cartesianVersorY = new Vector2(0.0f, 1000.0f);

    public static final float JUMPCONSTANT = 8f;

    public static final float METERS_TO_PIXELS = 25f;

    public static final float PLAYER_JUMP_VALUE = 6.0f;

    public static final float PLAYER_RUN_CAP = 6.0f;

    public static final float FORCE = 50f;

    public static final float SPEEDDELTA = 0.025f;

    public static final float PIXELS_TO_METERS = 1/METERS_TO_PIXELS;

    public static final float RAD_TO_DEG = (float)(180/ Math.PI);

    public static final float PLAYER_HEIGHT = 1.9f;

    public static final float WORLD_STEP = 1/60f;

    public static final int GRAPHICS_HEIGHT = 550;

    public static final int GRAPHICS_WIDTH = 977;

    public static final int WORLD_HEIGHT = MathUtils.ceil(GRAPHICS_HEIGHT * PIXELS_TO_METERS);

    public static final int WORLD_WIDTH = MathUtils.ceil(GRAPHICS_WIDTH * PIXELS_TO_METERS);

}
