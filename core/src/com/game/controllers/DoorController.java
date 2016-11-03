package com.game.controllers;

/**
 * Created by juan on 03/11/16.
 */
public class DoorController {
    private static boolean isOpen;

    public void open(){
        isOpen = true;
    }

    public void close(){
        isOpen = false;
    }

    public boolean doorOpenState(){
        return isOpen;
    }
}
