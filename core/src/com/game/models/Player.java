package com.game.models;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.spine.AnimationState;
import com.game.services.AssetsService;
import com.game.services.BodyService;
import com.game.services.ConstantsService;
import com.sun.glass.ui.Window;

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

    public Player() {
        state = new AnimationState(AssetsService.getPlayerStateData());
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(0,0);
        bodyDef.fixedRotation=true;
        animation = null;
        vicinity = null;
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
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(1f,1.5f);                                  // DITTO LO DE ABAJO
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor=true;
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData("Sensor");

        shape.setAsBox(0.3f, 0.2f, new Vector2(0,-0.85f),0);        //DESPUES HAY QUE MOVER LOS MAGIC NUMBERS A SERVICES
        fixtureDef.shape=shape;                                     //LOS SETTEE ARBITRARIAMENTE, TIENEN QUE VER CON LA FORMA
        body.createFixture(fixtureDef).setUserData("FootSensor");   //DEL SPRITE DE PLAYER

        shape = BodyService.getPlayerShape();

        fixtureDef = BodyService.getPlayerFixtureDef();
        fixtureDef.shape = shape;

        body.createFixture(fixtureDef).setUserData("Body");
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

    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    public void setAnimation(String animation){
        this.animation=animation;
    }
}
