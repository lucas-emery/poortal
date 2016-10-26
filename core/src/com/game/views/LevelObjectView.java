package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;


public class LevelObjectView {

    private LevelObject model;
    private Sprite sprite;

    public LevelObjectView(LevelObject model) {
        this.model = model;
        this.sprite = AssetsService.getSprite(model.getType());
    }

    public Sprite getUpdatedSprite() {
        Vector2 position = model.getPosition();
        sprite.setPosition( (position.x * ConstantsService.METERS_TO_PIXELS) - sprite.getWidth()/2,
                            (position.y * ConstantsService.METERS_TO_PIXELS) - sprite.getHeight()/2);

        return sprite;
    }
}
