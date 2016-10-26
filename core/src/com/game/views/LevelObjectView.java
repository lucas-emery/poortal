package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;
import com.game.services.AssetsService;


public class LevelObjectView {

    private LevelObject model;
    private Sprite sprite;

    public LevelObjectView(LevelObject model) {
        this.model = model;
        this.sprite = AssetsService.getSprite(model.getType());
    }

    public Sprite getUpdatedSprite() {
        Vector2 position = model.getPosition();
        position.add(-sprite.getWidth()/2, -sprite.getHeight()/2);
        sprite.setPosition(position.x, position.y);

        return sprite;
    }
}
