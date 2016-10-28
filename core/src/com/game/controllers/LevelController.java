package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.models.Cube;
import com.game.models.LevelObject;
import com.game.models.Player;
import com.game.models.Wall;
import com.game.services.ConstantsService;
import com.game.views.LevelObjectView;

import java.util.HashSet;


public class LevelController {

    private Player player;
    private HashSet<LevelObject> levelObjects;
    private HashSet<LevelObjectView> levelObjectsViews;
    private HashSet<Wall> walls;
    private World world;

    public LevelController() {
        levelObjects = new HashSet<LevelObject>();
        levelObjectsViews = new HashSet<LevelObjectView>();
        walls = new HashSet<Wall>();
    }

    public HashSet<LevelObject> getLevelObjects() {
        return (HashSet<LevelObject>) levelObjects.clone();
    }

    public HashSet<LevelObjectView> getLevelObjectsViews() {
        return (HashSet<LevelObjectView>) levelObjectsViews.clone();
    }

    public HashSet<Wall> getWalls() {
        return (HashSet<Wall>) walls.clone();
    }

    public void generateLevel() {
        world = new World(new Vector2(0, -9.8f), true);

        player.setInitialPosition(new Vector2(10,3));
        player.setBody(world.createBody(player.getBodyDef()));

        LevelObject newObject = new Cube(new Vector2(4, 7));
        newObject.setBody(world.createBody(newObject.getBodyDef()));
        levelObjects.add(newObject);
        levelObjectsViews.add(new LevelObjectView(newObject));
        
        Wall floor = new Wall(new Vector2(0,35* ConstantsService.PIXELS_TO_METERS ), true);
        floor.setWall(world.createBody(floor.getBodyDef()), new Vector2(977* ConstantsService.PIXELS_TO_METERS,0), true);
        walls.add(floor);

        Wall leftWall = new Wall(new Vector2(40* ConstantsService.PIXELS_TO_METERS,35* ConstantsService.PIXELS_TO_METERS), true);
        leftWall.setWall(world.createBody(leftWall.getBodyDef()), new Vector2(0,520* ConstantsService.PIXELS_TO_METERS), false);
        walls.add(floor);

        Wall rightWall = new Wall(new Vector2(925* ConstantsService.PIXELS_TO_METERS,35* ConstantsService.PIXELS_TO_METERS), true);
        rightWall.setWall(world.createBody(rightWall.getBodyDef()), new Vector2(0,520* ConstantsService.PIXELS_TO_METERS), false);
        walls.add(floor);


    }
    public World getLevelWorld(){
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
