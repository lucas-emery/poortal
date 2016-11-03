package com.game.models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.game.services.BodyService;
import com.game.services.ConstantsService;

/**
 * Created by juan on 03/11/16.
 */
public class Door extends LevelObject{

        public Door(Vector2 position) {
            this.position = position;
            this.type = LevelObject.Type.DOOR;
            createBodyDef();
        }

        @Override
        public void createFixtureDef(){
            Shape shape = BodyService.getShape(type);
            FixtureDef fixtureDef = BodyService.getFixtureDef(type);
            fixtureDef.shape=shape;
            body.createFixture(fixtureDef).setUserData(ConstantsService.ColliderType.DOOR.val());
            shape.dispose();
    }
}
