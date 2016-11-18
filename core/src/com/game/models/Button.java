package com.game.models;

import com.badlogic.gdx.graphics.g3d.particles.influencers.RegionInfluencer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Button is a class in which the physical
 * information of the LevelObject button
 * is created and stored.
 * @author Pablo Radnic.
 */
public class Button extends LevelObject implements AnimatedObject{


    private boolean active;
    private Fixture fixture;
    private Door door;
    public float timer;

    /**
     * Constructor which assigns the position Vector and
     * type to the LevelObject, also creates BodyDef.
     * @param position Vector2 representing the physical
     *                 position of the Button.
     */
    public Button(Vector2 position, Door door) { //Concept

        this.position = position;
        this.type = Type.BUTTON;
        this.active=false;
        this.timer=0;
        this.door=door;
        createBodyDef();
    }

    /**
     * Method which creates fixture and userdata for the
     * LevelObject.
     */
    @Override
    public void createFixtureDef() {

        Shape shape;
        FixtureDef fixtureDef = new FixtureDef();

        fixtureDef.shape=(shape = BodyService.getButtonSensorShape());
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Collider(Collider.Type.BUTTONSENSOR));
        shape.dispose();

        fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=(shape = BodyService.getShape(type));
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.BUTTON));

        shape.dispose();


    }

    /**
     * Sets the button's fixture to a sensor.
     * @param pressed
     */
    private void isPressed(boolean pressed){
        fixture.setSensor(pressed);
    }

    @Override
    public boolean isActive() {
        return active;
    }
    /**
     * Sets the button's status to pressed and activates
     * the door which is linked with the button.
     * @param isActive weather the button is being
     *                 pressed or not.
     */
    public void setActive(boolean isActive){
        active=isActive;
        isPressed(active);
        door.setClosed(!isActive);
    }
    public void setTimer(float time){
        timer=time;
    }
    public float getTimer(){
        return timer;
    }

    /**
     * Method which checks received fixture by
     * address with Button Object.
     * @param otherFixture fixture for button
     *                     to be compared with.
     * @return boolean value which states if the received
     * fixture is in contact with the buttons fixture.
     */
    public boolean equals(Fixture otherFixture){
        return fixture.equals(otherFixture);
    }

    /**
     * hashcode generator for button fixture.
     * @return int hashcode for the button fixture.
     */
    public int hashCode(){
        return fixture.hashCode();
    }
}
