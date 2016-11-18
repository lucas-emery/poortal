package com.game.controllers;

import com.game.models.Model;
import com.game.models.Portal;
import com.game.models.Teleportation;

import java.util.HashSet;

/**
 * Created by lemery on 04/11/16.
 */
public class TeleportationController {

    private static HashSet<Teleportation> teleports = new HashSet<Teleportation>();

    public static void addTeleportation(Teleportation teleportation) {
        teleports.add(teleportation);
    }

    public static void update(Model model) {
        for(Teleportation tele : teleports) {
            if(tele.hasToTeleport()) {
                Portal portal = PortalController.getOtherPortal(tele.getPortalType());
                if(portal != null) {
                    if(model.getJoint()!=null && (tele.getObject()==model.getPlayer().getBody() || tele.getObject()==model.getPlayer().getVicinity().getBody())){
                        model.releaseJoint();
                        model.resetHolding(false);
                    }
                    tele.teleportObject(portal.getPosition(), portal.getPrimary(), portal.getNormal());
                }
            }
        }
    }


    public static void removeTeleportation(Teleportation teleportation) {
        teleports.remove(teleportation);
    }

    /**
     * This method will reset the teleports.
     */
    public static void reset() {
        teleports = new HashSet<Teleportation>();
    }
}
