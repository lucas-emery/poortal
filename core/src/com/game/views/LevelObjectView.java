package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.models.LevelObject;
import com.game.services.AssetsService;


public class LevelObjectView {

    private LevelObject model;
    private Sprite sprite;

    public LevelObjectView(LevelObject model) {
        this.model = model;
        this.sprite = AssetsService.getSprite(model.getType());
    }

    public void render(SpriteBatch batch) {
        batch.draw(sprite.getTexture(), model.getBody().getPosition().x,model.getBody().getPosition().y);
    }
}
