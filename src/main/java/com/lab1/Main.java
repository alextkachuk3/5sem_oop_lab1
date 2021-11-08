package com.lab1;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
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
        //bulletAppState.setDebugEnabled(true);
        startSimulation();
    }

    public void startSimulation() {
        initBalls(6);
    }

    public void initBalls(int count) {
        Material material = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        material.setColor("Color", ColorRGBA.Blue);

        for(int i = 0; i < count; i++)
        {
            Sphere sphere = new Sphere(64, 64, 1);
            Geometry sphereGeometry = new Geometry("Sphere", sphere);
            sphereGeometry.setMaterial(material);
            sphereGeometry.setLocalTranslation(i, 5, -3);
            sphereGeometry.addControl(new RigidBodyControl(2));
            rootNode.attachChild(sphereGeometry);
            bulletAppState.getPhysicsSpace().add(sphereGeometry);
        }
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

