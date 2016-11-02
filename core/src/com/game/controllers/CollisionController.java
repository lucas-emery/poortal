package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.models.LevelObject;

/**
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private static boolean playerOnGround = false;
    private static Fixture vicinity=null;
    private static int contactNumber=0;

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor")){
            if(isInteractable(f2)){
                vicinity=f2;
            }
        }
        if(f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            if(isInteractable(f1)){
                vicinity=f1;
            }
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            if(!(f1.getUserData().equals("POORTAL")||f2.getUserData().equals("POORTAL"))) {
                contactNumber++;
                playerOnGround = true;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        if(f1.getUserData()!= null && f1.getUserData().equals("Sensor")){
            if(isInteractable(f2)){
                vicinity=null;
            }
        }
        if(f2.getUserData()!=null && f2.getUserData().equals("Sensor")){
            if(isInteractable(f1)){
                vicinity=null;
            }
        }
        if(f1.getUserData()!= null && f1.getUserData().equals("FootSensor") ||f2.getUserData()!= null && f2.getUserData().equals("FootSensor")){
            if(!(f1.getUserData().equals("POORTAL")||f2.getUserData().equals("POORTAL"))) {
                contactNumber--;
                if (contactNumber == 0) {
                    playerOnGround = false;
                }
            }
            
        }

    }
    private static boolean isInteractable(Fixture fixture){
        return (fixture.getUserData()!=null && (fixture.getUserData().equals("CUBE")|| fixture.getUserData().equals("BUTTON")));
    }
    public static boolean isPlayerOnGround() {
        return playerOnGround;
    }

    public static Fixture getVicinity() {
        return vicinity;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
