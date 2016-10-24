package com.game.models;

import com.game.controllers.Controller;

/**
 * Created by juan on 24/10/16.
 */
public class Model {

    private Controller controller;

    public Model (Controller controller) {
        this.controller = controller;

        //load map

        restart();
    }

    public void restart() {

    }
}
