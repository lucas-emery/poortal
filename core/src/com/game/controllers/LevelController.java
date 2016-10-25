package com.game.controllers;

import com.game.models.GameObject;
import com.game.views.GameObjectView;

import java.util.HashSet;


public class LevelController { //Podria ser un service tambien

    private HashSet<GameObject> levelObjects;
    private HashSet<GameObjectView> levelObjectsViews;

    public LevelController() {
        levelObjects = new HashSet<GameObject>();
        levelObjectsViews = new HashSet<GameObjectView>();
    }

    public HashSet<GameObject> getLevelObjects() {
        return (HashSet<GameObject>) levelObjects.clone();
    }

    public HashSet<GameObjectView> getLevelObjectsViews() {
        return (HashSet<GameObjectView>) levelObjectsViews.clone();
    }
}
