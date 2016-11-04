package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.models.Door;
import com.game.models.LevelObject;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;


public abstract class LevelObjectView {

    protected LevelObject model;

    public abstract Sprite getUpdatedSprite();

}
