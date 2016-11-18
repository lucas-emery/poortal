package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.game.models.AnimatedObject;
import com.game.models.LevelObject;
import com.game.services.AssetsService;
import com.game.services.ConstantsService;

import java.util.ArrayList;
/**
 * Created by fdelgado on 04/11/16.
 */
public class AnimatedLevelObjectView extends LevelObjectView{

    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();


    public AnimatedLevelObjectView(LevelObject model) {
            this.model = model;
            this.sprites = AssetsService.getAnimatedSprites(model.getType());
    }
    @Override
    public  Sprite getUpdatedSprite() {
        if(model instanceof AnimatedObject) {
            int currentSprite = 0;
            int cantSprites = sprites.size();

            if(((AnimatedObject) model).isActive() && currentSprite < cantSprites-1){
                currentSprite++;
            }
            else{
                if(currentSprite>0)
                currentSprite--;
            }
            Sprite sprite =sprites.get(currentSprite);

            Vector2 position = model.getPosition().scl(ConstantsService.METERS_TO_PIXELS);

            sprite.setRotation((model.getBody().getAngle())* ConstantsService.RAD_TO_DEG);
            sprite.setPosition( (position.x - sprite.getWidth()/2),
                    (position.y - sprite.getHeight()/2));
            return sprite;
        }

        return null;
    }
}
