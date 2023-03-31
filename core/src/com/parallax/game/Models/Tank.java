package com.parallax.game.Models;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Vector3;

public class Tank {

    // TODO не забудь выключить публичность у позиции танка

    public Vector3 position;
    private Vector3 speed;
    private float health;
    private float angleBody;
    private float angleHead;
    private boolean isDestroy;
    private ModelInstance body;

    public Tank(float x, float y, float z) {

        position = new Vector3(x, y, z);
        speed = new Vector3(0,0,0);
        health = 100;
        angleBody = 0;
        angleHead = 0;
        isDestroy = false;

    }

//    TODO Добавить обработку подгрузки башни

    public Tank(float x, float y, float z, Model body) {

        position = new Vector3(x, y, z);
        speed = new Vector3(0,0,0);
        health = 100;
        angleBody = 0;
        angleHead = 0;
        isDestroy = false;

        this.body = new ModelInstance(body);
        this.body.transform.setToTranslation(position);
    }

    public void loadModel(Model body){

        this.body = new ModelInstance(body);
        this.body.transform.setToTranslation(position);
    }

    public ModelInstance getBody(){
        return body;
    }

    public void update(float x, float y, float z, float delta){

        speed.add(x, y, z);
        speed.scl(delta);
        position.add(speed);
        speed.scl(1/delta);
    }

}
