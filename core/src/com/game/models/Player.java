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
    private boolean running;
    private boolean flip;
    private LevelObject.Type type;
    private String animation;
    private boolean grounded;
    private Fixture vicinity;
    private boolean holding;

    public Player() {
        state = new AnimationState(AssetsService.getPlayerStateData());
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

        if(animation !=null ){
            if(animation == "LEFT")
                flip = true;
            else
                flip =false;

            if(!running) {
                state.setAnimation(0, "run", true);
                running = true;
            }
        }
        else{
            state.clearTrack(0);
            running = false;
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

    public boolean isFlipped(){
        return flip;
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

    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    public boolean isHolding() {
        return holding;
    }
}
