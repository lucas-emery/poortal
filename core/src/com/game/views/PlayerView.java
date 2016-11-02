package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.controllers.Controller;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;


public class PlayerView {

    private Player player;
    private Skeleton skeleton;
    private Rectangle dimensions;
    private Vector2 mouseVector, playerVector, playerToMouseVector;

    public PlayerView(Player player) {
        this.player = player;
        skeleton = new Skeleton(AssetsService.getPlayerSkeletonData());
        dimensions = AssetsService.getPlayerDimensions();

        playerVector = new Vector2();
        mouseVector = new Vector2();
        playerToMouseVector = new Vector2();
    }

    public void updatePortalArm(int x, int y) {
        playerVector.x = player.getBody().getPosition().x * ConstantsService.METERS_TO_PIXELS; //* screenConversionFactor.x;
        playerVector.y = player.getBody().getPosition().y * ConstantsService.METERS_TO_PIXELS; //* screenConversionFactor.y;

        mouseVector = Controller.getGraphicsCoords(new Vector2(x, y));

        playerToMouseVector.set(mouseVector.x - playerVector.x, mouseVector.y - playerVector.y);

        float theta = ConstantsService.cartesianVersorY.angle(playerToMouseVector);

        if (player.isFlipped())
            theta *= -1.0f;

        skeleton.findBone("arm_right").setRotation(theta);
    }

    public Skeleton getUpdatedSkeleton() {
        Vector2 position = player.getPosition();
        skeleton.setPosition(position.x / ConstantsService.PIXELS_TO_METERS, (position.y / ConstantsService.PIXELS_TO_METERS) - dimensions.getHeight()/2);
        player.getState().apply(skeleton);
        skeleton.setFlipX(player.isFlipped());
        skeleton.updateWorldTransform();

        return skeleton;
    }
}
