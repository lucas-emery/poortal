package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.models.Collider;
import com.game.models.LevelObject;
import com.game.models.Player;
import com.game.services.BodyService;
import com.game.models.Portal;
import com.game.services.ConstantsService;
import com.game.views.PlayerView;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.math.MathContext;

/**
 *
 */
public class PlayerController {

    public static Vector2 ray = new Vector2(0,0);  // sacar esto -jg
    public static Vector2 rayPos = new Vector2(0,0); // esto tmb
    private static PlayerView playerView;
    private static Player player;

    /**
     *This method returns the player in PlayerController.
     * @return the player from PlayerController
     */
    public static Player getPlayer(){
        return player;
    }

    /**
     *This method sets the player view associated with the player
     * into the PlayerController
     * @param receivedPlayerView is the PlayerView which will be set.
     */
    public static void setPlayerView(PlayerView receivedPlayerView){
        playerView = receivedPlayerView;
    }

    /**
     *This method will set the player into the PlayerController
     * @param receivedPlayer the Player which will be set.
     */
    public static void setPlayer(Player receivedPlayer){
        player = receivedPlayer;
    }

    /**
     *Method which if the player is grounded it will allow
     * it to jump by apliyng a force
     */
    protected static void jump(){
        if(player.isGrounded())
            player.applyForceToCenter(0, ConstantsService.FORCE* ConstantsService.JUMPCONSTANT, true);
    }

    /**
     *
     * @param positive
     */
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

    /**
     *
     * @param animation
     */
    public static void setAnimation(String animation){
        player.setAnimation(animation);
    }

    /**
     *
     * @param deltaTime
     */
    public static void update(float deltaTime) {
        playerView.updateAimingPose();
        player.update(deltaTime);
    }

    /**
     *
     * @param x
     * @param y
     */
    protected static void updateAiming(int x, int y){
        playerView.updateAimingPoint(x,y);
    }

    /**
     *
     * @param clickPos
     * @param portalType
     */
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

    /**
     *
     * @param grounded
     * @param vicinity
     */
    public static void updatePlayerCollisionState(boolean grounded, Fixture vicinity) {
        player.setGrounded(grounded);
        player.setVicinity(vicinity);
    }

    /**
     *
     */
    public static void interact() {
        if(!player.isHolding()){
            if(player.getVicinity()!=null){
                if(player.isFacingCube()) {
                    player.setHolding(true);
                }
            }
        }
        else
            player.setHolding(false);
    }
}
