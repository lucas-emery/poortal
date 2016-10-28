package com.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.game.services.VariablesService;


public class InputController implements InputProcessor{

    protected static boolean aIsPressed;
    protected static boolean dIsPressed;

    private PlayerController playerController;

    public InputController(PlayerController playerController){
        this.playerController = playerController;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        playerController.firePortal(button);
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
    public void update(){
        if (InputController.aIsPressed)
            playerController.moveHorizontal(false);     // 	CAMBIAR BOOLEANO
        if (InputController.dIsPressed)					//  POR RIGHT AND LEFT
            playerController.moveHorizontal(true);		// UBICADO EN ALGUN SERVICE
    }

    @Override
    public boolean keyDown(int keycode){
        switch (keycode){
            case Input.Keys.W:
                playerController.jump();
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
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
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
