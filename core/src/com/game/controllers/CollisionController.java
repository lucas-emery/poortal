package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.game.models.Button;
import com.game.models.Collider;
import com.game.models.Collider.Type;
import com.game.models.Player;
import com.game.models.Teleportation;
import com.game.services.ConstantsService;
import com.game.services.DebugService;

/**
 * This controller is in charge of managing
 * all of the collisions that are occurring in the game
 * Created by juan on 24/10/16.
 */
public class CollisionController implements ContactListener {

    private static boolean playerOnGround = false;
    private static Fixture vicinity=null;
    private static int contactNumber=0;
    private static int pressers=0;

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
        Collider c1 = (Collider)f1.getUserData();
        Collider c2 = (Collider)f2.getUserData();

        int value = c1.val()+c2.val();

        if(value == Type.PSENSOR.val()+Type.CUBE.val()) {
            if (!PlayerController.getPlayer().isHolding()) {
                if((c1.val()== Type.CUBE.val())) {
                    vicinity = f1;
                }
                else {
                    vicinity = f2;
                }
            }
        }


        if((value & (Type.PORTAL.val()+Type.PSENSORFOOT.val()))==Type.PSENSORFOOT.val()){
            contactNumber++;
            playerOnGround = true;
        }
        if(((value & (Type.BUTTONSENSOR.val()))==Type.BUTTONSENSOR.val())){
            if(value - Type.BUTTONSENSOR.val()==Type.PSENSORFOOT.val() || value - Type.BUTTONSENSOR.val()==Type.CUBE.val()){
                pressers++;
                Button button = ButtonController.findButton((((Collider)f1.getUserData()).val()==Type.BUTTONSENSOR.val())?f1:f2);
                button.setActive(true);
            }
        }
        if((value & Type.PORTAL.val()) == Type.PORTAL.val()) {
            Fixture portal, object;
            if(c1.val() == Type.PORTAL.val()) {
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
        Collider c1 = (Collider)f1.getUserData();
        Collider c2 = (Collider)f2.getUserData();

        int value = c1.val()+c2.val();

        if(value == Type.PSENSOR.val()+Type.CUBE.val()) {
            if (!PlayerController.getPlayer().isHolding()) {
                vicinity=null;
            }
        }

        if((value & Type.PSENSORFOOT.val())==Type.PSENSORFOOT.val()){
            contactNumber--;
            if(contactNumber==0)
                playerOnGround = false;
        }

        if(((value & (Type.BUTTONSENSOR.val()))==Type.BUTTONSENSOR.val())){
            if(value - Type.BUTTONSENSOR.val()==Type.PSENSORFOOT.val() || value - Type.BUTTONSENSOR.val()==Type.CUBE.val()){
                pressers--;
                if(pressers==0){
                    Button button = ButtonController.findButton((((Collider)f1.getUserData()).val()==Type.BUTTONSENSOR.val())?f1:f2);
                    button.setActive(false);
                }
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
                ((Collider)object.getUserData()).enableContact();
            }
        }
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
        Fixture f1 = contact.getFixtureA();
        Fixture f2 = contact.getFixtureB();
        Collider c1 = (Collider) f1.getUserData();
        Collider c2 = (Collider) f2.getUserData();

        if(((c1.val() | c2.val()) & Type.PORTALRIM.val()) != Type.PORTALRIM.val()) {
            WorldManifold worldManifold = contact.getWorldManifold();
            int size = worldManifold.getNumberOfContactPoints();
            Vector2[] contactPoints = worldManifold.getPoints();
            boolean attendContact = true;
//            System.out.println(size);

            for (int i = 0; i < size; i++) {
                if (!c1.attendContact(contactPoints[i]) || !c2.attendContact(contactPoints[i])) {
//                    System.out.println("ignore");
                    attendContact = false;
                    break;
                }
            }

            contact.setEnabled(attendContact);
        }
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