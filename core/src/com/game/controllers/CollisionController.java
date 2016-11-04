package com.game.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.game.models.Player;
import com.game.services.ConstantsService.ColliderType;

/**
 * This controller is in charge of managing
 * all of the collisions that are occurring in the game
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private static boolean playerOnGround = false;
    private static Fixture vicinity=null;
    private static int contactNumber=0;
    private static boolean isButtonPressed = false;

    /**
     *
     * @param vicinity
     */
    public static void setVicinity(Fixture vicinity) {
        CollisionController.vicinity = vicinity;
    }

    /**
     *This method is called whenever two fixtures collide
     * with each other and manages all the events when two
     * objects are in contact.
     *
     * @param contact the object which describes the contact
     *                managed by the physics engine
     */
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

    /**
     *This method is called wheneaver two fixtures stop colliding
     * with each other handling the events wheneaver two objects
     * stop being in contact
     * @param contact
     */
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

    /**
     *
     * @param pressed
     */
    public static void buttonPresssed(boolean pressed) {
        isButtonPressed=pressed;
    }

    /**
     *
     * @return
     */
    public static boolean getButtonPressed(){
        return isButtonPressed;
    }

    /**
     *
     * @return
     */
    public static boolean isPlayerOnGround() {
        return playerOnGround;
    }

    /**
     *
     * @return
     */
    public static Fixture getVicinity() {
        //System.out.println(vicinity);
        return vicinity;
    }

    /**
     *This method is not used within our program buy iy is a mandatory
     * implementation of the ContactListener interface
     * @param contact
     * @param oldManifold
     */
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    /**
     *his method is not used within our program buy iy is a mandatory
     *implementation of the ContactListener interface
     * @param contact
     * @param impulse
     */
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}