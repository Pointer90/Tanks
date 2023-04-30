package com.parallax.game.Maps;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.utils.Array;
import com.parallax.game.Models.Obstacle;
import com.parallax.game.Parser;

public class Map {

    private Array<Obstacle> obstacles;
    private Array<ModelInstance> instances;
    private boolean loading;

    private String[] meta;
    private Parser parser;

    public Map(){
        obstacles = new Array<Obstacle>();
        instances = new Array<ModelInstance>();
        loading = true;

        parser = new Parser("Desert");
        meta = parser.getMetaObstacles();
    }

    public void setLoading(AssetManager gam){
        for (int i = 0; i < meta.length; i++) gam.load("Models/Obstacles/" + meta[i] + ".g3dj", Model.class);
    }

    public boolean isLoading(){
        return loading;
    }

    public void load(AssetManager gam){
        if (gam.update()){

            parser.parseToObstacles(obstacles, gam);

            for (int i = 0; i < obstacles.size; i++){
                ModelInstance body = obstacles.get(i).getBody();
                instances.add(body);
            }

            loading = false;
        }
    }

    public Array<ModelInstance> getRenderInstances(){
        return instances;
    }

    public Array<Obstacle> getObstacles(){
        return obstacles;
    }
}
