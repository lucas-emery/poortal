package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.esotericsoftware.spine.SkeletonRenderer;
import com.game.controllers.PlayerController;
import com.game.models.LevelObject;
import com.game.models.Model;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;
import com.game.services.VariablesService;

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
    private Sprite levelBackgorund;
    private Music theme;
    private BitmapFont font;
    private ShapeRenderer shapeRenderer;

    public View(Model model, HashSet<LevelObjectView> levelObjectsViews, PlayerView playerView, Sprite levelBackground) {

        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setColor(1,1,0,0);

        this.model = model;
        this.levelObjectsViews = levelObjectsViews;
        this.playerView = playerView;
        this.levelBackgorund = levelBackground;

        batch = new SpriteBatch();
        skeletonRenderer = new SkeletonRenderer();
        skeletonRenderer.setPremultipliedAlpha(true);
        debugRenderer = new Box2DDebugRenderer();
        shapeRenderer = new ShapeRenderer();

        camera = new OrthographicCamera();
        viewport = new FitViewport(977, 550, camera);
        viewport.apply();
        camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);

        //PlaceHolder theme
        theme = AssetsService.getTheme();
        theme.setLooping(true);
        theme.play();
    }

    public void render(){
        camera.update();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        levelBackgorund.draw(batch);

        for(LevelObjectView view : levelObjectsViews)
            view.getUpdatedSprite().draw(batch);

        skeletonRenderer.draw(batch, playerView.getUpdatedSkeleton());

        if (VariablesService.SHOW_FPS){
            if ((int) System.currentTimeMillis() % 2 == 0)
                VariablesService.FPS = (Gdx.graphics.getFramesPerSecond());
            font.draw(batch, VariablesService.FPS + "fps", 70, 545);
        }

        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined.cpy().scale(ConstantsService.METERS_TO_PIXELS, ConstantsService.METERS_TO_PIXELS, 0));
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.FIREBRICK);
        shapeRenderer.line(PlayerController.rayPos, PlayerController.ray);
        shapeRenderer.end();

        debugRenderer.render(model.getWorld(), camera.combined.cpy().scale(ConstantsService.METERS_TO_PIXELS, ConstantsService.METERS_TO_PIXELS, 0));
    }

    public void mouseMove(int x, int y){
        playerView.updatePortalArm(x,y);
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
}
