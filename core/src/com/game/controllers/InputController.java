package com.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;
import com.game.models.Portal;

/**
 * The InputController class is tasked with handling the keyboard/mouse
 * inputs and sends messages to other classes in order to react to the events.
 * @author Juan Godfrid
 */

public class InputController implements InputProcessor{

    protected static boolean aIsPressed;
    protected static boolean dIsPressed;
    public static boolean spaceispressed=false;

    /**
     /**
     * This method is required due to the implementation of the InputProcessor
     * interface.
     * @return false since it is not used for the program.
     */
    @Override

    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     *
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Portal.Type portalType;
        if(button == 0)
            portalType = LevelObject.Type.PORTAL_BLUE;
        else
            portalType = LevelObject.Type.PORTAL_ORANGE;

        Vector2 click = Controller.getGraphicsCoords(new Vector2(screenX, screenY));

        PlayerController.firePortal(click, portalType);
        return false;
    }

    /**
     *This method is required due to the implementation of the InputProcessor
     * interface.
     * @param screenX
     * @param screenY
     * @param pointer
     * @param button
     * @return false since it is not used for the program.
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     *This method is required due to the implementation of the InputProcessor
     * interface
     * @param character
     * @return false since it is not used for the program.
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     *This method is required due to the implementation of the InputProcessor
     * interface.
     * @param amount
     * @return false since it is not used for the program.
     */
    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    /**
     *This method updates the player's movement according to
     * which key is being pressed at the moment
     */
    public static void update(){
        boolean isPressed=false;
        if (InputController.aIsPressed){
            PlayerController.moveHorizontal(false);
            isPressed=true;
        }
        if (InputController.dIsPressed){
            PlayerController.moveHorizontal(true);
            isPressed=true;
        }
        if (!isPressed)
	       PlayerController.setAnimation(null);
    }

    /**
     * This method listens for whenever a key is pressed and sets the conditions
     * with which the key press corresponds.
     * @param keycode
     * @return
     */
    @Override
    public boolean keyDown(int keycode){
        switch (keycode){
            case Input.Keys.W:
                PlayerController.jump();
                break;
            case Input.Keys.A:
                aIsPressed = true;
                break;
            case Input.Keys.D:
                dIsPressed = true;
                break;
            case Input.Keys.E:
                PlayerController.interact();
                break;
            case Input.Keys.SPACE:
                spaceispressed=true;
                break;
        }
        return false;
    }

    /**
     * This method listens for the mouse input and updates
     * the aiming position of the player.
     * @param screenX
     * @param screenY
     * @return
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        PlayerController.updateAiming(screenX,screenY);
        return false;
    }

    /**
     * This method listens for whenever a key is released
     * and sets the corresponding variable's value.
     * @param keycode
     * @return
     */
    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                aIsPressed = false;
            case Input.Keys.D:
                dIsPressed = false;
                break;
            case Input.Keys.SPACE:
                spaceispressed=false;
                break;
        }
        return false;
    }

    /**
     * This method resets the status of the keys.
     */
    public static void reset() {
        aIsPressed = false;
        dIsPressed = false;
    }
}
