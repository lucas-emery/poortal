package com.game.models;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.spine.AnimationState;
import com.game.services.AssetsService;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

public class Player {

    private Body body;
    private BodyDef bodyDef;
    private AnimationState state;
    private String oldState;
    private boolean lookingLeft;
    private LevelObject.Type type;
    private String animation;
    private boolean grounded;
    private Fixture vicinity;
    private boolean holding;

    public Player() {
        state = new AnimationState(AssetsService.getPlayerStateData());
        state.setTimeScale(1.5f);
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation=true;
        animation = null;
        vicinity = null;
        holding = false;
    }
    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }

    public void setInitialPosition(Vector2 position) {
        bodyDef.position.set(position.x, position.y);
    }

    public void update(float deltaTime) {

        state.update(deltaTime);
        if(animation != null ){
            String newState;
            if((animation.equals("LEFT") && lookingLeft) || (animation.equals("RIGHT") && !lookingLeft))
                newState = "run";
            else
                newState = "runback";

            if(!oldState.equals(newState)) {
                state.setAnimation(0, newState, true);
                oldState = newState;
            }
        }
        else{
            state.setAnimation(0, "idle", false);
            oldState = "idle";
        }
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

    public Body getBody(){ return body;}

    public void setBody(Body body) {
        this.body = body;
        createFixture();
    }
    public void setGrounded(boolean grounded){
        this.grounded=grounded;
    }

    public void createFixture() {
        PolygonShape shape;
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor=true;

        fixtureDef.shape=(shape=BodyService.getPlayerSensorShape(true));
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.PSENSORRIGHT.val());
        shape.dispose();

        fixtureDef.shape=(shape=BodyService.getPlayerSensorShape(false));
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.PSENSORLEFT.val());
        shape.dispose();

        fixtureDef.shape=(shape=BodyService.getPlayerFootSensor());
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.PSENSORFOOT.val());
        shape.dispose();

        fixtureDef = BodyService.getPlayerFixtureDef();
        fixtureDef.shape = (shape = BodyService.getPlayerShape());
        body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.PSENSORBODY.val());
        shape.dispose();
    }

    public boolean isLookingLeft(){
        return lookingLeft;
    }

    public boolean isGrounded() {
        return grounded;
    }
    public Fixture getVicinity(){
        return vicinity;
    }
    public void setVicinity(Fixture vicinity){
        this.vicinity=vicinity;
    }

    public void applyForceToCenter(float v, float i, boolean b) {
        body.applyForceToCenter(v, i, b);
    }

    public void setAnimation(String animation){
        this.animation=animation;
    }

    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    public boolean isHolding() {
        return holding;
    }
}
