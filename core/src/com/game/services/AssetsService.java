package com.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.game.models.LevelObject;
import com.badlogic.gdx.graphics.Texture;

import java.util.EnumMap;


public class AssetsService {

    private static EnumMap<LevelObject.Type, Texture> textures = new EnumMap<LevelObject.Type, Texture>(LevelObject.Type.class);

    private static SkeletonData playerSkeletonData;
    private static AnimationStateData playerStateData;


    public static void initialize(){

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("stickman/skeleton.atlas"));

        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(ConstantsService.PLAYER_SCALE);

        playerSkeletonData = json.readSkeletonData(Gdx.files.internal("stickman/skeleton.json"));

        playerStateData = new AnimationStateData(playerSkeletonData);
        //Set animation mixes if any


        Texture texture;

        texture = new Texture(Gdx.files.internal("cube2.png"));
        textures.put(LevelObject.Type.CUBE, texture);

        texture = new Texture(Gdx.files.internal("portal_blue.png"));
        textures.put(LevelObject.Type.PORTAL_BLUE, texture);

        texture = new Texture(Gdx.files.internal("portal_orange.png"));
        textures.put(LevelObject.Type.PORTAL_ORANGE, texture);
    }

    public static Sprite getSprite(LevelObject.Type type) {

        Sprite newSprite = new Sprite(textures.get(type));
        return newSprite;
    }

    public static SkeletonData getPlayerSkeletonData() {
        return playerSkeletonData;
    }

    public static AnimationStateData getPlayerStateData() {
        return playerStateData;
    }

    public static Rectangle getPlayerDimensions() { return new Rectangle(0, 0, 250*ConstantsService.PLAYER_SCALE, 525*ConstantsService.PLAYER_SCALE); }
}
