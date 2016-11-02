package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.models.LevelObject;
import com.game.models.Player;
import com.game.services.BodyService;
import com.game.models.Portal;
import com.game.services.ConstantsService;
import com.game.views.PlayerView;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.math.MathContext;

public class PlayerController {

    public static Vector2 ray = new Vector2(0,0);  // sacar esto -jg
    public static Vector2 rayPos = new Vector2(0,0); // esto tmb
    private static PlayerView playerView;
    private static Player player;

    public static Player getPlayer(){
        return player;
    }

    public static void setPlayerView(PlayerView receivedPlayerView){
        playerView = receivedPlayerView;
    }

    public static void setPlayer(Player receivedPlayer){
        player = receivedPlayer;
    }

    protected static void jump(){
        if(player.isGrounded())
            player.applyForceToCenter(0, ConstantsService.FORCE* ConstantsService.JUMPCONSTANT, true);
    }

    protected static void moveHorizontal(boolean positive) {
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

    public static void setAnimation(String animation){
        player.setAnimation(animation);
    }

    protected static void movePortalArm(int x, int y){
        playerView.updatePortalArm(x,y);
    }

    public static void firePortal(Vector2 clickPos, Portal.Type portalType){
        Vector2 playerPos = player.getPosition().cpy();

        clickPos.scl(ConstantsService.PIXELS_TO_METERS);
        clickPos.sub(playerPos);
        clickPos.setLength2(ConstantsService.WORLD_HEIGHT * ConstantsService.WORLD_HEIGHT +
                ConstantsService.WORLD_WIDTH * ConstantsService.WORLD_WIDTH);
        clickPos.add(playerPos);
        ray = clickPos.cpy();
        rayPos = playerPos.cpy();

        PortalController.firePortal(playerPos, clickPos, portalType);
    }

    public static void updatePlayerCollisionState(boolean grounded, Fixture vicinity) {
        player.setGrounded(grounded);
        player.setVicinity(vicinity);
    }

    public static void interact() {
        if(player.getVicinity()!=null){
            if((Integer)(player.getVicinity().getUserData())==ConstantsService.ColliderType.CUBE.val()){
                if(!player.isHolding()) {
                    player.setHolding(true);
                }
                else{
                    player.setHolding(false);
                }
            }
        }
    }

}
