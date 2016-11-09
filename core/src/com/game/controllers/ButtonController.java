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
            System.out.println("enters");
            if(button.equals(fixture))
                return button;
        }
        return null;
    }

}