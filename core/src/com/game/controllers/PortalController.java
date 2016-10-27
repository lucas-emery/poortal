package com.game.controllers;

import com.game.models.Model;
import com.game.models.Portal;

/**
 * Created by juan on 24/10/16.
 */
public class PortalController {
    protected static Portal bluePortal;
    protected static Portal orangePortal;

    public PortalController(Portal bluePortal, Portal orangePortal){
        this.bluePortal = bluePortal;
        this.orangePortal = orangePortal;
    }

    public void spawnBluePortal(Portal portal){
        if(portal.getColor() != "BLUE")
            throw new IllegalArgumentException("Portal is not blue");

        bluePortal = portal;

    }

    public void spawnOrangePortal(Portal portal){
        if(portal.getColor() != "ORANGE")
            throw new IllegalArgumentException("Portal is not orange");

        orangePortal = portal;
    }
}
