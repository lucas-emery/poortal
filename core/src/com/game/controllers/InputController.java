package com.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;
import com.game.models.Portal;
import com.game.services.VariablesService;
import com.game.views.PlayerView;
import com.game.views.View;


public class InputController implements InputProcessor{

    protected static boolean aIsPressed;
    protected static boolean dIsPressed;

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

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

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    public static void update(){
        boolean isPressed=false;
        if (InputController.aIsPressed){
            PlayerController.moveHorizontal(false);     // 	CAMBIAR BOOLEANO
            isPressed=true;
        }
        if (InputController.dIsPressed){
            PlayerController.moveHorizontal(true);	   //  POR RIGHT AND LEFT
            isPressed=true;
        }
        if (!isPressed)
	       PlayerController.setAnimation(null);                                             // UBICADO EN ALGUN SERVICE
    }

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
            case Input.Keys.G:
                VariablesService.SHOW_FPS = !VariablesService.SHOW_FPS;
                break;
            case Input.Keys.F:
                VariablesService.SHOW_FULLSCREEN = !VariablesService.SHOW_FULLSCREEN;
                break;
            case Input.Keys.E:
                PlayerController.interact();
                break;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        PlayerController.movePortalArm(screenX,screenY);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.A:
                aIsPressed = false;
            case Input.Keys.D:
                dIsPressed = false;
        }
        return false;
    }
}
