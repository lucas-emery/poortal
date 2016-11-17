package com.game.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.models.*;
import com.game.services.ConstantsService;
import com.game.views.AnimatedLevelObjectView;
import com.game.views.LevelObjectView;
import com.game.views.StaticLevelObjectView;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.HashSet;
import java.util.IllegalFormatException;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.zip.CheckedOutputStream;

/**
 * LevelController is a class which handles
 * the initialisation of a level and stores
 * the physics world for the level
 *
 * */

public class LevelController {

    private static Player player;
    private static LinkedHashSet<LevelObject> levelObjects;
    private static HashSet<LevelObjectView> levelObjectsViews;
    private static HashSet<Wall> walls;
    private static HashSet<Button> buttons;
    private static World world;
    private static Door door;
    private static Finish finish;
    private static int level;
    private static Music theme;

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
    public static void generateLevel(Integer level) {

        levelObjects = new LinkedHashSet<LevelObject>();
        levelObjectsViews = new LinkedHashSet<LevelObjectView>();
        walls = new HashSet<Wall>();
        buttons = new HashSet<Button>();

//        LevelObject newObject;
//        door = new Door(new Vector2(928* ConstantsService.PIXELS_TO_METERS,68*ConstantsService.PIXELS_TO_METERS));
//        door.setBody(world.createBody(door.getBodyDef()));

        if(level <= 0 || level > ConstantsService.MAX_LEVEL)
            throw new IllegalArgumentException("Level cannot be negative or greater than "+ConstantsService.MAX_LEVEL+". Value: "+level);

        LevelController.level = level;

        String file = "levels/level"+level.toString()+".json";

        try {
            JSONObject levelData = (JSONObject) new JSONParser().parse(new FileReader(file));

            JSONArray themeData = (JSONArray) levelData.get("theme");

            if (themeData.size() != 1){
                System.out.println(themeData.size());
            throw new IllegalArgumentException("theme in " + file + " is not a String");
            }

            SetTheme((String)themeData.get(0));


            JSONArray gravityData = (JSONArray) levelData.get("gravity");

            if(gravityData.size() != 2)
                throw new IllegalArgumentException("gravity in "+file+" represents a 2D vector and must have 2 values");

            Vector2 gravity = new Vector2(((Double)gravityData.get(0)).floatValue(), ((Double)gravityData.get(1)).floatValue());

            world = new World(gravity, false);
            world.setContactListener(new CollisionController());

            
            JSONArray playerData = (JSONArray) levelData.get("player");
            
            if(playerData.size() != 2)
                throw new IllegalArgumentException("player in "+file+" represents a 2D vector and must have 2 values");
            
            Vector2 playerPosition = new Vector2(((Double)playerData.get(0)).floatValue(), ((Double)playerData.get(1)).floatValue()).scl(ConstantsService.PIXELS_TO_METERS);
            
            player.setInitialPosition(playerPosition);
            player.setBody(world.createBody(player.getBodyDef()));


            JSONArray levelObjectsData = (JSONArray) levelData.get("levelObjects");

            Iterator<JSONObject> levelObjectsDataIt = levelObjectsData.iterator();

            int index = 0;
            while(levelObjectsDataIt.hasNext()) {
                JSONObject levelObjectData = levelObjectsDataIt.next();
                index++;
                
                JSONArray positionData = (JSONArray) levelObjectData.get("position");
                
                if(positionData.size() != 2)
                    throw new IllegalArgumentException("position in "+file+" at levelObject n° "+index+" represents a 2D vector and must have 2 values");

                Vector2 position = new Vector2(((Double)positionData.get(0)).floatValue(), ((Double)positionData.get(1)).floatValue()).scl(ConstantsService.PIXELS_TO_METERS);

                LevelObject newObject;
                String type = (String) levelObjectData.get("type");
                if(type.equals("CUBE"))
                    newObject = new Cube(position);
                else if(type.equals("DOOR")) {
                    newObject = new Door(position);
                    door = (Door) newObject;
                }
                else if(type.equals("BUTTON"))
                    newObject = new Button(position, door);
                else
                    throw new IllegalArgumentException("type in "+file+" at levelObject n° "+index+" is not a valid type. Possible types: CUBE, BUTTON, DOOR.");

                newObject.setBody(world.createBody(newObject.getBodyDef()));
                
                levelObjects.add(newObject);

                if(newObject instanceof Button)
                    buttons.add((Button) newObject);
            }

            
            JSONArray wallsData = (JSONArray) levelData.get("walls");
            
            Iterator<JSONObject> wallsDataIt = wallsData.iterator();
            
            index = 0;
            while(wallsDataIt.hasNext()) {
                JSONObject wallData = wallsDataIt.next();
                index++;
                
                JSONArray originData, endData;
                Vector2 origin, end;
                
                originData = (JSONArray) wallData.get("origin");

                if(originData.size() != 2)
                    throw new IllegalArgumentException("origin in "+file+" at wall n° "+index+" represents a 2D vector and must have 2 values");

                origin = new Vector2(((Double)originData.get(0)).floatValue(), ((Double)originData.get(1)).floatValue()).scl(ConstantsService.PIXELS_TO_METERS);


                endData = (JSONArray) wallData.get("end");

                if(endData.size() != 2)
                    throw new IllegalArgumentException("end in "+file+" at wall n° "+index+" represents a 2D vector and must have 2 values");

                end = new Vector2(((Double)endData.get(0)).floatValue(), ((Double)endData.get(1)).floatValue()).scl(ConstantsService.PIXELS_TO_METERS);

                Vector2 size = end.sub(origin);

                Boolean floor = (Boolean) wallData.get("floor");

                Boolean portable = (Boolean) wallData.get("portable");

                Wall newWall = new Wall(origin, portable);
                newWall.setWall(world.createBody(newWall.getBodyDef()), size, floor);
                walls.add(newWall);
            }

            
            JSONArray finishData = (JSONArray) levelData.get("finish");
            
            if(finishData.size() != 2)
                throw new IllegalArgumentException("finish in "+file+" represents a 2D vector and must have 2 values");

            Vector2 finishPosition = new Vector2(((Double)finishData.get(0)).floatValue(), ((Double)finishData.get(1)).floatValue()).scl(ConstantsService.PIXELS_TO_METERS);

            finish = new Finish(finishPosition);

            finish.setBody(world.createBody(finish.getBodyDef()));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for(LevelObject object : levelObjects) {
            if( object instanceof AnimatedObject) {
                levelObjectsViews.add(new AnimatedLevelObjectView(object));
            }
            else{
                levelObjectsViews.add(new StaticLevelObjectView(object));
            }
        }

        WallController.reset();
        ButtonController.reset();
    }

    private static void SetTheme(String location) {
        theme = Gdx.audio.newMusic(Gdx.files.internal(location));
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
     */
    public static void initialize() {
        player = PlayerController.getPlayer();
    }

    public static int getLevel() {
        return level;
    }

    public static HashSet<Button> getButtons() {
        return (HashSet<Button>) buttons.clone();
    }

    public static Music getTheme() {
        return theme;
    }
}
