package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.Player;
import com.game.models.Portal;
import com.game.services.ConstantsService;
import com.game.views.PlayerView;

import java.math.MathContext;

/**
 * The InputController class is tasked with handling the Player model-view
 * interactions and sends messages to other classes in order to react to the events.
 */
public class PlayerController {

    private static PlayerView playerView;
    private static Player player;


    public static void initialize() {
        player = new Player();
        playerView = new PlayerView(player);
    }

    /**
     * This method will reset the state of the player.
     */
    public static void reset() {
        player.resetState();
    }

    /**
     *This method returns the player in PlayerController.
     * @return the player from PlayerController
     */
    public static Player getPlayer(){
        return player;
    }

    /**
     *This method returns the playerView in PlayerController.
     * @return the player from PlayerController
     */
    public static PlayerView getPlayerView() {
        return playerView;
    }

    /**
     *Method which if the player is grounded it will allow
     * it to jump by applying a force
     */
    protected static void jump(){
        if(player.isGrounded())
            player.applyForceToCenter(0, ConstantsService.FORCE* ConstantsService.JUMPCONSTANT, true);
    }

    /**
     *This method will move the player horizontally depending
     * on if he is currently on the ground or not.
     * @param positive the direction of motion on our axis,
     *                 right is positive and left is negative.
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
     * Sets an animation state for the player.
     * @param animation the animation state string
     *                  representation to be set.
     */
    public static void setAnimation(String animation){
        player.setAnimation(animation);
    }

    /**
     * Updates the player's model and view
     * @param deltaTime
     */
    public static void update(float deltaTime) {
        playerView.updateAimingPose();
        player.update(deltaTime);
    }

    /**
     * This method will update the point to
     * which the player's view is aiming.
     * @param x
     * @param y
     */
    protected static void updateAiming(int x, int y){
        playerView.updateAimingPoint(x,y);
    }

    /**
     *Method which the player calls to fire a portal and then the
     * portalController is tasked with handling the portal.
     * @param clickPos The vector of the click position.
     * @param portalType either an orange or blue portal.
     */
    public static void firePortal(Vector2 clickPos, Portal.Type portalType){
        Vector2 playerPos = player.getPosition().cpy();

        clickPos.scl(ConstantsService.PIXELS_TO_METERS);
        clickPos.sub(playerPos);
        clickPos.setLength2(ConstantsService.WORLD_HEIGHT * ConstantsService.WORLD_HEIGHT +
                ConstantsService.WORLD_WIDTH * ConstantsService.WORLD_WIDTH);
        clickPos.add(playerPos);

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

    public static float getAimingAngle() {
        Vector2 gunPos = player.getPosition().add(0, ConstantsService.PLAYER_GUN_OFFSET).scl(ConstantsService.METERS_TO_PIXELS);
        Vector2 aimingPoint = playerView.getAimingPoint();

        Vector2 aimingVector = new Vector2(aimingPoint.x - gunPos.x, aimingPoint.y - gunPos.y);

        return aimingVector.angleRad(ConstantsService.CARTESIAN_VERSOR_Y);
    }
}
