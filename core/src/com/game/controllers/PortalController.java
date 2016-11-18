package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.Collider;
import com.game.models.LevelObject;
import com.game.models.LevelObject.Type;
import com.game.models.Portal;
import com.game.services.ConstantsService;
import com.game.views.LevelObjectView;
import com.game.views.StaticLevelObjectView;

/**
 *The PortalController is in charge of managing the interaction between the two
 * linked portals.
 */
public class PortalController {
    private static Portal bluePortal;
    private static LevelObjectView bluePortalView;
    private static Portal orangePortal;
    private static LevelObjectView orangePortalView;

    /**
     * This method will store a portal's information within the controller when a successful
     * portal fire is executed @see firePortal
     * @param point this is the position where the portal is currently
     * @param normal this is a vector which is notmal to the wall with which the portal collided
     * @param portalType the color of the portal that was successfully fired
     */
    private static void spawnPortal(Vector2 point, Vector2 normal, Type portalType) {
        Portal newPortal = new Portal(point, normal, portalType);
        LevelObjectView newView = new StaticLevelObjectView(newPortal);
        Controller.addLevelObject(newPortal, newView);
        if(portalType == Type.PORTAL_BLUE) {
            if(bluePortal != null)
                Controller.removeLevelObject(bluePortal, bluePortalView);
            bluePortal = newPortal;
            bluePortalView = newView;
        }
        else {
            if(orangePortal != null)
                Controller.removeLevelObject(orangePortal, orangePortalView);
            orangePortal = newPortal;
            orangePortalView = newView;
        }
    }

    /**
     *
     * @param playerPos
     * @param clickPos
     * @param portalType
     */
    public static void firePortal(Vector2 playerPos, Vector2 clickPos, LevelObject.Type portalType){
        PortalRayCastCallback callback = new PortalRayCastCallback();
        Controller.queryRayCast(callback, playerPos, clickPos);
        if(callback.isWallPortable())
            spawnPortal(callback.getWallPoint(), callback.getWallNormal(), portalType);
    }

    /**
     * returns the type of portal in question or null if
     * the object is not a portal.
     * @param fixture the portal to query
     * @return
     */
    public static Type getPortalType(Fixture fixture) {
        if(bluePortal != null && bluePortal.compareFixture(fixture))
            return Type.PORTAL_BLUE;
        if(orangePortal != null && orangePortal.compareFixture(fixture))
            return Type.PORTAL_ORANGE;

        return null;
    }

    /**
     * This method will return the other portal which is linked with
     * the portal in question
     * @param portalType the type of portal
     * @return
     */
    public static Portal getOtherPortal(Type portalType) {
        if(portalType.equals(Type.PORTAL_BLUE))
            return orangePortal;
        else
            return bluePortal;
    }

    /**
     * This method resets all portal's models and views.
     */
    public static void reset() {
        bluePortal = null;
        bluePortalView = null;
        orangePortal = null;
        orangePortalView = null;
    }

    /**
     * This method which indicates weather both portals
     * are currently set or not in the wold.
     * @return
     */
    public static boolean bothPortalsExist() {
        return bluePortal != null && orangePortal != null;
    }

    /**
     *
     */
    private static class PortalRayCastCallback implements RayCastCallback {

        private float nearestWallFraction;
        private Vector2 wallPoint;
        private Vector2 wallNormal;
        private boolean wallIsPortable;

        public PortalRayCastCallback() {
            nearestWallFraction = 2;
        }

        /**
         *
         * @param fixture
         * @param point
         * @param normal
         * @param fraction
         * @return
         */
        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {

            Collider collider = ((Collider) fixture.getUserData());
            int type = collider.val();

            if(type == Collider.Type.WALL.val() && fraction < nearestWallFraction) {
                Vector2 x = fixture.getBody().getPosition();
                Vector2 size = new Vector2();
                ((EdgeShape)fixture.getShape()).getVertex2(size);
                Vector2 space1 = x.cpy().add(size).sub(point);
                Vector2 space2 = x.cpy().sub(point);
                float portalWidth = ConstantsService.getHeight(Type.PORTAL_ORANGE);

                Vector2 newPoint =  point.cpy();
                if(space1.len() < portalWidth/2) {
                    newPoint = space2.cpy().setLength(portalWidth/2).add(x).add(size);
                }
                else if(space2.len() < portalWidth/2) {
                    newPoint = space1.cpy().setLength(portalWidth/2).add(x);
                }

                nearestWallFraction = fraction;
                wallIsPortable = WallController.isPortableWall(fixture);
                wallPoint = newPoint;
                wallNormal = normal.cpy();
            }
            else if((type & (Collider.Type.CUBE.val() | Collider.Type.PORTAL.val() | Collider.Type.PORTALRIM.val())) == 0 && fraction < nearestWallFraction)
                if(type != Collider.Type.DOOR.val() || !collider.ignore())
                    wallIsPortable = false;

            return 1;
        }

        public Vector2 getWallPoint() {
            return wallPoint;
        }

        public Vector2 getWallNormal() {
            return wallNormal;
        }

        /**
         * method which indicates weather the wall in question
         * supports portals or not.
         * @return false if the wall is not portable, otherwise
         *          true.
         */
        public boolean isWallPortable() {
            return wallIsPortable;
        }
    }
}
