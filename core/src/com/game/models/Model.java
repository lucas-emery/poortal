package com.game.models;

import com.game.controllers.Controller;

import java.util.HashSet;

/**
 * Created by juan on 24/10/16.
 */
public class Model {

    private HashSet<GameObject> levelObjects;
    private Player player;

    public Model (HashSet<GameObject> levelObjects, Player player) {
        this.player = player;
        this.levelObjects = levelObjects;
    }

    public void update() {
        //Update models??
    }
}
