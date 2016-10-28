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
import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;
import java.util.EnumMap;


public class AssetsService {

    private static ArrayList<Texture> levelBackgrounds = new ArrayList<Texture>();

    private static EnumMap<LevelObject.Type, Texture> textures = new EnumMap<LevelObject.Type, Texture>(LevelObject.Type.class);

    private static SkeletonData playerSkeletonData;
    private static AnimationStateData playerStateData;
    private static Rectangle playerTextureDimensions;
    private static float playerScale;
    private static Music theme;


    public static void initialize(){

        Texture texture;

        texture = new Texture(Gdx.files.internal("mapa.png"));
        levelBackgrounds.add(texture);

        texture = new Texture(Gdx.files.internal("cube2.png"));
        textures.put(LevelObject.Type.CUBE, texture);

        texture = new Texture(Gdx.files.internal("portal_blue.png"));
        textures.put(LevelObject.Type.PORTAL_BLUE, texture);

        texture = new Texture(Gdx.files.internal("portal_orange.png"));
        textures.put(LevelObject.Type.PORTAL_ORANGE, texture);


        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("stickman/skeleton.atlas"));
        playerTextureDimensions = new Rectangle(0, 0, 500, 1050);

        playerScale = (ConstantsService.PLAYER_HEIGHT * ConstantsService.METERS_TO_PIXELS) / playerTextureDimensions.getHeight();

        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(playerScale);

        playerSkeletonData = json.readSkeletonData(Gdx.files.internal("stickman/skeleton.json"));

        playerStateData = new AnimationStateData(playerSkeletonData);
        //Set animation mixes if any

        //PLACEHOLDER THEME
        theme = Gdx.audio.newMusic(Gdx.files.internal("Music/theme.mp3"));
    }

    public static Sprite getSprite(LevelObject.Type type) {
        Sprite newSprite = new Sprite(textures.get(type));
        newSprite.setSize(ConstantsService.getWidth(type)*ConstantsService.METERS_TO_PIXELS, ConstantsService.getHeight(type)*ConstantsService.METERS_TO_PIXELS);
        newSprite.setOriginCenter();
        return newSprite;
    }

    public static SkeletonData getPlayerSkeletonData() {
        return playerSkeletonData;
    }

    public static AnimationStateData getPlayerStateData() {
        return playerStateData;
    }

    public static Rectangle getPlayerDimensions() {
        return new Rectangle(0, 0, playerTextureDimensions.getWidth() * playerScale, playerTextureDimensions.getHeight() * playerScale);
    }

    public static Sprite getLevelSprite(int index) {
        return new Sprite(levelBackgrounds.get(index));
    }

    public static Music getTheme(){ return theme;}
}
