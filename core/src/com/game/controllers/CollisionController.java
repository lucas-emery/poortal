package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.models.LevelObject;
import com.sun.glass.ui.Window;

/**
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private boolean playerOnGround;
    private Fixture vicinity;
    public CollisionController(){
        playerOnGround=false;
        vicinity = null;
    }
    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor")){
            if(f2.getUserData()!=null && f2.getUserData().equals("INTERRACTABLE")){
                vicinity=f2;
            }
        }
        if(f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            if(f2.getUserData()!=null && f2.getUserData().equals("INTERRACTABLE")){
                vicinity=f1;
            }
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            playerOnGround=true;
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor")){
            if(f2.getUserData()!=null && f2.getUserData().equals("INTERRACTABLE")){
                vicinity=null;
            }
        }
        if(f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            if(f2.getUserData()!=null && f2.getUserData().equals("INTERRACTABLE")){
                vicinity=null;
            }
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            playerOnGround=false;
        }

    }
    public boolean isPlayerOnGround() {
        return playerOnGround;
    }

    public Fixture getVicinity() {
        return vicinity;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
