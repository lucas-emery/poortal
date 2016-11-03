package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.game.models.Door;
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
        if(model.getType() == LevelObject.Type.LEFT_DOOR || (model.getType() == LevelObject.Type.RIGHT_DOOR)){
            if(!((Door)model).isClosed())
                return null;
        }
        Vector2 position = model.getPosition();

        sprite.setRotation((model.getBody().getAngle())*ConstantsService.RAD_TO_DEG);
        sprite.setPosition( (position.x * ConstantsService.METERS_TO_PIXELS) - sprite.getWidth()/2,
                            (position.y * ConstantsService.METERS_TO_PIXELS) - sprite.getHeight()/2);

        return sprite;
    }
}
