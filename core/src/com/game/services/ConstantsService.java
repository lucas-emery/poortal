package com.game.services;


import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;

/**
 * This service contains all the parameters used within the program
 */
public class ConstantsService {

    public static final float FORCEINGROUND = 1;

    public static float FORCEINAIR = 0.1f;

    public static Float getWidth(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return 1f;
            case BUTTON:
                return 68*PIXELS_TO_METERS;
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
                return 2.5f;
            case DOOR:
            case CLOSED_DOOR:
            case OPENED_DOOR:
                return 1f;
            case FINISH:
                return 0.5f;
            default:
                return 1f;
        }
    }

    public static Float getHeight(LevelObject.Type type) {
        switch (type) {
            case CUBE:
                return 1f;
            case BUTTON:
                return 20*PIXELS_TO_METERS;
            case PORTAL_BLUE:
            case PORTAL_ORANGE:
                return 2.5f;
            case DOOR:
            case CLOSED_DOOR:
                return 10.6f;
            case OPENED_DOOR:
                return 2f;
            case FINISH:
                return 2.2f;
            default:
                return 1f;
        }
    }

    public static final float PF_SENXORX = 0.435f;

    public static final float PF_SENSORY = 0.2f;

    public static final float PF_SENSOR_VECY= -0.75f;

    public static final Vector2 CARTESIAN_VERSOR_Y = new Vector2(0, 1);

    public static final float JUMPCONSTANT = 8f;

    public static final float METERS_TO_PIXELS = 25f;

    public static final float PLAYER_RUN_CAP = 6.0f;

    public static final float FORCE = 50f;

    public static final float MAX_MOTOR_TORQUE = 10;

    public static final float TIMESCALE = 1.5f;

    public static final float PORTAL_RIM_RADIUS = 0.02f;

    public static final float JOINT_LENGTH=1.7f;

    public static final float PIXELS_TO_METERS = 1/METERS_TO_PIXELS;

    public static final float RAD_TO_DEG = (float)(180/ Math.PI);

    public static final float DEG_TO_RAD = (float)(1/(RAD_TO_DEG));

    public static final float PLAYER_HEIGHT = 1.9f;

    public static final float PLAYER_WIDTH = 1f;

    public static final float PLAYER_GUN_OFFSET = 0.2f;

    public static final float WORLD_STEP = 1/60f;

    public static final int GRAPHICS_HEIGHT = 550;

    public static final int GRAPHICS_WIDTH = 977;

    public static final int WORLD_HEIGHT = MathUtils.ceil(GRAPHICS_HEIGHT * PIXELS_TO_METERS);

    public static final int WORLD_WIDTH = MathUtils.ceil(GRAPHICS_WIDTH * PIXELS_TO_METERS);

    public static final  float BUTTON_HEIGHT = 0.4f;

    public static final int MAX_LEVEL = 3;

}