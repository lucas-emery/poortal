package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.models.LevelObject;
import com.game.views.LevelObjectView;

import java.util.HashSet;


public class LevelController { //Podria ser un service tambien

    private HashSet<LevelObject> levelObjects;
    private HashSet<LevelObjectView> levelObjectsViews;
    private World world;

    public LevelController() {
        levelObjects = new HashSet<LevelObject>();
        levelObjectsViews = new HashSet<LevelObjectView>();

        generateLevel();
    }

    public HashSet<LevelObject> getLevelObjects() {
        return (HashSet<LevelObject>) levelObjects.clone();
    }

    public HashSet<LevelObjectView> getLevelObjectsViews() {
        return (HashSet<LevelObjectView>) levelObjectsViews.clone();
    }

    private void generateLevel() {
        world = new World(new Vector2(0, -9.8f), true);
        LevelObject newObject = new LevelObject(LevelObject.Type.CUBE, new Vector2(100, 500));
        newObject.setBody(world.createBody(newObject.getBodyDef()));
        levelObjects.add(newObject);
        levelObjectsViews.add(new LevelObjectView(newObject));

        //piso de testeo
        LevelObject wall = new LevelObject(LevelObject.Type.WALL, new Vector2(0,0) );
        wall.setWall(world.createBody(wall.getBodyDef()), new Vector2(0,0), new Vector2(800,0));
        levelObjects.add(wall);

    }
    public World getLevelWorld(){
        return world;
    }
}
