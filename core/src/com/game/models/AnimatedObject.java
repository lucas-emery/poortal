package com.game.models;

/**
 * This interface is implemented by LevelObjects when the sprite
 * associated with the object is animated meaning it will have more
 * than one sprite associated with the object om its view.
 * Created by fdelgado on 04/11/16.
 */
public interface AnimatedObject {

    /**
     * This method determines weather a current animation is active
     * for example returns true when a button or a door is activated
     * or a door
     * @return boolean which is true when the object is active and false
     *         when it is not.
     */
    public boolean isActive();

}
