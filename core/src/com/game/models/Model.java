package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.controllers.PlayerController;
import com.game.services.ConstantsService;

/**
 * Class is the component of the MVCS which handles the physical
 * being of the objects within the level, it creates the world,
 * adds/removes the objects, and updates it in every render.
 * @author Lucas Emery
 */
public class Model {

    private HashSet<LevelObject> levelObjects;
    private Player player;
    private World world;
    private float accumulatedTime;
    private boolean wasHolding;
    private Joint joint;

    /**
     * Constructor which sets the player, world and the HashSet levelobjects
     * @param levelObjects HashSet with LevelObjects contained in the level
     * @param player Model which handles the physicality of the player
     * @param world Model which handles the physicality of the world
     */
    public Model (HashSet<LevelObject> levelObjects, Player player, World world) {
        this.player = player;
        this.levelObjects = levelObjects;
        this.world = world;
        wasHolding = false;
    }

    /**
     * Method which is called every render frame, updates player isHolding state and
     * the accumulated game time
     */
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        accumulatedTime += deltaTime;
        while(accumulatedTime >= ConstantsService.WORLD_STEP) {
            world.step(ConstantsService.WORLD_STEP, 8, 3);
            accumulatedTime -= ConstantsService.WORLD_STEP;
        }
        if(player.isHolding()!= wasHolding ){
            wasHolding = player.isHolding();
            if(player.isHolding()){
                createJoint();
            }
            else{
                releaseJoint();
            }
        }
        PlayerController.update(deltaTime);
    }

    /**
     * Method which gets size of Level Objects set
     * @return integer with size
     */
    public int sizeOfLevelObjectsSet(){
        return levelObjects.size();
    }

    /**
     * Method which gets World Object from attribute world
     * @return World world
     */
    public World getWorld() {
        return world;
    }


    /**
     * Wrapper for Box2D rayCast method
     * @param callback RayCastCallback for world rayCast
     * @param from Vector2 coordinates for rayCast
     * @param to Vector2 coordinates for rayCast
     */
    public void queryRayCast(RayCastCallback callback, Vector2 from, Vector2 to) {
        world.rayCast(callback, from, to);
    }

    /**
     * Method which adds LevelObject to the HashSet of LevelObjects within the Model
     * @param object to be added
     */
    public void addObject(LevelObject object) {
        levelObjects.add(object);
        object.setBody(world.createBody(object.getBodyDef()));
    }

    /**
     * Method which removes LevelObject from the HashSet of LevelObjects within the Model
     * @param object to be removed
     */
    public void removeObject(LevelObject object) {
        levelObjects.remove(object);
        world.destroyBody(object.getBody());
    }

    /**
     * Method which creates Box2D joint for the player and the held object
     */
    public void createJoint(){
        RevoluteJointDef rDef;
        rDef = new RevoluteJointDef();
        rDef.bodyA = player.getBody();
        rDef.bodyB = player.getVicinity().getBody();
        rDef.collideConnected = true;

        if (player.isLookingLeft())
            rDef.localAnchorA.set(-1.5f,0.5f);
        else
            rDef.localAnchorA.set(1.5f,0.5f);

        rDef.localAnchorB.set(0.0f,0);
        joint = world.createJoint(rDef);
        joint.setUserData("Joint");
    }

    /**
     * Method which removes Box2D joint from the player and the held object
     */
    public void releaseJoint(){
        Body body = joint.getBodyB();
        world.destroyJoint(joint);
        if (player.isLookingLeft())
            body.applyForceToCenter(-150.0f,150.0f,true);
        else
            body.applyForceToCenter(150.0f,150.0f,true);
    }
}
