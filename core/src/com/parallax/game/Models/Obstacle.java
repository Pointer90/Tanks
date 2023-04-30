package com.parallax.game.Models;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

public class Obstacle {

    private final BoundingBox collisionModel;
    private final ModelInstance body;
    private boolean isStatic;
    private Vector3 position;
    private String name;
    private Vector3 minBoundary;
    private Vector3 maxBoundary;
    private Vector3 dimensions;
    private Matrix4 transform;
    private Matrix4 translate;
    private Matrix4 rotate;

    public Obstacle(String name, AssetManager asset, float x, float y, float z, float width, float height, float depth, float degree){
        this.name = name;
        isStatic = true;

        position = new Vector3(x, y, z);
        dimensions = new Vector3(width, height, depth);

        body = new ModelInstance(asset.get("Models/Obstacles/" + name + ".g3dj", Model.class));

        collisionModel = new BoundingBox();
        minBoundary = new Vector3();
        maxBoundary = new Vector3(dimensions);

        transform = new Matrix4();
        translate = new Matrix4();
        rotate = new Matrix4();

        translate.translate(position);
        rotate.rotate(Vector3.Y, degree);
        transform.mulLeft(translate).mulLeft(rotate);


        collisionModel.set(minBoundary, maxBoundary);


        body.transform.mulLeft(transform);
        collisionModel.mul(transform);


        transform.toNormalMatrix();
        translate.toNormalMatrix();
        rotate.toNormalMatrix();

    }

    public ModelInstance getBody(){
        return body;
    }

    public boolean isCollision(Bullet bullet){
        return this.collisionModel.intersects(bullet.getCollisionModel());
    }
    public BoundingBox getCollisionModel(){
        return collisionModel;
    }

}
