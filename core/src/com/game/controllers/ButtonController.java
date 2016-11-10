package com.game.controllers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.Button;
import com.game.models.Wall;

import java.util.HashSet;

/**
 *
 *
 *
 * */
public class ButtonController {

    private static HashSet<Button> buttons;

    public static void setButtons(HashSet<Button> levelButtons) {
        buttons = levelButtons;
    }

    public static Button findButton(Fixture fixture) {
        for(Button button : buttons) {
            if(button.equals(fixture))
                return button;
        }
        return null;
    }

    public static void startTimer(Button button) {
        button.timer=5;
    }

    public static void stopTimer(Button button) {
        button.timer=-1;
    }

    public static void updateTimer(float time){
        for(Button button : buttons){
            if(button.getTimer()>0) {
                button.setTimer(button.getTimer()-time);
                if (button.getTimer() < 0) {
                    button.setActive(false);
                    button.setTimer(-1);
                }
            }
        }
    }

    public static void printTimer(){
        for(Button button: buttons){
            System.out.println(button.getTimer());
            System.out.println(button.isActive());
        }
    }
}