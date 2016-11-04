package com.game.models;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.esotericsoftware.spine.AnimationState;
import com.game.services.AssetsService;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Player is a model type class which handles the physical rules which the player abides to
 * @author Juan Godfrid
 */
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

    /**
     * Constructor which initializes player body and sets its world coordinates
     */
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

    /**
     * Method which gets the players linear velocity
     * @return Vector2 Two-dimensional velocity vector
     */
    public Vector2 getVelocity(){
        return body.getLinearVelocity();
    }

    /**
     * Method which sets the initial spatial coordinates for the players position
     * @param position Vector2 Two-dimensional vector with players position
     */
    public void setInitialPosition(Vector2 position) {
        bodyDef.position.set(position.x, position.y);
    }

    /**
     * Method which runs every render, updating player animation state
     * @param deltaTime deltaTime for the game
     */
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

    /**
     * Method which gets the animation state for the player
     * @return AnimationState state
     */
    public AnimationState getState() {
        return state;
    }

    /**
     * Method which gets the position of the player
     * @return Vector2 Two-dimensional vector containing the players coordinates
     */
    public Vector2 getPosition() {
        return body.getPosition();
    }

    /**
     * Method which gets the players bodyDef
     * @return BodyDef bodyDef
     */
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    /**
     * Method which gets the players body
     * @return
     */
    public Body getBody(){ return body;}

    /**
     * Method which sets the Body for the player
     * @param body Body to be added to the player
     */
    public void setBody(Body body) {
        this.body = body;
        createFixture();
    }

    /**
     * Method which sets the boolean grounded attribute, true states that the player is in contact with the ground,
     * false states otherwise
     * @param grounded boolean value for grounded state
     */
    public void setGrounded(boolean grounded){
        this.grounded=grounded;
    }

    /**
     * Method which creates the fixture for the player
     */
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

    /**
     * Method which gets value representing direction in which the player is looking
     * @return Boolean lookingLeft
     */
    public boolean isLookingLeft(){
        return lookingLeft;
    }

    /**
     * Method which gets value representing grounded state of the player
     * @return Boolean grounded
     */
    public boolean isGrounded() {
        return grounded;
    }
    public Fixture getVicinity(){
        return vicinity;
    }

    /**
     * Method which sets fixture for the object which the player has in his vicinity
     * @param vicinity Fixture vicinity of the object
     */
    public void setVicinity(Fixture vicinity){
        this.vicinity=vicinity;
    }

    /**
     * Wrapper method for Box2D applyForceToCenter body method
     * @param v float force in x axis
     * @param i float force in y axis
     * @param b boolean wake value
     */
    public void applyForceToCenter(float v, float i, boolean b) {
        body.applyForceToCenter(v, i, b);
    }


    /**
     * Method which sets animation for the player
     * @param animation String representing animation
     */
    public void setAnimation(String animation){
        this.animation=animation;
    }

    /**
     * Method which sets lookingLeft boolean value
     * @param lookingLeft boolean to set as lookingLeft Player attribute
     */
    public void setLookingLeft(boolean lookingLeft) {
        this.lookingLeft = lookingLeft;
    }

    /**
     * Method which sets holding boolean value which represents hold state for a player
     * @param holding boolean value to set as holding attribute
     */
    public void setHolding(boolean holding) {
        this.holding = holding;
    }

    /**
     * Method which gets holding value
     * @return boolean holding attribute
     */
    public boolean isHolding() {
        return holding;
    }
}
