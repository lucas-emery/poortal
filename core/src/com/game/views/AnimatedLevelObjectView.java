package com.game.views;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.game.models.Door;
import com.game.models.LevelObject;
import com.game.services.AssetsService;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by fdelgado on 04/11/16.
 */
public class AnimatedLevelObjectView extends LevelObjectView{

    private ArrayList<Sprite> sprites = new ArrayList<Sprite>();


    public AnimatedLevelObjectView(LevelObject model) {
            this.model = model;
            this.sprites = AssetsService.getAnimatedSprites(model.getType());
        }
    }

    public  Sprite getUpdatedSprite() {

        return
        }

    }
}
