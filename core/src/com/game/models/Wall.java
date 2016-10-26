package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.game.services.BodyService;
import com.badlogic.gdx.physics.box2d.*;
import com.game.services.AssetsService;

/**
 * Created by Usuario on 26/10/2016.
 */
public class Wall {

    private Body body;
    private BodyDef bodyDef;
    private Vector2 position;

    public Wall(Vector2 position) { //Concept

        this.position = position;
        createBodyDef();

    }

    public void setWall(Body wall, Vector2 beginning, Vector2 end){
        this.body = wall;

        EdgeShape shape = new EdgeShape();
        shape.set(beginning,end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
        shape.dispose();

    }

    public void createBodyDef() {
        bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(position.x, position.y);
    }


    public BodyDef getBodyDef(){
        return bodyDef;
    }
}
