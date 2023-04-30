package com.parallax.game;

import com.badlogic.gdx.utils.Array;
import com.parallax.game.Models.Bullet;
import com.parallax.game.Models.Obstacle;
import com.parallax.game.Models.Tank;

public class GameCollisionManager {

    public Array<Bullet> bullets;
    public Array<Tank> tanks;
    public Array<Obstacle> obstacles;

    public GameCollisionManager(Array<Bullet> arrayBullets, Array<Tank> arrayTanks, Array<Obstacle> arrayObstacles){
        bullets = arrayBullets;
        tanks = arrayTanks;
        obstacles = arrayObstacles;
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

            for (int k = 0; k < obstacles.size; k++){
                Obstacle obstacle = obstacles.get(k);

                if (currentTank.isCollision(obstacle)){
                    currentTank.repel();
                }
            }
        }


        for (int i = 0; i < bullets.size; i++){
            Bullet bullet = bullets.get(i);

            for (int j = 0; j < tanks.size; j++){
                Tank tank = tanks.get(j);

                if (tank.isCollision(bullet)){
                    bullet.hit();
                    tank.getDamage();
                } else tank.isDestroy = false;
            }

            for (int k = 0; k < obstacles.size; k++){
                Obstacle obstacle = obstacles.get(k);
                if (obstacle.isCollision(bullet)) bullet.hit();
            }
        }

    }

}
