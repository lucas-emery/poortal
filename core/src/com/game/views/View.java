package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.controllers.LevelController;
import com.game.controllers.PlayerController;
import com.game.models.Model;
import com.game.services.AssetsService;
import com.game.services.VariablesService;

import java.util.LinkedHashSet;

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
    private LinkedHashSet<LevelObjectView> levelObjectsViews;
    private PlayerView playerView;
    private Sprite levelBackground;
    private Sprite levelForeground;
    private Music theme;
    private BitmapFont font;

    public View(Model model){

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(1,1,0,0);

        this.model = model;
        this.levelObjectsViews = LevelController.getLevelObjectsViews();
        this.playerView = PlayerController.getPlayerView();
        this.levelBackground = AssetsService.getLevelBackground();
        this.levelForeground = AssetsService.getLevelForeground();

        batch = new SpriteBatch();
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        debugRenderer = new Box2DDebugRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(977, 550, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        theme = LevelController.getTheme();
        theme.setLooping(true);
        theme.play();
    }

    public void render(){
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        batch.begin();
        levelBackground.draw(batch);

        for(LevelObjectView view : levelObjectsViews) {
           if(view.getUpdatedSprite()!= null)
                view.getUpdatedSprite().draw(batch);
        }

        skeletonRenderer.draw(batch, playerView.getUpdatedSkeleton());

        if (VariablesService.SHOW_FPS){
            if ((int) System.currentTimeMillis() % 2 == 0)
                VariablesService.FPS = (Gdx.graphics.getFramesPerSecond());
            font.draw(batch, VariablesService.FPS + "fps", 70, 545);
        }

        levelForeground.draw(batch);
        batch.end();

       // debugRenderer.render(model.getWorld(), camera.combined.cpy().scale(ConstantsService.METERS_TO_PIXELS, ConstantsService.METERS_TO_PIXELS, 0));

    }

    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    public Vector2 getGraphicsCoords(Vector2 screenCoords) {
        Vector3 graphicsCoords = camera.unproject(new Vector3(screenCoords.x, screenCoords.y, 0), viewport.getLeftGutterWidth(), viewport.getBottomGutterHeight(), viewport.getScreenWidth(), viewport.getScreenHeight());
        return new Vector2(graphicsCoords.x, graphicsCoords.y);
    }

    public void addView(LevelObjectView view) {
        levelObjectsViews.add(view);
    }

    public void removeView(LevelObjectView view) {
        levelObjectsViews.remove(view);
    }

    public void reset(Model model) {
        this.model = model;
        resetMusic();
        this.levelObjectsViews = LevelController.getLevelObjectsViews();
        this.playerView = PlayerController.getPlayerView();
        this.levelBackground = AssetsService.getLevelBackground();
        this.levelForeground = AssetsService.getLevelForeground();
    }
    public void resetMusic(){
        theme.stop();
        this.theme = LevelController.getTheme();
        theme.setLooping(LevelController.getLevel()!=3);
        theme.play();
    }
}
