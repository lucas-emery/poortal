package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.models.*;
import com.game.services.ConstantsService;
import com.game.views.AnimatedLevelObjectView;
import com.game.views.LevelObjectView;
import com.game.views.StaticLevelObjectView;

import java.util.HashSet;
import java.util.LinkedHashSet;
/**
 * LevelController is a class which handles
 * the initialisation of a level and stores
 * the physics world for the level
 *
 * */

public class LevelController {

    private static Player player;
    private static LinkedHashSet<LevelObject> levelObjects = new LinkedHashSet<LevelObject>();
    private static HashSet<LevelObjectView> levelObjectsViews = new LinkedHashSet<LevelObjectView>();
    private static HashSet<Wall> walls = new HashSet<Wall>();
    private static World world;

    /**
     * Method that returns a clone of the
     * HashSet of the LevelController's LevelObjects
     * @return HashSet of LevelObjects
     */
    public static HashSet<LevelObject> getLevelObjects() {
        return (HashSet<LevelObject>) levelObjects.clone();
    }
    /**
     * Method that returns a clone of the
     * LinkedHashSet of the LevelController's LevelObjectViews
     * @return LinkedHashSet of LevelObjectViews
     */
    public static LinkedHashSet<LevelObjectView> getLevelObjectsViews() {
        return (LinkedHashSet<LevelObjectView>) levelObjectsViews.clone();
    }
    /**
     * Method that returns a clone of the
     * HashSet of the LevelController's walls
     */
    public static HashSet<Wall> getWalls() {
        return (HashSet<Wall>) walls.clone();
    }

    /**
     * The generateLevel method will instance the physics world,
     * set the level object's initial position and insert them
     * into the world also instancing the level's walls.
     */
    public static void generateLevel() {
        world = new World(new Vector2(0, -9.8f), false);
        world.setContactListener(new CollisionController());

        player.setInitialPosition(new Vector2(10,3));
        player.setBody(world.createBody(player.getBodyDef()));

        //levelObjects.add(new Door(new Vector2(928* ConstantsService.PIXELS_TO_METERS,68*ConstantsService.PIXELS_TO_METERS)));
        levelObjects.add(new Cube(new Vector2(4, 7)));
        levelObjects.add(new Button(new Vector2(3,2)));

        for(LevelObject object : levelObjects) {
            object.setBody(world.createBody(object.getBodyDef()));

            if( object instanceof AnimatedObject) {
                levelObjectsViews.add(new AnimatedLevelObjectView(object));
            }
            else{
                levelObjectsViews.add(new StaticLevelObjectView(object));
            }
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

        Wall roof = new Wall(new Vector2(0,500).scl(ConstantsService.PIXELS_TO_METERS), true);
        roof.setWall(world.createBody(roof.getBodyDef()), new Vector2(977* ConstantsService.PIXELS_TO_METERS,0), false);
        walls.add(roof);


    }

    /**
     * A method which returns the level's physics world.
     * @return the level's physics world.
     */
    public static World getLevelWorld(){
        return world;
    }

    /**
     * This method will set the current level player.
     * @param recievedPlayer Player object to be set in the level
     */
    public static void setPlayer(Player recievedPlayer) {
        player = recievedPlayer;
    }

}
