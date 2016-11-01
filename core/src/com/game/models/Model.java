package com.game.models;

import java.util.HashSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.game.services.ConstantsService;


public class Model {

    private HashSet<LevelObject> levelObjects;
    private Player player;
    private World world;
    private float accumulatedTime;
    private boolean lastframe;
    private Joint joint;

    public Model (HashSet<LevelObject> levelObjects, Player player, World world) {
        this.player = player;
        this.levelObjects = levelObjects;
        this.world = world;
        lastframe = false;
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        accumulatedTime += deltaTime;
        while(accumulatedTime >= ConstantsService.WORLD_STEP) {
            world.step(ConstantsService.WORLD_STEP, 8, 3);
            accumulatedTime -= ConstantsService.WORLD_STEP;
        }
        if(player.isHolding()!=lastframe){
            lastframe=player.isHolding();
            if(player.isHolding()){
                createJoint();
            }
            else{
                releaseJoint();
            }
        }
        player.update(deltaTime);
    }

    public World getWorld() {
        return world;
    }

    public void queryRayCast(RayCastCallback callback, Vector2 from, Vector2 to) {
        world.rayCast(callback, from, to);
    }

    public void addObject(LevelObject object) {
        levelObjects.add(object);
        object.setBody(world.createBody(object.getBodyDef()));
    }

    public void removeObject(LevelObject object) {
        levelObjects.remove(object);
        world.destroyBody(object.getBody());
    }

    public void createJoint(){
        RevoluteJointDef rDef;
        rDef = new RevoluteJointDef();
        rDef.bodyA = player.getBody();
        rDef.bodyB = player.getVicinity().getBody();
        rDef.collideConnected = true;

        if (player.isFlipped())
            rDef.localAnchorA.set(-1.5f,0.5f);
        else
            rDef.localAnchorA.set(1.5f,0.5f);   //HAY QUE MOVER ESTAS CONSTANTES

        rDef.localAnchorB.set(0.0f,0);          // A ALGUN SERVICE (para juan no)
        joint = world.createJoint(rDef);
        joint.setUserData("Joint");
    }
    public void releaseJoint(){
        Body body = joint.getBodyB();
        world.destroyJoint(joint);
        if (player.isFlipped())
            body.applyForceToCenter(-150.0f,150.0f,true);
        else
            body.applyForceToCenter(150.0f,150.0f,true);
    }
}
