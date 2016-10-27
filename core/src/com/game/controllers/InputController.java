package com.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


public class InputController implements InputProcessor{

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

    @Override
    public boolean keyDown(int keycode){
        switch (keycode){
            case Input.Keys.W:
                playerController.jump();
                break;
            case Input.Keys.A:
                playerController.moveHorizontal(-1.0f);
                break;
            case Input.Keys.D:
                playerController.moveHorizontal(1.0f);
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
        return false;
    }
}
