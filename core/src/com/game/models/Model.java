package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by juan on 24/10/16.
 */
public class Model {

    private HashSet<LevelObject> levelObjects;
    private Player player;
    private World world;

    public Model (HashSet<LevelObject> levelObjects, Player player, World world) {
        this.player = player;
        this.levelObjects = levelObjects;
        this.world = world;
    }

    public void update() {
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }
}
