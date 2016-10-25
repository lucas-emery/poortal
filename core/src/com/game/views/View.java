package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.models.Model;

import java.util.HashSet;

/**
 * Created by juan on 24/10/16.
 */
public class View {

    private Model model;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;
    private SkeletonRenderer skeletonRenderer;
    private HashSet<GameObjectView> levelObjectsViews;
    private PlayerView playerView;

    public View(HashSet<GameObjectView> levelObjectsViews, PlayerView playerView) {
        this.levelObjectsViews = levelObjectsViews;
        this.playerView = playerView;
        batch = new SpriteBatch();
        skeletonRenderer = new SkeletonRenderer();
    }

    public void render(){
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        playerView.render(batch, skeletonRenderer);
        for(GameObjectView view : levelObjectsViews)
            view.render(batch);
        batch.end();
    }
}
