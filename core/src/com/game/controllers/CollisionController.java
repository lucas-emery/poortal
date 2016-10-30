package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;

/**
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private boolean playerOnGround;
    private boolean InVicinityofLevelObject;
    public CollisionController(){
        playerOnGround=false;
        InVicinityofLevelObject =false;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor") || f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            InVicinityofLevelObject =true;
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            playerOnGround=true;
        }
    }

    public boolean isPlayerOnGround() {
        return playerOnGround;
    }

    public boolean isInVicinityofLevelObject() {
        return InVicinityofLevelObject;
    }

    @Override
    public void endContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor") || f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            InVicinityofLevelObject =false;
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            playerOnGround=false;
        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
