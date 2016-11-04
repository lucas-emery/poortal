package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.models.Collider;
import com.game.models.Collider.Type;
import com.game.models.Player;
import com.game.models.Teleportation;
import com.game.services.DebugService;

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

        int value = ((Collider)(f1.getUserData())).val()+((Collider)(f2.getUserData())).val();

        if(!PlayerController.getPlayer().isLookingLeft()) {
            if ((value & Type.PSENSORRIGHT.val()) == Type.PSENSORRIGHT.val()) {
                if ((value & Type.CUBE.val()) == Type.CUBE.val()) {
                    if (((Collider)f1.getUserData()).val() == Type.PSENSORRIGHT.val())
                        vicinity = f2;
                    else
                        vicinity = f1;
                }
            }
        }
        else{
            if ((value & Type.PSENSORLEFT.val() )== Type.PSENSORLEFT.val()) {
                if ((value & Type.CUBE.val())==Type.CUBE.val()) {
                    if(((Collider)f1.getUserData()).val() == Type.PSENSORLEFT.val()) {
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
        if((value & (Type.PORTAL.val()+Type.PSENSORFOOT.val()))==Type.PSENSORFOOT.val()){
            contactNumber++;
            playerOnGround = true;
        }
        if(((value & (Type.BUTTONSENSOR.val()))==Type.BUTTONSENSOR.val())){
            if(value - Type.BUTTONSENSOR.val()==Type.PSENSORFOOT.val() || value - Type.BUTTONSENSOR.val()==Type.CUBE.val()){
                System.out.println("Button Collision Detected");
                buttonPresssed(true);
            }
        }
        if((value & Type.PORTAL.val()) == Type.PORTAL.val()) {
            Fixture portal, object;
            if(((Collider)f1.getUserData()).val() == Type.PORTAL.val()) {
                portal = f1;
                object = f2;
            }
            else {
                portal = f2;
                object = f1;
            }
            if(!object.isSensor()) {
                TeleportationController.addTeleportation(new Teleportation(portal, object));
                Vector2 portalPos = portal.getBody().getPosition().cpy();
                Vector2 portalPrimary = new Vector2(0,1).rotateRad(portal.getBody().getAngle());
                ((Collider)object.getUserData()).disableContactFromVector(portalPos, portalPrimary);
//                DebugService.rays.add(portalPos);
//                DebugService.rays.add(portalPrimary.add(portalPos));
                System.out.println("Add tele");
            }
        }
    }

    @Override
    public void endContact (Contact contact) {
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();

        int value = ((Collider)(f1.getUserData())).val()+((Collider)(f2.getUserData())).val();

        if ((value & Collider.Type.PSENSORRIGHT.val()) == Type.PSENSORRIGHT.val()) {
            if ( (value & Type.CUBE.val()) == Type.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if ((value & Type.PSENSORLEFT.val() )== Type.PSENSORLEFT.val()) {
            if ((value & Type.CUBE.val())==Type.CUBE.val()) {
                if(f1 == vicinity || f2 == vicinity)
                    vicinity=null;
            }
        }

        if((value & (Type.PORTAL.val()+Type.PSENSORFOOT.val()))==Type.PSENSORFOOT.val()){
            contactNumber--;
            if(contactNumber==0)
                playerOnGround = false;
        }
        if(((value & (Type.BUTTONSENSOR.val()))==Type.BUTTONSENSOR.val())){
            if(value - Type.BUTTONSENSOR.val()==Type.PSENSORFOOT.val() || value - Type.BUTTONSENSOR.val()==Type.CUBE.val()){
                System.out.println("Button Collision Stopped");
                buttonPresssed(false);
            }
        }
        if((value & Type.PORTAL.val()) == Type.PORTAL.val()) {
            Fixture portal, object;
            if(((Collider)f1.getUserData()).val() == Type.PORTAL.val()) {
                portal = f1;
                object = f2;
            }
            else {
                portal = f2;
                object = f1;
            }
            if(!object.isSensor()) {
                TeleportationController.removeTeleportation(new Teleportation(portal, object));
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