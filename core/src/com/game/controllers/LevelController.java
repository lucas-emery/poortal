package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.game.models.Cube;
import com.game.models.LevelObject;
import com.game.models.Player;
import com.game.models.Wall;
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

    public void generateLevel() {
        world = new World(new Vector2(0, -9.8f), true);

        player.setInitialPosition(new Vector2(10,3));
        player.setBody(world.createBody(player.getBodyDef()));

        LevelObject newObject = new Cube(new Vector2(3, 7));
        newObject.setBody(world.createBody(newObject.getBodyDef()));
        levelObjects.add(newObject);
        levelObjectsViews.add(new LevelObjectView(newObject));

        //piso de testeo
        Wall wall = new Wall(new Vector2(0,0) );
        wall.setWall(world.createBody(wall.getBodyDef()), new Vector2(0,0), new Vector2(800,0));
        walls.add(wall);


    }
    public World getLevelWorld(){
        return world;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
