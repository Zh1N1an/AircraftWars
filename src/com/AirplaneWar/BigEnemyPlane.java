package com.AirplaneWar;

import java.util.Random;

public class BigEnemyPlane extends FlyingObject implements Enemy {
    private int speed = 6;

    public BigEnemyPlane() {
        image = ShootGame.BigBoss;
        width = image.getWidth();
        height = image.getHeight();
        Random r = new Random();
        x = r.nextInt(ShootGame.WIDTH - width + 1);
        y = -height;
    }

    @Override
    public int getScore() {
        return 50;
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
}
