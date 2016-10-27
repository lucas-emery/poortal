package com.game.controllers;

import com.game.models.Player;
import com.game.services.ConstantsService;

public class PlayerController {

    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    protected void jump(){
        //Esto es un poco nefasto porahi
        if(player.getBody().getLinearVelocity().y==0)
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
