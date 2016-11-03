package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.models.Player;
import com.game.services.ConstantsService.ColliderType;

/**
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private static boolean playerOnGround = false;
    private static Fixture vicinity=null;
    private static int contactNumber=0;
    private static boolean isButtonPressed = false;

    public static void setVicinity(Fixture vicinity) {
        CollisionController.vicinity = vicinity;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        int value = ((Integer)(f1.getUserData())+(Integer)(f2.getUserData()));

        if(!PlayerController.getPlayer().isLookingLeft()) {
            if ((value & ColliderType.PSENSORRIGHT.val()) == ColliderType.PSENSORRIGHT.val()) {
                if ((value & ColliderType.CUBE.val()) == ColliderType.CUBE.val()) {
                    if ((Integer) (f1.getUserData()) == ColliderType.PSENSORRIGHT.val())
                        vicinity = f2;
                    else
                        vicinity = f1;
                }
            }
        }
        else{
            if ((value & ColliderType.PSENSORLEFT.val() )== ColliderType.PSENSORLEFT.val()) {
                if ((value & ColliderType.CUBE.val())==ColliderType.CUBE.val()) {
                    if((Integer)(f1.getUserData()) == ColliderType.PSENSORLEFT.val()) {
                        if(!PlayerController.getPlayer().isHolding())
                            vicinity = f2;
                    }
                    else{
                        if(!PlayerController.getPlayer().isHolding())
                            vicinity = f1;
                    }
                }
            }
        }
        if((value & (ColliderType.PORTAL.val()+ColliderType.PSENSORFOOT.val()))==ColliderType.PSENSORFOOT.val()){
            contactNumber++;
            playerOnGround = true;
        }
        if(((value & (ColliderType.BUTTONSENSOR.val()))==ColliderType.BUTTONSENSOR.val())){
            if(value - ColliderType.BUTTONSENSOR.val()==ColliderType.PSENSORFOOT.val() || value - ColliderType.BUTTONSENSOR.val()==ColliderType.CUBE.val()){
                System.out.println("Button Collision Detected");
                buttonPresssed(true);
            }
        }
    }

    @Override
    public void endContact (Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        int value = ((Integer)(f1.getUserData())+(Integer)(f2.getUserData()));

        if ((value & ColliderType.PSENSORRIGHT.val()) == ColliderType.PSENSORRIGHT.val()) {
            if ( (value & ColliderType.CUBE.val()) == ColliderType.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if ((value & ColliderType.PSENSORLEFT.val() )== ColliderType.PSENSORLEFT.val()) {
            if ((value & ColliderType.CUBE.val())==ColliderType.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if((value & (ColliderType.PORTAL.val()+ColliderType.PSENSORFOOT.val()))==ColliderType.PSENSORFOOT.val()){
            contactNumber--;
            if(contactNumber==0)
                playerOnGround = false;
        }
        if(((value & (ColliderType.BUTTONSENSOR.val()))==ColliderType.BUTTONSENSOR.val())){
            if(value - ColliderType.BUTTONSENSOR.val()==ColliderType.PSENSORFOOT.val() || value - ColliderType.BUTTONSENSOR.val()==ColliderType.CUBE.val()){
                System.out.println("Button Collision Stopped");
                buttonPresssed(false);
            }
        }

    }

    public static void buttonPresssed(boolean pressed) {
        isButtonPressed=pressed;
    }

    public static boolean getButtonPressed(){
        return isButtonPressed;
    }

    public static boolean isPlayerOnGround() {
        return playerOnGround;
    }

    public static Fixture getVicinity() {
        //System.out.println(vicinity);
        return vicinity;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}