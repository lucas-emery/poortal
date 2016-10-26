package com.game.views;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.models.Player;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;


public class PlayerView {

    Player player;
    Skeleton skeleton;
    Rectangle dimensions;

    public PlayerView(Player player) {
        this.player = player;
        skeleton = new Skeleton(AssetsService.getPlayerSkeletonData());
        dimensions = AssetsService.getPlayerDimensions();
    }

    public Skeleton getUpdatedSkeleton() {
        Vector2 position = player.getPosition();
        skeleton.setPosition(position.x / ConstantsService.PIXELS_TO_METERS, (position.y / ConstantsService.PIXELS_TO_METERS) - dimensions.getHeight());
        player.getState().apply(skeleton);
        skeleton.updateWorldTransform();

        return skeleton;
    }
}
