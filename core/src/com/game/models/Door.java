package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Created by Usuario on 03/11/2016.
 */
public class Door extends LevelObject {

    private boolean isclosed;
    /**
     * By default Door is initialised as closed
     */
    public Door(Vector2 position,boolean pointsRight){
        this.position = position;
        isclosed = true;
        if(pointsRight)
            type = Type.RIGHT_DOOR;
        else
            type = Type.LEFT_DOOR;

        createBodyDef();
    }

    public void createFixtureDef(){
        Shape shape = BodyService.getShape(type);
        FixtureDef fixtureDef = BodyService.getFixtureDef(type);
        fixtureDef.shape=shape;
        body.createFixture(fixtureDef).setUserData(new Collider(Collider.Type.CUBE));
        shape.dispose();
    }
    public boolean isClosed(){
        return isclosed;
    }
    public void setClosed(boolean value){
        isclosed = value;
    }

}
