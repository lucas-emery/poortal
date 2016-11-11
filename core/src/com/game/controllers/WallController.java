package com.game.controllers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.Wall;

import java.util.HashSet;

/**
 *
 *
 *
 * */
public class WallController {

    private static HashSet<Wall> walls;

    public static void reset() {
        walls = LevelController.getWalls();
    }

    public static Boolean isPortableWall(Fixture fixture) {
        for(Wall wall : walls) {
            if(wall.equals(fixture))
                return wall.isPortable();
        }
        return null;
    }

}
