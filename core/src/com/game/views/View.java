package com.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.game.models.Model;

/**
 * Created by juan on 24/10/16.
 */
public class View {

    private Model model;
    private SpriteBatch batch;
    private Camera camera;
    private Viewport viewport;

    public View(Model model) {
        this.model = model;
    }

    public void render(){
        Gdx.gl.glClearColor(1, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
