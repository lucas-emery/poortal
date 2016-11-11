package com.game.tests;

import com.badlogic.gdx.math.Vector2;
import com.game.controllers.Controller;
import com.game.models.Cube;
import com.game.views.LevelObjectView;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by juan on 10/11/16.
 */
public class ControllerTest {
    LevelObjectView levelObjectView;
    Controller controller;

    @Before
    public void setUp() throws Exception {
        /*controller = new Controller();
        controller.create();*/
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void addLevelObject() throws Exception {/*
        int size = Controller.getModel().sizeOfLevelObjectsSet();
        Controller.addLevelObject(new Cube(new Vector2(0.0f,0.0f)), levelObjectView);
        size = Controller.getModel().sizeOfLevelObjectsSet();
        assertEquals("Failed to create LevelObject",size+1,size);*/
    }

    @Test
    public void removeLevelObject() throws Exception {

    }

}