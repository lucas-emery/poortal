package com.game.models;

import java.util.HashSet;

/**
 * Created by juan on 24/10/16.
 */
public class Model {

    private HashSet<LevelObject> levelObjects;
    private Player player;

    public Model (HashSet<LevelObject> levelObjects, Player player) {
        this.player = player;
        this.levelObjects = levelObjects;
    }

    public void update() {
        //Update models??
    }
}
