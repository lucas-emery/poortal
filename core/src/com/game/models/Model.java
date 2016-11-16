package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.controllers.LevelController;
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
    private RevoluteJoint joint;

    /**
     * Constructor which sets the player, world and the HashSet levelobjects
     */
    public Model () {
        this.player = PlayerController.getPlayer();
        this.levelObjects = LevelController.getLevelObjects();
        this.world = LevelController.getLevelWorld();
        wasHolding = false;
    }

    public Player getPlayer(){
        return player;
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
        updateJoint();
    }
    public void resetHolding(boolean isholding){
        player.setHolding(isholding);
        wasHolding=isholding;
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
        float degtorad=0.0174533f;

        Body playerBody = player.getBody();
        Body cubeBody = player.getVicinity().getBody();

        RevoluteJointDef rDef;
        rDef = new RevoluteJointDef();
        rDef.bodyA = playerBody;
        rDef.bodyB = cubeBody;
        rDef.collideConnected = false;
        rDef.enableLimit=false;

        Vector2 gunPos = playerBody.getPosition().add(0, ConstantsService.PLAYER_GUN_OFFSET);
        Vector2 cubePos = cubeBody.getPosition();
        Vector2 jointVec = new Vector2(cubePos.x - gunPos.x, cubePos.y - gunPos.y);
        rDef.referenceAngle = jointVec.angleRad(ConstantsService.CARTESIAN_VERSOR_Y);
//        rDef.lowerAngle=-120*degtorad;
//        rDef.upperAngle=120*degtorad;
        rDef.enableMotor = true;
        rDef.maxMotorTorque = 10;
        rDef.motorSpeed = 0;

        jointVec.rotate(180).setLength(1.7f);
        rDef.localAnchorB.set(jointVec);
        /*if (player.isLookingLeft())
            rDef.localAnchorB.set(-1.5f,0.5f);
        else
            rDef.localAnchorB.set(1.5f,0.5f);*/

        rDef.localAnchorA.set(0,ConstantsService.PLAYER_GUN_OFFSET);
        joint = (RevoluteJoint) world.createJoint(rDef);
        joint.setUserData("Joint");
    }
    public void applyForce(){
        joint.getBodyB().applyForceToCenter(0,50,true);
    }

    /**
     * Method which removes Box2D joint from the player and the held object
     */
    public void releaseJoint(){
        Body body = joint.getBodyB();
        world.destroyJoint(joint);
        joint=null;
    }

    public Joint getJoint() {
        return joint;
    }

    public void updateJoint() {
        float targetAngle = -PlayerController.getAimingAngle();
        if(joint != null) {
            float angle = joint.getJointAngle();
            float vel = (targetAngle - angle)*10;
            joint.setMotorSpeed(vel);
        }
    }
}
