package com.lab1;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.joints.Point2PointJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.InputListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication implements AnalogListener {

    private BulletAppState bulletAppState;

    private HingeJoint[] arrayJoint;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        setupKeys();
        float gravity = 150f;
        Vector3f gravityVector = new Vector3f(0f, -gravity, 0f);
        bulletAppState.getPhysicsSpace().setGravity(gravityVector);
        startSimulation();
    }

    private void setupKeys() {
        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_H));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_K));
        inputManager.addMapping("Swing", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener((InputListener) this, "Left", "Right", "Swing");
    }

    @Override
    public void onAnalog(String binding, float value, float tpf) {
        if(binding.equals("Left")){
            arrayJoint[0].enableMotor(true, 1.1f, 2f);
        }
        else if(binding.equals("Right")){
            arrayJoint[0].enableMotor(true, 1.2f, 2f);
        }
        else if(binding.equals("Swing")){
            arrayJoint[0].enableMotor(false, 0, 0);
        }
    }

    public void startSimulation() {
        initBalls(6);
    }

    public void initBalls(int count) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Blue);

        Geometry[] arraySphereGeometry = new Geometry[count];
        arrayJoint = new HingeJoint[2 * count];
        Node[] arrayNode = new Node[2 * count];


        for(int i = 0; i < count; i++) {
            Sphere sphere = new Sphere(64, 64, 1f);
            arraySphereGeometry[i] = new Geometry("Sphere", sphere);
            arraySphereGeometry[i].setMaterial(material);
            arraySphereGeometry[i].setLocalTranslation(2*i, -4, 0);
            arraySphereGeometry[i].addControl(new RigidBodyControl(1f));
            rootNode.attachChild(arraySphereGeometry[i]);
            bulletAppState.getPhysicsSpace().add(arraySphereGeometry[i]);
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
                arrayJoint[2 * i] = new HingeJoint(arrayNode[2 * i].getControl(RigidBodyControl.class), arraySphereGeometry[i].getControl(RigidBodyControl.class), new Vector3f(0f, 0f, 0f), new Vector3f(0f, 4, -1f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
                bulletAppState.getPhysicsSpace().add(arrayJoint[2 * i]);

                arrayJoint[2 * i + 1] = new HingeJoint(arrayNode[2 * i + 1].getControl(RigidBodyControl.class), arraySphereGeometry[i].getControl(RigidBodyControl.class), new Vector3f(0f, 0f, 0f), new Vector3f(0f, 4, 1f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
                bulletAppState.getPhysicsSpace().add(arrayJoint[2 * i + 1]);
        }
    }

    public static Node createPhysicsNode(AssetManager manager, CollisionShape shape, float mass) {
        Node node = new Node("PhysicsNode");
        RigidBodyControl control = new RigidBodyControl(shape, mass);
        node.addControl(control);
        return node;
    }

    @Override
    public void simpleUpdate(float tpf) {
        //System.out.print(tpf);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}

