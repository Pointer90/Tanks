package com.parallax.game;

import com.badlogic.gdx.utils.Array;
import com.parallax.game.Models.Bullet;
import com.parallax.game.Models.Tank;

public class GameCollisionManager {

    public Array<Bullet> bullets;
    public Array<Tank> tanks;

    public GameCollisionManager(Array<Bullet> arrayBullets, Array<Tank> arrayTanks){
        bullets = arrayBullets;
        tanks = arrayTanks;
    }

    public void update(){
        for (int i = 0; i < tanks.size - 1; i++){
            Tank currentTank = tanks.get(i);

            for (int j = i + 1; j < tanks.size; j++){
                Tank tank = tanks.get(j);

                if (currentTank.isCollision(tank)){
                    currentTank.repel();
                    tank.repel();
                }
            }
        }


        for (int i = 0; i < tanks.size; i++){
            Tank tank = tanks.get(i);
            for (int j = 0; j < bullets.size; j++){
                if (tank.isCollision(bullets.get(j))){
                    bullets.get(j).hit();
                } else tank.isDestroy = false;
            }
        }
    }

}
