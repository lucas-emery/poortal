package com.game.views;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.models.Player;
import com.game.services.AssetsService;


public class PlayerView {

    Player player;
    Skeleton skeleton;

    public PlayerView(Player player, AssetsService assetsService) {
        this.player = player;
        skeleton = new Skeleton(assetsService.getPlayerSkeletonData());
    }

    public void render(SpriteBatch batch, SkeletonRenderer skeletonRenderer) {
        skeletonRenderer.draw(batch, skeleton);
    }
}
