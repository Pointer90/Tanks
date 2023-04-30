package com.parallax.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Array;
import com.parallax.game.Models.Obstacle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Parser {

    private String name;

    public Parser(String name){
        this.name = name;
    }

    public void parseToObstacles(Array<Obstacle> obstacles, AssetManager gam){
        File file = new File("Config/"+ name + ".txt");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String[] words;

            br.readLine();

            while ((line = br.readLine()) != null) {
                words = line.split(",");

                String nameObst = words[0];
                float x = Float.parseFloat(words[1]);
                float y = Float.parseFloat(words[2]);
                float z = Float.parseFloat(words[3]);
                float width = Float.parseFloat(words[4]);
                float height = Float.parseFloat(words[5]);
                float depth = Float.parseFloat(words[6]);
                float degree = Float.parseFloat(words[7]);

                obstacles.add(new Obstacle(nameObst, gam, x, y, z, width, height, depth, degree));
            }
            br.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getMetaObstacles(){
        File file = new File("Config/" + name + ".txt");
        String[] metaObstacles = new String[]{};

        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            metaObstacles = line.split(",");
            br.close();
        }
        catch (IOException ex){
            ex.printStackTrace();
        }

        return metaObstacles;
    }
}
