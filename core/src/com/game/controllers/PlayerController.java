package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.game.models.Player;
import com.game.services.ConstantsService;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class PlayerController {

    private Player player;

    public PlayerController(Player player){
        this.player = player;
    }

    protected void jump(){
        if(player.isGrounded())
            player.changeVelocity(0, ConstantsService.PLAYER_JUMP_VALUE);
    }

    protected void moveHorizontal(boolean positive) {
        float multiplier =1;
        if(!player.isGrounded()){
            multiplier=ConstantsService.FORCEINAIR;
        }
        if (positive){
            if ((player.getVelocity().x)< ConstantsService.PLAYER_RUN_CAP)
                    player.applyForceToCenter(multiplier*ConstantsService.FORCE, 0, true);
        }
        else {
            if (player.getVelocity().x > -ConstantsService.PLAYER_RUN_CAP)
                player.applyForceToCenter(-(multiplier*ConstantsService.FORCE), 0, true);
        }
    }

    protected void firePortal(int button){

    }
}
