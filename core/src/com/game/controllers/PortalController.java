package com.game.controllers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.game.models.LevelObject;
import com.game.models.Portal;


public class PortalController {
    private static Portal bluePortal;
    private static Portal orangePortal;


    private static void spawnPortal(Vector2 point, Vector2 normal, LevelObject.Type portalType) {
        System.out.println("Spawn portal");
    }

    public static void firePortal(Vector2 playerPos, Vector2 clickPos, Portal.Type portalType){
        PortalRayCastCallback callback = new PortalRayCastCallback(portalType);
        Controller.queryRayCast(callback, playerPos, clickPos);
    }

    private static class PortalRayCastCallback implements RayCastCallback {

        private LevelObject.Type portalType;

        public PortalRayCastCallback(LevelObject.Type portalType) {
            if(portalType != LevelObject.Type.PORTAL_BLUE && portalType != LevelObject.Type.PORTAL_ORANGE)
                throw new IllegalArgumentException("LevelObject.Type is not a portal type");

            this.portalType = portalType;

        }

        @Override
        public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
            System.out.println("Fixture found");
            Boolean isPortableWall = WallController.isPortableWall(fixture);
            if(isPortableWall == null)
                return 1;

            if(isPortableWall)
                spawnPortal(point, normal, portalType);

            return fraction;
        }
    }
}
