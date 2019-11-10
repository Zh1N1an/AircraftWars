package com.AirplaneWar;

import java.util.Random;

public class EnemyPlane extends FlyingObject implements Enemy {
    private int speed = 3;      //敌机的速度

    public EnemyPlane() {
        image = ShootGame.airplane;
        width = image.getWidth();
        height = image.getHeight();
        Random r = new Random();
        x = r.nextInt(ShootGame.WIDTH - width + 1);
        y = -height;
    }

    @Override
    public void step() {
        y = y + speed;
    }

    @Override
    public boolean outOfBorder() {
        if (y >= ShootGame.HEIGHT) {
//            System.out.println("敌机越界");
            return true;
        } else
            return false;

    }

    @Override
    public int getScore() {
        return 10;
    }
}
