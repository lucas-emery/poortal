package com.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.esotericsoftware.spine.SkeletonData;
import com.game.models.LevelObject;
import com.badlogic.gdx.graphics.Texture;

import java.util.EnumMap;


public class AssetsService {

    private static EnumMap<LevelObject.Type, Texture> textures = new EnumMap<LevelObject.Type, Texture>(LevelObject.Type.class);

    public static SkeletonData getPlayerSkeletonData() { //TODO
        return new SkeletonData();
    }


    public static void initialize(){

        Texture spriteTexture = new Texture(Gdx.files.internal("cube2.png"));
        textures.put(LevelObject.Type.CUBE,spriteTexture);

        spriteTexture = new Texture(Gdx.files.internal("portal_blue.png"));
        textures.put(LevelObject.Type.PORTAL_BLUE,spriteTexture);

        spriteTexture = new Texture(Gdx.files.internal("portal_orange.png"));
        textures.put(LevelObject.Type.PORTAL_ORANGE,spriteTexture);
    }
    public static Sprite getSprite(LevelObject.Type type) {
       // return new Sprite();

        Sprite newSprite = new Sprite(textures.get(type));
        return newSprite;
    }
}
