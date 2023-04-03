package com.parallax.game.Models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

//    Todo Реализовать коллизию с помощью BoundingBox

public class Tank {

    private final Vector3 position;
    private final float speedHead;
    private final float speedBody;
    private final float speedRotateBody;
    private final float health;
    private final boolean isDestroy;
    private ModelInstance body;
    private ModelInstance head;
    private final Matrix4 transformMatrix = new Matrix4();
    private final Vector3 directionBody;
    private final Vector3 directionHead;

    public Tank(float x, float y, float z) {

        position = new Vector3(x, y, z);
        speedBody = 15;
        speedRotateBody = 1000;
        speedHead = 1000;
        directionBody = new Vector3(0, 0, 0);
        directionHead = new Vector3(0, 0, 0);
        health = 100;
        isDestroy = false;
    }

    public void loadModel(AssetManager asset){
        body = new ModelInstance(asset.get("Models/Tank.obj", Model.class));
        head = new ModelInstance(asset.get("Models/Head.obj", Model.class));

        body.transform.setToTranslation(position);
        head.transform.setToTranslation(position);
        body.transform.setToRotation(Vector3.Y, 180);
        head.transform.setToRotation(Vector3.Y, 180);
    }

    // TODO Доделать систему поворота на месте
    public void moveBody(Touchpad joystickB, Touchpad joystickH, float delta){

        float joyBX = joystickB.getKnobPercentX();
        float joyBY = joystickB.getKnobPercentY();
        float joyHX = joystickH.getKnobPercentX();
        float joyHY = joystickH.getKnobPercentY();


        position.add(joyBX / speedBody, 0 , -joyBY /speedBody);
        directionBody.set(-joyBX * speedRotateBody, 0, joyBY * speedRotateBody);
        directionHead.set(-joyHX * speedHead, 0, joyHY * speedHead);

        transformMatrix.translate(position);
        body.transform.set(transformMatrix);
        head.transform.set(transformMatrix);
        position.set(0, 0, 0);

        body.transform.rotateTowardTarget(directionBody, Vector3.Y);
        if (joystickH.isTouched()) head.transform.rotateTowardTarget(directionHead, Vector3.Y);
    }

    public ModelInstance getBody(){
        return body;
    }
    public ModelInstance getHead(){
        return head;
    }
}
