package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;
import com.game.services.ConstantsService;


public class Model {

    private HashSet<LevelObject> levelObjects;
    private Player player;
    private World world;
    private float accumulatedTime;

    public Model (HashSet<LevelObject> levelObjects, Player player, World world) {
        this.player = player;
        this.levelObjects = levelObjects;
        this.world = world;
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        accumulatedTime += deltaTime;
        while(accumulatedTime >= ConstantsService.WORLD_STEP) {
            world.step(ConstantsService.WORLD_STEP, 8, 3);
            accumulatedTime -= ConstantsService.WORLD_STEP;
        }
        player.update(deltaTime);
    }

    public World getWorld() {
        return world;
    }

    public void queryRayCast(RayCastCallback callback, Vector2 from, Vector2 to) {
        world.rayCast(callback, from, to);
    }

    public Body createBody(BodyDef bodyDef) {
        return world.createBody(bodyDef);
    }
}
