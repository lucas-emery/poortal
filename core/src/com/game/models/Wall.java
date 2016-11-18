package com.game.models;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.game.models.LevelObject.Type.*;

/**
 * Class representing a physical wall in the level
 * @author Francisco Delgado
 */
public class Wall {

    private Body body;
    private BodyDef bodyDef;
    private Vector2 position;
    private LevelObject.Type type;
    private Fixture fixture;
    private boolean portable;

    /**
     * Constructor for the wall, sets portable attribute and position within the level
     * @param position Vector2 with position coordinates
     * @param portable Boolean value for portable attribute
     */
    public Wall(Vector2 position,boolean portable) {

        this.portable = portable;
        this.position = position;
        createBodyDef();
        type = WALL;

    }

    /**
     * Method which sets fixture properties for the Wall object
     * @param wall Body for the LevelObject
     * @param end  Vector2 stating placing position relative to the previous position of the object
     * @param floor Boolean value representing if the Wall object is a floor
     */
    public void setWall(Body wall, Vector2 end, boolean floor){
        this.body = wall;

        EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(0,0),end);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        if(floor)
            fixtureDef.friction = 2f;
        else
            fixtureDef.friction= 0;

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(new Collider(Collider.Type.WALL));
        shape.dispose();

    }

    /**
     * Method which creates the bodyDef for the Wall object
     */
    public void createBodyDef() {
        bodyDef = new BodyDef();

        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(position.x, position.y);
    }

    /**
     * Method which gets the bodyDef of the Wall object
     * @return BodyDef bodyDef attribute
     */
    public BodyDef getBodyDef(){
        return bodyDef;
    }

    /**
     * Method which checks received fixture by address with Wall Object
     * @param otherFixture fixture for wall to be compared with
     * @return boolean value which states if the received fixture is in contact with the walls fixture
     */
    public boolean equals(Fixture otherFixture){
        return fixture.equals(otherFixture);
    }

    /**
     * hashcode generator for wall fixture
     * @return int hashcode for the wall fixture
     */
    public int hashCode(){
        return fixture.hashCode();
    }

    /**
     * Method which gets portable attribute for the wall
     * @return boolean value of portable attribute
     */
    public boolean isPortable() {
        return portable;
    }
}