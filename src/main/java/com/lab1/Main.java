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
public class Main extends SimpleApplication {

    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.setDebugEnabled(true);
        startSimulation();
    }

    public void startSimulation() {
        initBalls(6);
        //setupJoint();
    }

    public void initBalls(int count) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Blue);

        Geometry[] arraySphereGeometry = new Geometry[count];
        HingeJoint[] arrayJoint = new HingeJoint[count];
        Node[] arrayNode = new Node[count];

        for(int i = 0; i < count; i++) {
            Sphere sphere = new Sphere(64, 64, 0.99f);
            arraySphereGeometry[i] = new Geometry("Sphere", sphere);
            arraySphereGeometry[i].setMaterial(material);
            arraySphereGeometry[i].setLocalTranslation(2*i, -1, 0);
            arraySphereGeometry[i].addControl(new RigidBodyControl(2));
            rootNode.attachChild(arraySphereGeometry[i]);
            bulletAppState.getPhysicsSpace().add(arraySphereGeometry[i]);
        }

        for(int i = 0; i < count; i++) {
            arrayNode[i] = createPhysicsTestNode(assetManager, new BoxCollisionShape(new Vector3f( .0f, .0f, .0f)),0);
            arrayNode[i].getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(2*i,5f,0f));
            rootNode.attachChild(arrayNode[i]);
            bulletAppState.getPhysicsSpace().add(arrayNode[i]);
        }

        for(int i = 0; i < count; i++) {
            arrayJoint[i] = new HingeJoint(arrayNode[i].getControl(RigidBodyControl.class), arraySphereGeometry[i].getControl(RigidBodyControl.class), new Vector3f(0f, 0f, 0f), new Vector3f(0f,4,0f), Vector3f.UNIT_Z, Vector3f.UNIT_Z);
            bulletAppState.getPhysicsSpace().add(arrayJoint[i]);
        }

    }

    public static Node createPhysicsTestNode(AssetManager manager, CollisionShape shape, float mass) {
        Node node = new Node("PhysicsNode");
        RigidBodyControl control = new RigidBodyControl(shape, mass);
        node.addControl(control);
        return node;
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}

