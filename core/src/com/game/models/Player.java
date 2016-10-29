package com.game.models;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
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

    public Player() {
        state = new AnimationState(AssetsService.getPlayerStateData());
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation=true;
        animation = null;
    }

    public void changeVelocity(float dX, float dY){
        body.setLinearVelocity(body.getLinearVelocity().x + dX, body.getLinearVelocity().y + dY);
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
    public void createFixture() {
        PolygonShape shape = BodyService.getPlayerShape();

        FixtureDef fixtureDef = BodyService.getPlayerFixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    public boolean isFlipped(){
        return flip;
    }

    public boolean isGrounded() {
        if(Math.abs((double) getLinearVelocity().y)<ConstantsService.SPEEDDELTA)
            return true;
        else
            return false;
    }

    public void applyForceToCenter(float v, float i, boolean b) {
        body.applyForceToCenter(v, i, b);
    }

    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void setAnimation(String animation){
        this.animation=animation;
    }
}
