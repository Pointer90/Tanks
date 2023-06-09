package com.parallax.game.Models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;

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
    public Matrix4 translation = new Matrix4();
    public Matrix4 rotation = new Matrix4();
    private final Vector3 directionBody;
    private final Vector3 directionHead;
    private   Vector3 position;
    private final float speedHead;
    private final float speedBody;
    private final float speedRotateBody;
    public float health;
    public boolean isDestroy;
    private boolean isReloading;
    private float timeReloading;


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
        isReloading = false;
        timeReloading = 1.5f;

        directionBody = new Vector3(speedBody, 0, speedBody);
        directionHead = new Vector3(speedHead, 0, speedHead);


        height = 1.47f;
        width = 1.48f / 1.4f;
        depth = 0.98f * 1.5f;
        minBoundary = new Vector3(position.x - width, 0, position.z - depth);
        maxBoundary = new Vector3(position.x + width, height, position.z + depth);
        collisionModel = new BoundingBox(minBoundary, maxBoundary);
    }

    public boolean isReloading(){
        return isReloading;
    }

    public void reload(float delta){
        if (timeReloading < 0){
            timeReloading = 1.5f;
            isReloading = false;
        } else {
            timeReloading -= delta;
        }
    }

    public void getDamage(){
        health -= 10;
    }

    public void setLoading(AssetManager gam){
        gam.load("Models/Body.g3dj", Model.class);
        gam.load("Models/Head.g3dj", Model.class);
    }
    public void loadModel(AssetManager asset){
        body = new ModelInstance(asset.get("Models/Body.g3dj", Model.class));
        head = new ModelInstance(asset.get("Models/Head.g3dj", Model.class));

        body.transform.setToTranslation(position);
        head.transform.setToTranslation(position);
    }

    public void moveBody(Touchpad joystickB, Touchpad joystickH){

        float joyBX = -joystickB.getKnobPercentX();
        float joyBY = joystickB.getKnobPercentY();
        float joyHX = -joystickH.getKnobPercentX();
        float joyHY = joystickH.getKnobPercentY();

        transformMatrix.toNormalMatrix();


        position.add(joyBX / speedBody, 0 , joyBY /speedBody);

        minBoundary.set(position.x - width, 0, position.z - depth);
        maxBoundary.set(position.x + width, height, position.z + depth);

        if (joystickB.isTouched()){
            directionBody.set(-joyBX, 0, -joyBY);
            directionBody.nor();
            directionBody.scl(speedRotateBody);
        }

        if (joystickH.isTouched()){
            directionHead.set(-joyHX, 0, -joyHY);
            directionHead.nor();
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

    public boolean isCollision(Bullet bullet){
        return this.collisionModel.intersects(bullet.getCollisionModel());
    }

    public boolean isCollision(Obstacle obstacle){
        return this.collisionModel.intersects(obstacle.getCollisionModel());
    }

    public void repel(){
        directionBody.nor();
        directionBody.scl(0.07f);
        position.add(directionBody);
    }

    public BoundingBox getCollisionModel(){
        return collisionModel;
    }

    public Bullet shot(Bullet bullet){
        Vector3 directionB = new Vector3(directionHead);
        Vector3 positionB = new Vector3(position);

        positionB.y = 1.6f;
        directionB.nor();
        directionB.scl(-3);
        positionB.add(directionB);
        directionB.scl(-1);

        bullet.setDirection(directionB);
        bullet.setPosition(positionB);
        isReloading = true;
        return bullet;
    }

    public ModelInstance getBody(){
        return body;
    }
    public ModelInstance getHead(){
        return head;
    }
}
