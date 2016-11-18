package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.models.LevelObject;


public abstract class LevelObjectView {

    protected LevelObject model;

    public abstract Sprite getUpdatedSprite();

}
