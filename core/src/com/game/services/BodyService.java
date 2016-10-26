package com.game.services;

import com.game.models.LevelObject;


public class BodyService {
    public static Boolean isDynamic(LevelObject.Type type){
        switch(type) {
            case CUBE:
                return true;

            default:
                return false;
        }
    }
}
