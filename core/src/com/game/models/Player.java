package com.game.models;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.game.services.AssetsService;

public class Player {

    Body body;
    BodyDef bodyDef;
    AnimationState state;

    public Player() {
        state = new AnimationState(AssetsService.getPlayerStateData());
        state.setAnimation(0, "run", true);

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
    }

    public void setInitialPosition(Vector2 position) {
        bodyDef.position.set(position.x, position.y);
    }

    public void update(float deltaTime) {
        state.update(deltaTime);
    }

    public AnimationState getState() {
        return state;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public void setBody(Body body) {
        this.body = body;
    }
}
