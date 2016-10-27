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
        if(player.getBody().getLinearVelocity().y==0)
            player.changeVelocity(0, ConstantsService.PLAYER_JUMP_VALUE);
    }

    protected void moveHorizontal(float directionScalar){
        if (player.getBody().getLinearVelocity().y != 0)
            directionScalar *= 0.5; // Aerial move damping

        if (directionScalar > 0 && player.getVelocity().x < ConstantsService.PLAYER_RUN_CAP)
            player.changeVelocity(directionScalar * ConstantsService.PLAYER_MOVE_VALUE, 0);

        else if (directionScalar < 0 && player.getVelocity().x > -ConstantsService.PLAYER_RUN_CAP)
            player.changeVelocity(directionScalar * ConstantsService.PLAYER_MOVE_VALUE, 0);
    }

    protected void firePortal(int button){

    }
}
