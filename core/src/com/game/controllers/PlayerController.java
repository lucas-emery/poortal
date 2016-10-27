package com.game.controllers;

import com.game.models.Player;
import com.game.services.ConstantsService;

public class PlayerController {

    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    protected void jump(){
        if(player.getBody().getLinearVelocity().y==0)
            player.changeVelocity(0, ConstantsService.PLAYER_JUMP_VALUE);
    }

    protected void moveHorizontal(float directionScalar){
        player.changeVelocity(directionScalar * ConstantsService.PLAYER_MOVE_VALUE, 0);
    }

    protected void firePortal(int button){

    }
}
