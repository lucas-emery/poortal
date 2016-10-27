package com.game.controllers;

import com.game.models.Player;
import com.game.services.ConstantsService;

public class PlayerController {

    private Player player;

    public PlayerController(){
        player = new Player();
    }

    protected void jump(){
        player.changeVelocity(0, ConstantsService.PLAYER_JUMP_VALUE);
    }

    protected void moveLeft(){
        player.changeVelocity(-ConstantsService.PLAYER_MOVE_VALUE, 0);
    }

    protected void moveRight(){
        player.changeVelocity(ConstantsService.PLAYER_MOVE_VALUE, 0);
    }

    protected void firePortal(int button){

    }
}
