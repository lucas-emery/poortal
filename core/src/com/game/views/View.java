package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.models.Model;
import com.game.services.ConstantsService;

import java.util.HashSet;

/**
 * Created by juan on 24/10/16.
 */
public class View {

    private Model model;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;
    private SkeletonRenderer skeletonRenderer;
    private Box2DDebugRenderer debugRenderer;
    private Matrix4 debugMatrix;
    private HashSet<LevelObjectView> levelObjectsViews;
    private PlayerView playerView;

    public View(Model model, HashSet<LevelObjectView> levelObjectsViews, PlayerView playerView) {
        this.model = model;
        this.levelObjectsViews = levelObjectsViews;
        this.playerView = playerView;
        batch = new SpriteBatch();
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1366, 768, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
    }

    public void render(){
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        skeletonRenderer.draw(batch, playerView.getUpdatedSkeleton());
        for(LevelObjectView view : levelObjectsViews)
            view.getUpdatedSprite().draw(batch);
        batch.end();

        debugRenderer.render(model.getWorld(), camera.combined);
    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}
