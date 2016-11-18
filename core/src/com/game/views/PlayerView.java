package com.game.views;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.game.controllers.Controller;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;


public class PlayerView {

    private Player player;
    private Skeleton skeleton;
    private Rectangle dimensions;
    private Vector2 mouseVector, playerVector, playerToMouseVector;
    private Bone head, torso, rightArm, rightForearm, leftArm, leftForearm;

    public PlayerView(Player player) {
        this.player = player;
        skeleton = new Skeleton(AssetsService.getPlayerSkeletonData());
        dimensions = AssetsService.getPlayerDimensions();

        playerVector = new Vector2();
        mouseVector = new Vector2();
        playerToMouseVector = new Vector2();

        head = skeleton.findBone("head");
        torso = skeleton.findBone("torso");
        rightArm = skeleton.findBone("arm_right");
        rightForearm = skeleton.findBone("lowerarm_right");
        leftArm = skeleton.findBone("arm_left");
        leftForearm = skeleton.findBone("lowerarm_left");
    }

    public void updateAimingPoint(int x, int y) {
        mouseVector.set(Controller.getGraphicsCoords(new Vector2(x, y)));
    }

    public void updateAimingPose() {
        playerVector.set(player.getPosition()).scl(ConstantsService.METERS_TO_PIXELS);

        playerToMouseVector.set(mouseVector.x - playerVector.x, mouseVector.y - playerVector.y);

        float theta = ConstantsService.CARTESIAN_VERSOR_Y.angle(playerToMouseVector);

        if(theta > 0){
            player.setLookingLeft(true);
            theta *= -1;
        }
        else
            player.setLookingLeft(false);

        theta += 90;

        float   deltaHead = 0,
                deltaTorso = 0,
                deltaLeftFore = 0,
                deltaLeftArm = 0,
                deltaRightFore = 0,
                deltaRightArm = 0;

        if(theta < -80) {
            deltaLeftFore = -40;
            deltaTorso = -40;
        }
        else if(theta < -40) {
            float delta = theta + 40;
            deltaLeftFore = -40*0.8f + delta*0.2f;
            deltaTorso = -40*0.2f + delta*0.8f;
        }
        else if(theta < 0) {
            deltaLeftFore = theta * 0.8f;
            deltaTorso = theta * 0.2f;
        }
        else if(theta < 60) {
            deltaLeftArm = theta;
        }
        else {
            float delta = theta - 60;
            deltaLeftArm = 60 + delta*0.8f;
            deltaRightFore = delta*0.7f;
            deltaRightArm = delta*-1.3f;
            deltaHead = delta*0.4f;
            deltaTorso = delta*0.2f;
        }

        head.setRotation(deltaHead + head.getData().getRotation());
        torso.setRotation(deltaTorso + torso.getData().getRotation());
        leftForearm.setRotation(deltaLeftFore + leftForearm.getData().getRotation());
        leftArm.setRotation(deltaLeftArm + leftArm.getData().getRotation());
        rightForearm.setRotation(deltaRightFore + rightForearm.getData().getRotation());
        rightArm.setRotation(deltaRightArm + rightArm.getData().getRotation());


    }

    public Skeleton getUpdatedSkeleton() {
        Vector2 position = player.getPosition();
        skeleton.setPosition(position.x / ConstantsService.PIXELS_TO_METERS, (position.y / ConstantsService.PIXELS_TO_METERS) - dimensions.getHeight()/2);
        player.getState().apply(skeleton);
        skeleton.setFlipX(player.isLookingLeft());
        skeleton.updateWorldTransform();

        return skeleton;
    }

    public Vector2 getAimingPoint() {
        return mouseVector.cpy();
    }
}
