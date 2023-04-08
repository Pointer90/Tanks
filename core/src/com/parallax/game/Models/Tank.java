package com.parallax.game.Models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

import java.lang.reflect.Array;

public class Tank {

//    -------------- Переменные для колижн модели ------------

    private final BoundingBox collisionModel;
    private final Vector3 minBoundary;
    private final Vector3 maxBoundary;
    private final float height;
    private final float width;
    private final float depth;


//    --------------- Переменные состояния танка ---------------

    private final Matrix4 transformMatrix = new Matrix4();
    private final Vector3 directionBody;
    private final Vector3 directionHead;
    private final Vector3 position;
    private final float speedHead;
    private final float speedBody;
    private final float speedRotateBody;
    public final float health;
    private final boolean isDestroy;


//    ---------------- Переменные модели -------------------------

    private ModelInstance body;
    private ModelInstance head;



    public Tank(float x, float y, float z) {

        position = new Vector3(x, y, z);
        speedBody = 15;
        speedRotateBody = 1000;
        speedHead = 1000;
        isDestroy = false;
        health = 100;


        directionBody = new Vector3(speedBody, 0, speedBody);
        directionHead = new Vector3(speedHead, 0, speedHead);


        height = 1.47f;
        width = 1.48f / 1.4f;
        depth = 0.98f * 1.5f;
        minBoundary = new Vector3(position.x - width, 0, position.z - depth);
        maxBoundary = new Vector3(position.x + width, height, position.z + depth);
        collisionModel = new BoundingBox(minBoundary, maxBoundary);
    }

    public void loadModel(AssetManager asset){
        body = new ModelInstance(asset.get("Models/Tank.obj", Model.class));
        head = new ModelInstance(asset.get("Models/Head.obj", Model.class));

        body.transform.setToTranslation(position);
        head.transform.setToTranslation(position);
    }

    public void moveBody(Touchpad joystickB, Touchpad joystickH, boolean collision){

        float joyBX = -joystickB.getKnobPercentX();
        float joyBY = joystickB.getKnobPercentY();
        float joyHX = -joystickH.getKnobPercentX();
        float joyHY = joystickH.getKnobPercentY();

        transformMatrix.toNormalMatrix();


        if (collision){
            position.add(-joyBX * 2, 0 , -joyBY * 2);
        } else {
            position.add(joyBX / speedBody, 0 , joyBY /speedBody);
        }

        minBoundary.set(position.x - width, 0, position.z - depth);
        maxBoundary.set(position.x + width, height, position.z + depth);

        if (joystickB.isTouched()){
            directionBody.set(-joyBX, 0, -joyBY);
            directionBody.scl(speedRotateBody);
        }

        if (joystickH.isTouched()){
            directionHead.set(-joyHX, 0, -joyHY);
            directionHead.scl(speedHead);
        }
        collisionModel.set(minBoundary, maxBoundary);

        transformMatrix.translate(position);
        body.transform.set(transformMatrix);
        head.transform.set(transformMatrix);


        body.transform.rotateTowardTarget(directionBody, Vector3.Y);
        head.transform.rotateTowardTarget(directionHead, Vector3.Y);
    }

    public boolean isCollision(Tank tank){
        return this.collisionModel.intersects(tank.getCollisionModel());
    }

    public BoundingBox getCollisionModel(){
        return collisionModel;
    }

    public ModelInstance getBody(){
        return body;
    }
    public ModelInstance getHead(){
        return head;
    }
}
