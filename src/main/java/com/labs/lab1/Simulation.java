package com.labs.lab1;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Simulation class
 * @author Alex
 */
public class Simulation  extends SimpleApplication implements AnalogListener {

    private BulletAppState bulletAppState;

    private SimulationBall[] simulationBalls;

    private SimulationNode[] simulationNodes;


    /**
     * Simulation initialization
     */
    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        setupKeys();
        initSimulation(4);
    }

    /**
     * Setup keys for setting count of balls
     */
    private void setupKeys() {
        inputManager.addMapping("One", new KeyTrigger(KeyInput.KEY_1));
        inputManager.addMapping("Two", new KeyTrigger(KeyInput.KEY_2));
        inputManager.addMapping("Three", new KeyTrigger(KeyInput.KEY_3));
        inputManager.addMapping("Four", new KeyTrigger(KeyInput.KEY_4));
        inputManager.addMapping("Five", new KeyTrigger(KeyInput.KEY_5));
        inputManager.addMapping("Six", new KeyTrigger(KeyInput.KEY_6));
        inputManager.addListener((InputListener) this, "One", "Two", "Three", "Four", "Five", "Six");
    }

    /**
     * User clicks handler
     */
    @Override
    public void onAnalog(String binding, float value, float tpf) {
        if(binding.equals("One")) {
            initSimulation(1);
        }
        else if(binding.equals("Two")) {
            initSimulation(2);
        }
        else if(binding.equals("Three")) {
            initSimulation(3);
        }
        else if(binding.equals("Four")) {
            initSimulation(4);
        }
        else if(binding.equals("Five")) {
            initSimulation(5);
        }
        else if(binding.equals("Six")) {
            initSimulation(6);
        }
    }

    /**
     * Simulation initialization
     * @param count Count of balls
     */
    public void initSimulation(int count) {
        stateManager.detach(bulletAppState);
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);

        simulationBalls = new SimulationBall[count];
        simulationNodes = new SimulationNode[2 * count]; // 2 - for each ball 2 nodes

        bulletAppState.getPhysicsSpace().setAccuracy(0.01f);


        for(int i = 0; i < count; i++) {
            simulationBalls[i] = new SimulationBall(bulletAppState, new Vector3f(2*i, -4, 0));
        }


        for(int i = 0; i < count; i++) {
            simulationNodes[i * 2] = new SimulationNode(bulletAppState, assetManager, rootNode, new Vector3f(2*i,5f,-1f));
            simulationNodes[i * 2 + 1] = new SimulationNode(bulletAppState, assetManager, rootNode, new Vector3f(2*i,5f,1f));
        }

        for(int i = 0; i < count; i++) {
            simulationBalls[i].connectToNode(simulationNodes[2 * i].getNode(), new Vector3f(0f, 4, -1f), bulletAppState);
            simulationBalls[i].connectToNode(simulationNodes[2 * i + 1].getNode(), new Vector3f(0f, 4, 1f), bulletAppState);
        }

        simulationBalls[0].getPhysicsRigidBody().applyCentralForce(new Vector3f(-300f * count, 0f, 0f));
    }

}
