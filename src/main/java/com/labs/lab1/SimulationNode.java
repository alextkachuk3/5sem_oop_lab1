package com.labs.lab1;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Simulation node class
 * @author Alex
 */
public class SimulationNode {
    private final Node node;

    SimulationNode(BulletAppState bulletAppState, AssetManager assetManager, Node rootNode, Vector3f location) {
        node = createPhysicsNode(assetManager, new BoxCollisionShape(new Vector3f( .0f, .0f, .0f)),0);
        node.getControl(RigidBodyControl.class).setPhysicsLocation(location);
        rootNode.attachChild(node);
        bulletAppState.getPhysicsSpace().add(node);
    }

    private Node createPhysicsNode(AssetManager manager, CollisionShape shape, float mass) {
        Node node = new Node("PhysicsNode");
        RigidBodyControl control = new RigidBodyControl(shape, mass);
        node.addControl(control);
        return node;
    }

    public Node getNode() {
        return node;
    }
}
