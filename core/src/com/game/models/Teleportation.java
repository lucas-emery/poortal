package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.controllers.PortalController;
import com.game.services.ConstantsService;

/**
 * A portal-object pair which is instanced whenever a
 * object enters a portal region and stores both object's
 * data in the transition.
 * Created by lemery on 04/11/16.
 */
public class Teleportation {

    private LevelObject.Type portalType;
    private Body portal;
    private Body object;

    /**
     * Constructor for the Teleportation class
     * @param portal the portal in question
     * @param object the object which is being teleported
     */
    public Teleportation(Fixture portal, Fixture object) {
        this.portal = portal.getBody();
        this.object = object.getBody();
        portalType = PortalController.getPortalType(portal);
    }

    public LevelObject.Type getPortalType() {
        return portalType;
    }

    /**
     *
     * @return
     */
    public boolean hasToTeleport() {
        Vector2 portalPrimary = new Vector2(0,1);
        portalPrimary.rotateRad(portal.getAngle());
        Vector2 objectRelative = object.getPosition().cpy().sub(portal.getPosition());

        if(objectRelative.crs(portalPrimary) < 0)
            return true;

        return false;
    }

    public void teleportObject(Vector2 position, Vector2 primary, Vector2 normal) {
        Vector2 relative = object.getPosition().cpy().sub(portal.getPosition());
        float relativeAngle = relative.angle();
        relative.setAngle(primary.angle());
        relative.rotate(relativeAngle);
        Vector2 newPosition = relative.add(position);

        float thisPrimaryAngle = portal.getAngle() + (float)Math.PI/2;
        float otherPrimaryAngle = primary.angleRad();

        float diff = otherPrimaryAngle - thisPrimaryAngle;

        float newAngle = (float)Math.PI;
        if(Math.abs(diff % (2*Math.PI)) < 0.1f ) {
            Vector2 thisPrimary = ConstantsService.CARTESIAN_VERSOR_Y.cpy().setAngleRad(thisPrimaryAngle);
            relativeAngle = object.getLinearVelocity().angleRad(thisPrimary);
            newAngle = relativeAngle * 2;
        }
        newAngle += diff;

        object.setTransform(newPosition, object.getAngle());
        object.setLinearVelocity(object.getLinearVelocity().rotateRad(newAngle));

    }

    /**
     * This will set the hashcode for the Teleportation so that it involves
     * both the portal and the object and it is a commutative operation meaning
     * that the order in which the portal-object paired is enter is irrelevant.
     * @return the hashCode of the Teleportation object.
     */
    @Override
    public int hashCode() {
        return portal.hashCode() + object.hashCode();
    }

    /**
     * Method used to compare one Teleportation to another.
     * @param o the Teleportation object to compare to.
     * @returnn true if the teleportation objects are equal,
     * fale otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if(o instanceof Teleportation) {
            Teleportation other = (Teleportation) o;
            return portal.equals(other.getPortal()) && object.equals(other.getObject());
        }
        else
            return false;
    }

    public Body getObject() {
        return object;
    }

    public Body getPortal() {
        return portal;
    }
}
