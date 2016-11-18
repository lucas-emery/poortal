package com.game.controllers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.Wall;

import java.util.HashSet;

/**
 *The WallController is in charge of handling all of the
 *  wall's portability status.
 * */
public class WallController {

    private static HashSet<Wall> walls;

    /**
     * resets all the walls
     */
    public static void reset() {
        walls = LevelController.getWalls();
    }

    /**
     * Method which indicates if the wall in question is portable or not.
     * @param fixture the wall to evaluate
     * @return true if the wall is portable, otherwise false
     */
    public static boolean isPortableWall(Fixture fixture) {
        for(Wall wall : walls) {
            if(wall.equals(fixture))
                return wall.isPortable();
        }
        return false;
    }

}
