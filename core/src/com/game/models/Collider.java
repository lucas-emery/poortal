package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.controllers.PortalController;
import com.game.services.ConstantsService;

/**
 * Created by lemery on 04/11/16.
 */
public class Collider{

    public enum Type {
        CUBE(1<<0),
        PSENSORRIGHT(1<<1),
        PSENSORLEFT(1<<2),
        PSENSORFOOT(1<<3),
        PLAYER(1<<4),
        BUTTON(1<<5),
        PLATSENSOR(1<<6),
        PLATBODY(1<<7),
        PORTAL(1<<8),
        WALL(1<<9),
        BUTTONSENSOR(1<<10),
        PORTALRIM(1<<11),
        FINISH(1<<12);

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


    public Collider(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public int val() {
        return type.val();
    }

    public void disableContactFromVector(Vector2 contactDisablePoint, Vector2 contactDisableVector) {
        this.contactDisablePoint = contactDisablePoint;
        this.contactDisableVector = contactDisableVector;
    }

    public boolean attendContact(Vector2 contact) {
        if(contactDisablePoint != null && PortalController.bothPortalsExist()) {
            Vector2 relativeContact = contact.cpy().sub(contactDisablePoint);
//            System.out.println(relativeContact);
            if (relativeContact.len() <= ConstantsService.getHeight(LevelObject.Type.PORTAL_BLUE)/2 && relativeContact.crs(contactDisableVector) < 0.01f) {
//                System.out.println(relativeContact);
                return false;
            }
        }

        return true;
    }

    public void enableContact() {
        contactDisablePoint = null;
        contactDisableVector = null;
    }

}
