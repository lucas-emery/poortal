package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.LevelObject;
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

    public void updatePlayerCollisionState(boolean grounded, Fixture vicinity) {
        player.setGrounded(grounded);
        player.setVicinity(vicinity);
    }

    public void interact() {
        if(player.getVicinity()!=null){
            if(player.getVicinity().getUserData().equals("CUBE")){
                if(!player.isHolding()) {
                    System.out.println("pickedup");
                    player.setHolding(true);
                }
                else{
                    System.out.println("letgo");
                    player.setHolding(false);
                }
            }
            System.out.println("interacted");
        }
    }
}
