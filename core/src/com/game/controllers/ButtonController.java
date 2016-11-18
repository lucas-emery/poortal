package com.game.controllers;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.game.models.Button;

import java.util.HashSet;

/**
 * the ButtonController class contains a set with the buttons in the level
 * and handles the timers that the button states activate.
 *
 * @author Pablo Radnic
 * */
public class ButtonController {

    private static HashSet<Button> buttons;

    /**
     * Resets the ButtonControllers button set by asking LevelController
     * for the level's buttons
     */
    public static void reset() {
        buttons = LevelController.getButtons();
    }

    /**
     * Finds button within Level by fixture
     * @param fixture fixture to be found
     * @return Button Object or null depending on the success of the search
     */
    public static Button findButton(Fixture fixture) {
        for(Button button : buttons) {
            if(button.equals(fixture))
                return button;
        }
        return null;
    }

    /**
     * Starts button press timer
     * @param button button to start timer of
     */
    public static void startTimer(Button button) {
        button.setTimer(1);
    }

    /**
     * Stops button press timer
     * @param button button to stop timer of
     */
    public static void stopTimer(Button button) {
        button.setTimer(-1);
    }

    /**
     * Updates button timer value
     * @param time float value representing time to be updated to
     */
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
}