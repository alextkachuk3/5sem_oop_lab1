package com.lab1;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * This is the Main Class of your simulation
 * @author Alex
 */
public class Main extends SimpleApplication implements AnalogListener {

    private BulletAppState bulletAppState;

    private HingeJoint[] arrayJoint;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    /**
     *
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
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Blue);

        stateManager.detach(bulletAppState);
        bulletAppState = new BulletAppState();
        bulletAppState.setDebugEnabled(true);
        stateManager.attach(bulletAppState);

        SphereCollisionShape[] sphereCollisionShapes = new SphereCollisionShape[count];
        PhysicsRigidBody[] physicsRigidBodies = new PhysicsRigidBody[count];
        arrayJoint = new HingeJoint[2 * count];
        Node[] arrayNode = new Node[2 * count];

        bulletAppState.getPhysicsSpace().setAccuracy(0.01f);


        for(int i = 0; i < count; i++) {
            sphereCollisionShapes[i] = new SphereCollisionShape(0.99f);
            physicsRigidBodies[i] = new PhysicsRigidBody(sphereCollisionShapes[i]);
            Vector3f location = new Vector3f(2*i, -4, 0);
            physicsRigidBodies[i].setPhysicsLocation(location);
            physicsRigidBodies[i].setMass(15.0f);
            physicsRigidBodies[i].setRestitution(1.0f);
            physicsRigidBodies[i].setFriction(0f);
            bulletAppState.getPhysicsSpace().add(physicsRigidBodies[i]);
        }


        for(int i = 0; i < count; i++) {

            arrayNode[i * 2] = createPhysicsNode(assetManager, new BoxCollisionShape(new Vector3f( .0f, .0f, .0f)),0);
            arrayNode[i * 2].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(2*i,5f,-1f));
            rootNode.attachChild(arrayNode[i * 2]);
            bulletAppState.getPhysicsSpace().add(arrayNode[i * 2]);

            arrayNode[i * 2 + 1] = createPhysicsNode(assetManager, new BoxCollisionShape(new Vector3f( .0f, .0f, .0f)),0);
            arrayNode[i * 2 + 1].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(2*i,5f,1f));
            rootNode.attachChild(arrayNode[i * 2 + 1]);
            bulletAppState.getPhysicsSpace().add(arrayNode[i * 2 + 1]);
        }

        for(int i = 0; i < count; i++) {
                arrayJoint[2 * i] = new HingeJoint(arrayNode[2 * i].getControl(RigidBodyControl.class), physicsRigidBodies[i], new Vector3f(0f, 0f, 0f), new Vector3f(0f, 4, -1f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
                bulletAppState.getPhysicsSpace().add(arrayJoint[2 * i]);

                arrayJoint[2 * i + 1] = new HingeJoint(arrayNode[2 * i + 1].getControl(RigidBodyControl.class), physicsRigidBodies[i], new Vector3f(0f, 0f, 0f), new Vector3f(0f, 4, 1f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
                bulletAppState.getPhysicsSpace().add(arrayJoint[2 * i + 1]);
        }

        physicsRigidBodies[0].applyCentralForce(new Vector3f(-300f * count, 0f, 0f));
    }

    private Node createPhysicsNode(AssetManager manager, CollisionShape shape, float mass) {
        Node node = new Node("PhysicsNode");
        RigidBodyControl control = new RigidBodyControl(shape, mass);
        node.addControl(control);
        return node;
    }

}

