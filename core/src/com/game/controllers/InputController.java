package com.game.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;


public class InputController implements InputProcessor{

    private PlayerController playerController;

    public InputController(){
        playerController = new PlayerController();
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
    public boolean keyDown(int keycode) {
        switch(keycode){
            case Input.Keys.W:
                playerController.jump();
            case Input.Keys.A:
                playerController.moveLeft();
            case Input.Keys.D:
                playerController.moveRight();
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
