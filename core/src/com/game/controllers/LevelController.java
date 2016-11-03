package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.models.*;
import com.game.services.ConstantsService;
import com.game.views.LevelObjectView;

import java.util.HashSet;


public class LevelController {

    private static Player player;
    private static HashSet<LevelObject> levelObjects = new HashSet<LevelObject>();
    private static HashSet<LevelObjectView> levelObjectsViews = new HashSet<LevelObjectView>();
    private static HashSet<Wall> walls = new HashSet<Wall>();
    private static World world;

    public static HashSet<LevelObject> getLevelObjects() {
        return (HashSet<LevelObject>) levelObjects.clone();
    }

    public static HashSet<LevelObjectView> getLevelObjectsViews() {
        return (HashSet<LevelObjectView>) levelObjectsViews.clone();
    }

    public static HashSet<Wall> getWalls() {
        return (HashSet<Wall>) walls.clone();
    }

    public static void generateLevel() {
        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(new CollisionController());

        player.setInitialPosition(new Vector2(10,3));
        player.setBody(world.createBody(player.getBodyDef()));

        levelObjects.add(new Cube(new Vector2(4, 7)));

        levelObjects.add(new Door(new Vector2(37,2)));

        for(LevelObject object : levelObjects) {
            object.setBody(world.createBody(object.getBodyDef()));
            levelObjectsViews.add(new LevelObjectView(object));
        }
        
        Wall floor = new Wall(new Vector2(0,35* ConstantsService.PIXELS_TO_METERS ), true);
        floor.setWall(world.createBody(floor.getBodyDef()), new Vector2(977* ConstantsService.PIXELS_TO_METERS,0), true);
        walls.add(floor);

        Wall leftWall = new Wall(new Vector2(40* ConstantsService.PIXELS_TO_METERS,35* ConstantsService.PIXELS_TO_METERS), true);
        leftWall.setWall(world.createBody(leftWall.getBodyDef()), new Vector2(0,520* ConstantsService.PIXELS_TO_METERS), false);
        walls.add(leftWall);

        Wall rightWall = new Wall(new Vector2(925,35).scl(ConstantsService.PIXELS_TO_METERS), true);
        rightWall.setWall(world.createBody(rightWall.getBodyDef()), new Vector2(0,520* ConstantsService.PIXELS_TO_METERS), false);
        walls.add(rightWall);


    }
    public static World getLevelWorld(){
        return world;
    }

    public static void setPlayer(Player recievedPlayer) {
        player = recievedPlayer;
    }

}
