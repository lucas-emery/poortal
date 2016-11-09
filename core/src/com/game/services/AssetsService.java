package com.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Rectangle;
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

    private static EnumMap<LevelObject.Type, Texture> staticTextures = new EnumMap<LevelObject.Type, Texture>(LevelObject.Type.class);
    private static EnumMap<LevelObject.Type,ArrayList<Texture>> animatedTextures =
            new EnumMap<LevelObject.Type,ArrayList<Texture>>(LevelObject.Type.class);

    private static SkeletonData playerSkeletonData;
    private static AnimationStateData playerStateData;
    private static Rectangle playerTextureDimensions;
    private static Rectangle buttonTextureDimensions;
    private static float playerScale;
    private static float buttonScale;
    private static Music theme;


    public static void initialize(){

        Texture texture;
        ArrayList<Texture> textures;

        texture = new Texture(Gdx.files.internal("mapa.png"));
        levelBackgrounds.add(texture);

        texture = new Texture(Gdx.files.internal("cube2.png"));
        staticTextures.put(LevelObject.Type.CUBE, texture);

        texture = new Texture(Gdx.files.internal("portal_blue.png"));
        staticTextures.put(LevelObject.Type.PORTAL_BLUE, texture);

        texture = new Texture(Gdx.files.internal("portal_orange.png"));
        staticTextures.put(LevelObject.Type.PORTAL_ORANGE, texture);

        textures = new ArrayList<Texture>();
        texture = new Texture(Gdx.files.internal("closed_door_right.png"));
        textures.add(0,texture);
        animatedTextures.put(LevelObject.Type.RIGHT_DOOR,textures);

        textures = new ArrayList<Texture>();
        texture = new Texture(Gdx.files.internal("closed_door_left.png"));
        textures.add(0,texture);
        animatedTextures.put(LevelObject.Type.LEFT_DOOR, textures);

        textures = new ArrayList<Texture>();
        texture = new Texture(Gdx.files.internal("button.png"));
        textures.add(0, texture);
        animatedTextures.put(LevelObject.Type.BUTTON, textures);

        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("stickman-test/skeleton.atlas"));
        playerTextureDimensions = new Rectangle(0, 0, 500, 1050);

        buttonTextureDimensions = new Rectangle(0,0, 20, 50);
        buttonScale = (ConstantsService.BUTTON_HEIGHT * ConstantsService.METERS_TO_PIXELS)  / buttonTextureDimensions.getHeight();

        playerScale = (ConstantsService.PLAYER_HEIGHT * ConstantsService.METERS_TO_PIXELS) / playerTextureDimensions.getHeight();

        SkeletonJson json = new SkeletonJson(atlas);
        json.setScale(playerScale);

        playerSkeletonData = json.readSkeletonData(Gdx.files.internal("stickman-test/skeleton.json"));

        playerStateData = new AnimationStateData(playerSkeletonData);
        playerStateData.setDefaultMix(0.2f);

        //PLACEHOLDER THEME
        theme = Gdx.audio.newMusic(Gdx.files.internal("Music/theme.mp3"));
    }

    public static Sprite getStaticSprite(LevelObject.Type type) {
        Sprite newSprite = new Sprite(staticTextures.get(type));
        newSprite.setSize(ConstantsService.getWidth(type)*ConstantsService.METERS_TO_PIXELS,
                ConstantsService.getHeight(type)*ConstantsService.METERS_TO_PIXELS);
        newSprite.setOriginCenter();
        return newSprite;
    }

    public static ArrayList<Sprite> getAnimatedSprites(LevelObject.Type type){
        ArrayList<Sprite> sprites = new ArrayList<Sprite>();
        for(Texture texture: animatedTextures.get(type)){
            Sprite newSprite = new Sprite(texture);
            newSprite.setSize(ConstantsService.getWidth(type)*ConstantsService.METERS_TO_PIXELS,
                    ConstantsService.getHeight(type)*ConstantsService.METERS_TO_PIXELS);
            newSprite.setOriginCenter();
            sprites.add(newSprite);
        }

        return sprites;

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

    public static Rectangle getButtonDimensions(){
        return new Rectangle(0,0,buttonTextureDimensions.getWidth()*buttonScale, buttonTextureDimensions.getHeight()*buttonScale);
    }

    public static Sprite getLevelSprite(int index) {
        return new Sprite(levelBackgrounds.get(index));
    }

    public static Music getTheme(){ return theme;}
}
