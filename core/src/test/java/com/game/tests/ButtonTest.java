package com.game.tests;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.game.controllers.InputController;
import com.game.controllers.LevelController;
import com.game.controllers.PlayerController;
import com.game.models.Button;
import com.game.models.Door;
import com.game.models.Model;
import com.game.services.AssetsService;
import com.game.views.View;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juan on 11/11/16.
 */
public class ButtonTest {
    Button button;
    Door door;

    @Before
    public void setUp() throws Exception {
        button = new Button(new Vector2(0.0f,0.0f),new Door(new Vector2(0.0f,0.0f)));
    }

    @Test
    public void isActive() throws Exception {
        assertFalse(button.isActive());
    }
}