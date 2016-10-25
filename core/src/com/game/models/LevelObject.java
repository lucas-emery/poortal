package com.game.models;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.game.services.AssetsService;

/**
 * Created by juan on 24/10/16.
 */
public class LevelObject {

    private Rectangle rekt; //esto seria el body
    private Type type;

    public LevelObject(Type type, Vector2 position) { //Concept
        this.type = type;
        rekt = new Rectangle();
        rekt.x = position.x;
        rekt.y = position.y;
        Sprite sprite = AssetsService.getSprite(type); //Concept
        rekt.width = sprite.getWidth();
        rekt.height = sprite.getHeight();
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        CUBE, BUTTON
    }
}
