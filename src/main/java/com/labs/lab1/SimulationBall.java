package com.labs.lab1;

import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.HingeJoint;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 * Simulation ball class
 * @author Alex
 */
public class SimulationBall {

    private final SphereCollisionShape sphereCollisionShape;
    private final PhysicsRigidBody physicsRigidBody;

    SimulationBall(BulletAppState bulletAppState, Vector3f location) {

        sphereCollisionShape = new SphereCollisionShape(0.99f);
        physicsRigidBody = new PhysicsRigidBody(sphereCollisionShape);
        physicsRigidBody.setPhysicsLocation(location);
        physicsRigidBody.setMass(15.0f);
        physicsRigidBody.setRestitution(1.0f);
        physicsRigidBody.setFriction(0f);
        bulletAppState.getPhysicsSpace().add(physicsRigidBody);
    }

    public PhysicsRigidBody getPhysicsRigidBody() {
        return physicsRigidBody;
    }

    public void connectToNode(Node node, Vector3f location, BulletAppState bulletAppState) {
        HingeJoint joint = new HingeJoint(node.getControl(RigidBodyControl.class), physicsRigidBody, new Vector3f(0f, 0f, 0f), location, Vector3f.UNIT_Z, Vector3f.UNIT_Z);
        bulletAppState.getPhysicsSpace().add(joint);
    }


}
