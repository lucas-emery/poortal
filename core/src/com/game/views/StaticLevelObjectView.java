package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.models.LevelObject;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;

/**
 * Created by fdelgado on 04/11/16.
 */
public class StaticLevelObjectView extends LevelObjectView {

    private Sprite sprite;

    public StaticLevelObjectView(LevelObject model) {
        this.model = model;
        this.sprite = AssetsService.getStaticSprite(model.getType());
    }

    public Sprite getUpdatedSprite(){
        Vector2 position = model.getPosition();

        sprite.setRotation((model.getBody().getAngle())* ConstantsService.RAD_TO_DEG);
        sprite.setPosition( (position.x * ConstantsService.METERS_TO_PIXELS) - sprite.getWidth()/2,
                (position.y * ConstantsService.METERS_TO_PIXELS) - sprite.getHeight()/2);

        return sprite;
    }
}
