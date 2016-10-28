package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.game.services.BodyService;
import com.badlogic.gdx.physics.box2d.*;

import static com.game.models.LevelObject.Type.*;

/**
 * Created by Usuario on 26/10/2016.
 */
public class Wall {

    private Body body;
    private BodyDef bodyDef;
    private Vector2 position;
    private LevelObject.Type type;
    private Fixture fixture;
    private boolean portable;

    public Wall(Vector2 position,boolean portable) { //Concept

        this.portable = portable;
        this.position = position;
        createBodyDef();
        type = WALL;

    }

    public void setWall(Body wall, Vector2 end, boolean floor){
        this.body = wall;

        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(0,0),end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        if(floor)
            fixtureDef.friction = 1.5f;
        else
            fixtureDef.friction= 0;

        fixture = body.createFixture(fixtureDef);
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

    //checkea por address
    public boolean equals(Fixture otherFixture){
        return fixture.equals(otherFixture);
    }
}
