package com.game.models;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by lemery on 04/11/16.
 */
public interface Teleportable {

    public void setTransform(Vector2 position, float angle);

    public Vector2 getPosition();

}
