package com.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.spine.SkeletonData;
import com.game.models.LevelObject;
import com.badlogic.gdx.graphics.Texture;


public class AssetsService {

    //Hacemos todo static??

    public static SkeletonData getPlayerSkeletonData() { //TODO
        return new SkeletonData();
    }

    public static Sprite getSprite(LevelObject.Type type) {
       // return new Sprite();
        Texture spriteTexture = new Texture(Gdx.files.internal("cube2.png"));
        Sprite newSprite = new Sprite(spriteTexture);
        return newSprite;
    }
}
