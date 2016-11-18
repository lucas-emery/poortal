package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.game.controllers.PortalController;
import com.game.services.ConstantsService;

/**
 * Created by lemery on 04/11/16.
 */
public class Collider{

    /**
     * Enumeration of the bit value for each type of
     * object which can be involved in a collision.
     */
    public enum Type {
        CUBE(1<<0),
        PSENSOR(1<<1),
        BUTTONSENSOR2(1<<2),
        PSENSORFOOT(1<<3),
        PLAYER(1<<4),
        BUTTON(1<<5),
        PLATSENSOR(1<<6),
        PLATBODY(1<<7),
        PORTAL(1<<8),
        WALL(1<<9),
        BUTTONSENSOR(1<<10),
        PORTALRIM(1<<11),
        FINISH(1<<12),
        DOOR(1<<13);

        private int value;

        private Type(int value) {
            this.value = value;
        }

        public int val() {
            return value;
        }
    }

    private Type type;
    private Vector2 contactDisablePoint;
    private Vector2 contactDisableVector;
    boolean ignore;

    /**
     * Constructor for a collider.
     * @param type the type of collider
     */
    public Collider(Type type) {
        this.type = type;
    }

    /**
     * This returns weather a collision should be ignored or not.
     * @return true if the collision should be ignored,
     * False otherwise.
     */
    public boolean ignore(){
        return ignore;
    }

    /**
     * method to set weather a collider should be ignored.
     * @param value true if the collider should be ignored
     */
    public void ignore(boolean value){
        ignore =value;
    }

    public Type getType() {
        return type;
    }

    /**
     * Returns Collider type value
     *
     * @return
     */
    public int val() {
        return type.val();
    }

    /**
     * Sets the vectors from which contact should be ignored
     *
     * @param contactDisablePoint
     * @param contactDisableVector
     */
    public void disableContactFromVector(Vector2 contactDisablePoint, Vector2 contactDisableVector) {
        this.contactDisablePoint = contactDisablePoint;
        this.contactDisableVector = contactDisableVector;
    }

    /**
     * Determines if @contact has to be ignored based on the vector provided in
     * disableContactFromVector(point, vector)
     *
     * @param contact
     * @return
     */
    public boolean attendContact(Vector2 contact) {
        if(contactDisablePoint != null && PortalController.bothPortalsExist()) {
            Vector2 relativeContact = contact.cpy().sub(contactDisablePoint);
            if (relativeContact.len() <= ConstantsService.getHeight(LevelObject.Type.PORTAL_BLUE)/2 && relativeContact.crs(contactDisableVector) < 0.01f) {
                return false;
            }
        }

        return true;
    }

    /**
     * Enables all contact for this collider
     */
    public void enableContact() {
        contactDisablePoint = null;
        contactDisableVector = null;
    }

}
