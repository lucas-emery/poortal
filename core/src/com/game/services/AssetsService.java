package com.game.services;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.spine.SkeletonData;
import com.game.models.LevelObject;


public class AssetsService {

    //Hacemos todo static??

    public static SkeletonData getPlayerSkeletonData() { //TODO
        return new SkeletonData();
    }

    public static Sprite getSprite(LevelObject.Type type) {
        return new Sprite();
    }
}
