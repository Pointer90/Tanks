package com.parallax.game.Models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Bullet {

    private final Vector3 position;
    private final Vector3 direction;
    private final float speed;
    private final BoundingBox collisionModel;
    private ModelInstance body;
    private final float height;
    private final float width;
    private final float depth;
    private boolean isActive;
    private final Matrix4 transformMatrix;
    private final Vector3 minBoundary;
    private final Vector3 maxBoundary;

    public Bullet(){
        position = new Vector3();
        direction = new Vector3();
        speed = 0.8f;
        height = 0.3f;
        width = 0.3f;
        depth = 0.6f;
        isActive = false;
        minBoundary = new Vector3(position.x - width, 0, position.z - depth);
        maxBoundary = new Vector3(position.x + width, height, position.z + depth);
        collisionModel = new BoundingBox(minBoundary, maxBoundary);
        transformMatrix = new Matrix4();
    }

    public void update(){
        direction.nor();
        direction.scl(speed);
        position.add(-direction.x, direction.y, -direction.z);
        transformMatrix.toNormalMatrix();

        minBoundary.set(position.x - width, 0, position.z - depth);
        maxBoundary.set(position.x + width, height, position.z + depth);

        collisionModel.set(minBoundary, maxBoundary);

        transformMatrix.translate(position);
        body.transform.set(transformMatrix);
        body.transform.rotateTowardTarget(direction, Vector3.Y);
    }

    public void hit(){
        direction.setZero();
        isActive = false;
    }

    public boolean isActive(){
        return isActive;
    }

    public void activate(){
        isActive = true;
    }

    public void deactivate(){
        isActive = false;
    }

    public BoundingBox getCollisionModel(){
        return collisionModel;
    }

    public void loadModel(AssetManager asset){
        body = new ModelInstance(asset.get("Models/Bullet.g3dj", Model.class));
        body.transform.setToTranslation(position);
    }

    public ModelInstance getBody(){
        return body;
    }

    public void setDirection(Vector3 directionMove){
        direction.set(directionMove);
    }

    public void setPosition(Vector3 newPosition){
        position.set(newPosition);
    }
}
