package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.controllers.PortalController;
import com.game.services.DebugService;

/**
 * Created by lemery on 04/11/16.
 */
public class Teleportation {

    private LevelObject.Type portalType;
    private Body portal;
    private Body object;

    public Teleportation(Fixture portal, Fixture object) {
        this.portal = portal.getBody();
        this.object = object.getBody();
        portalType = PortalController.getPortalType(portal);
    }

    public LevelObject.Type getPortalType() {
        return portalType;
    }

    public boolean hasToTeleport() {
        Vector2 portalPrimary = new Vector2(0,1);
        portalPrimary.rotateRad(portal.getAngle());
        Vector2 objectRelative = object.getPosition().cpy().sub(portal.getPosition());
        /*DebugService.rays.add(portal.getPosition());
        DebugService.rays.add(objectRelative.cpy().add(portal.getPosition()));
        DebugService.rays.add(portal.getPosition());
        DebugService.rays.add(portalPrimary.cpy().add(portal.getPosition()));*/

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

        //Espejar por vector normal

        //float newAngle = object.getAngle() + portal.getAngle() - primary.angleRad();

        object.setTransform(newPosition, object.getAngle());
    }

    @Override
    public int hashCode() {
        return portal.hashCode() + object.hashCode();
    }

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
