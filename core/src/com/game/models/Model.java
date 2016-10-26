package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;


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
        float deltaTime = Gdx.graphics.getDeltaTime();
        world.step(deltaTime, 6, 2);
        player.update(deltaTime);
    }
}
