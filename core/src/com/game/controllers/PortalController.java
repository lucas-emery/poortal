package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject.Type;
import com.game.models.Portal;
import com.game.views.LevelObjectView;
import com.game.views.StaticLevelObjectView;

/**
 *
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
    public static void firePortal(Vector2 playerPos, Vector2 clickPos, Portal.Type portalType){
        PortalRayCastCallback callback = new PortalRayCastCallback(portalType);
        Controller.queryRayCast(callback, playerPos, clickPos);
        if(callback.isWallPortable())
            spawnPortal(callback.getWallPoint(), callback.getWallNormal(), portalType);
    }

    public static Type getPortalType(Fixture fixture) {
        if(bluePortal.compareFixture(fixture))
            return Type.PORTAL_BLUE;
        if(orangePortal.compareFixture(fixture))
            return Type.PORTAL_ORANGE;

        return null;
    }

    public static Portal getOtherPortal(Type portalType) {
        if(portalType.equals(Type.PORTAL_BLUE))
            return orangePortal;
        else
            return bluePortal;
    }

    private static class PortalRayCastCallback implements RayCastCallback {

        private Type portalType;
        private float nearestWallFraction;
        private Vector2 wallPoint;
        private Vector2 wallNormal;
        private boolean wallIsPortable;

        public PortalRayCastCallback(Type portalType) {
            if(portalType != Type.PORTAL_BLUE && portalType != Type.PORTAL_ORANGE)
                throw new IllegalArgumentException("LevelObject.Type is not a portal type");

            this.portalType = portalType;
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
            System.out.println("Fixture found at: "+ point);
            Boolean isPortableWall = WallController.isPortableWall(fixture);
            if(isPortableWall != null && fraction < nearestWallFraction) {
                System.out.println("Wall");
                nearestWallFraction = fraction;
                wallIsPortable = isPortableWall;
                wallPoint = point.cpy();
                wallNormal = normal.cpy();
            }
            return 1;
        }

        /**
         *
         * @return
         */
        public Vector2 getWallPoint() {
            return wallPoint;
        }

        /**
         *
         * @return
         */
        public Vector2 getWallNormal() {
            return wallNormal;
        }

        /**
         *
         * @return
         */
        public boolean isWallPortable() {
            return wallIsPortable;
        }
    }
}
