package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject;
import com.game.models.Portal;
import com.game.views.LevelObjectView;


public class PortalController {
    private static Portal bluePortal;
    private static LevelObjectView bluePortalView;
    private static Portal orangePortal;
    private static LevelObjectView orangePortalView;


    private static void spawnPortal(Vector2 point, Vector2 normal, LevelObject.Type portalType) {
        Portal newPortal = new Portal(point, normal, portalType);
        LevelObjectView newView = new LevelObjectView(newPortal);
        Controller.addLevelObject(newPortal, newView);
        if(portalType == LevelObject.Type.PORTAL_BLUE) {
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

    public static void firePortal(Vector2 playerPos, Vector2 clickPos, Portal.Type portalType){
        PortalRayCastCallback callback = new PortalRayCastCallback(portalType);
        Controller.queryRayCast(callback, playerPos, clickPos);
        if(callback.isWallPortable())
            spawnPortal(callback.getWallPoint(), callback.getWallNormal(), portalType);
    }

    private static class PortalRayCastCallback implements RayCastCallback {

        private LevelObject.Type portalType;
        private float nearestWallFraction;
        private Vector2 wallPoint;
        private Vector2 wallNormal;
        private boolean wallIsPortable;

        public PortalRayCastCallback(LevelObject.Type portalType) {
            if(portalType != LevelObject.Type.PORTAL_BLUE && portalType != LevelObject.Type.PORTAL_ORANGE)
                throw new IllegalArgumentException("LevelObject.Type is not a portal type");

            this.portalType = portalType;
            nearestWallFraction = 2;
        }

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

        public Vector2 getWallPoint() {
            return wallPoint;
        }

        public Vector2 getWallNormal() {
            return wallNormal;
        }

        public boolean isWallPortable() {
            return wallIsPortable;
        }
    }
}
