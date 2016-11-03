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

    public static final Vector2 cartesianVersorY = new Vector2(0, 1);

    public static final float JUMPCONSTANT = 8f;

    public static final float METERS_TO_PIXELS = 25f;

    public static final float PLAYER_RUN_CAP = 6.0f;

    public static final float FORCE = 50f;

    public static final float PIXELS_TO_METERS = 1/METERS_TO_PIXELS;

    public static final float RAD_TO_DEG = (float)(180/ Math.PI);

    public static final float PLAYER_HEIGHT = 1.9f;

    public static final float WORLD_STEP = 1/60f;

    public static final int GRAPHICS_HEIGHT = 550;

    public static final int GRAPHICS_WIDTH = 977;

    public static final int WORLD_HEIGHT = MathUtils.ceil(GRAPHICS_HEIGHT * PIXELS_TO_METERS);

    public static final int WORLD_WIDTH = MathUtils.ceil(GRAPHICS_WIDTH * PIXELS_TO_METERS);

    public enum ColliderType {
        CUBE(1<<0),
        PSENSORRIGHT(1<<1),
        PSENSORLEFT(1<<2),
        PSENSORFOOT(1<<3),
        PSENSORBODY(1<<4),
        BUTTON(1<<5),
        PLATSENSOR(1<<6),
        PLATBODY(1<<7),
        PORTAL(1<<8),
        WALL(1<<9),
        DOOR(1<<10);

        private int value;

        private ColliderType(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

}