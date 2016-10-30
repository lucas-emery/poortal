package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.models.Player;
import com.game.services.BodyService;
import com.game.models.Portal;
import com.game.services.ConstantsService;
import com.game.views.PlayerView;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

public class PlayerController {

    private Player player;
    private PlayerView playerView;

    public PlayerController(Player player,PlayerView playerView){
        this.player = player;
        this.playerView = playerView;
    }

    protected void jump(){
        if(player.isGrounded())
            player.applyForceToCenter(0, ConstantsService.FORCE* ConstantsService.JUMPCONSTANT, true);
    }

    protected void moveHorizontal(boolean positive) {
        float multiplier =ConstantsService.FORCEINGROUND;
        if(!player.isGrounded()){
            multiplier=ConstantsService.FORCEINAIR;
        }
        if (positive){
            if ((player.getVelocity().x)< ConstantsService.PLAYER_RUN_CAP)
                    player.applyForceToCenter(multiplier*ConstantsService.FORCE, 0, true);
            setAnimation("RIGHT");
        }
        else {
            if (player.getVelocity().x > -ConstantsService.PLAYER_RUN_CAP)
                player.applyForceToCenter(-(multiplier*ConstantsService.FORCE), 0, true);
            setAnimation("LEFT");
        }
    }

    public void setAnimation(String animation){
        player.setAnimation(animation);
    }

    protected void movePortalArm(int x, int y){
        playerView.updatePortalArm(x,y);
    }

    public void firePortal(Vector2 clickPos, Portal.Type portalType) {
        PortalController.firePortal(player.getPosition().cpy(), clickPos.cpy(), portalType);
    }

    public void updatePlayerCollisionState(boolean grounded, boolean vicinity) {
        player.setGrounded(grounded);
        player.setVicinity(vicinity);
    }
}
