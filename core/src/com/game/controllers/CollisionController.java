package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.services.ConstantsService.ColliderType;

/**
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private static boolean playerOnGround = false;
    private static Fixture vicinity=null;
    private static int contactNumber=0;

    public static void setVicinity(Fixture vicinity) {
        CollisionController.vicinity = vicinity;
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        int value = ((Integer)(f1.getUserData())+(Integer)(f2.getUserData()));
        if(!PlayerController.getPlayer().isFlipped()) {
            if ((value & ColliderType.PSENSORRIGHT.val()) == ColliderType.PSENSORRIGHT.val()) {
                if ((value & ColliderType.BUTTON.val()) == ColliderType.BUTTON.val() || (value & ColliderType.CUBE.val()) == ColliderType.CUBE.val()) {
                    if ((Integer) (f1.getUserData()) == ColliderType.PSENSORRIGHT.val())
                        vicinity = f2;
                    else
                        vicinity = f1;
                }
            }
        }
        else{
            if ((value & ColliderType.PSENSORLEFT.val() )== ColliderType.PSENSORLEFT.val()) {
                if ((value & ColliderType.BUTTON.val())==ColliderType.BUTTON.val()||(value & ColliderType.CUBE.val())==ColliderType.CUBE.val()) {
                    if((Integer)(f1.getUserData()) == ColliderType.PSENSORLEFT.val()) {
                        vicinity = f2;
                        System.out.println(f2);
                    }
                    else{
                        vicinity = f1;
                        System.out.println(f2);
                    }
                }
            }
        }
        if((value & (ColliderType.PORTAL.val()+ColliderType.PSENSORFOOT.val()))==ColliderType.PSENSORFOOT.val()){
            contactNumber++;
            playerOnGround = true;
        }
    }

    @Override
    public void endContact (Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        int value = ((Integer)(f1.getUserData())+(Integer)(f2.getUserData()));

        if ((value & ColliderType.PSENSORRIGHT.val()) == ColliderType.PSENSORRIGHT.val()) {
            if ((value & ColliderType.BUTTON.val()) == ColliderType.BUTTON.val() || (value & ColliderType.CUBE.val()) == ColliderType.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if ((value & ColliderType.PSENSORLEFT.val() )== ColliderType.PSENSORLEFT.val()) {
            if ((value & ColliderType.BUTTON.val())==ColliderType.BUTTON.val()||(value & ColliderType.CUBE.val())==ColliderType.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if((value & (ColliderType.PORTAL.val()+ColliderType.PSENSORFOOT.val()))==ColliderType.PSENSORFOOT.val()){
            contactNumber--;
            if(contactNumber==0)
                playerOnGround = false;
        }

    }
    private static boolean isInteractable(Fixture fixture){
        return (fixture.getUserData()!=null && (fixture.getUserData().equals("CUBE")|| fixture.getUserData().equals("BUTTON")));
    }
    public static boolean isPlayerOnGround() {
        return playerOnGround;
    }

    public static Fixture getVicinity() {
        System.out.println(vicinity);
        return vicinity;
    }


    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}