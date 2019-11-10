package com.AirplaneWar;

import java.util.Random;

public class Treasure extends FlyingObject implements Award {
    private int xspeed = 4;     //蜜蜂x方向的速度
    private int yspeed = 4;     //蜜蜂y方向的速度
    private int awardType;      //决定被击中后给英雄机的奖励类型，0--加命，1--加火力

    public Treasure() {
        image = ShootGame.treasure;
        width = image.getWidth();
        height = image.getHeight();
        Random r = new Random();
        x = r.nextInt(ShootGame.WIDTH - width + 1);
        y = -height;
        awardType = r.nextInt(2);
//      定义一个出蜜蜂 随机向左或者向右飞行的算法
        Random random = new Random();
        int temp = random.nextInt(2);
        if (temp == 0) {
            xspeed = Math.abs(xspeed);
            temp = 3;
        } else if (temp == 1) {
            xspeed = 0 - Math.abs(xspeed);
            temp = 3;
        }
    }

    @Override
    public int getAwardType() {
        return awardType;
    }

    @Override
    public void step() {


        if (x <= 0) {
            xspeed = Math.abs(xspeed);
        }
        if (x >= ShootGame.WIDTH - width) {
            xspeed = -Math.abs(xspeed);
        }
        y = y + yspeed;
        x = x + xspeed;


    }

    @Override
    public boolean outOfBorder() {
        if (y >= ShootGame.HEIGHT) {
//            System.out.println("小蜜蜂越界");
            return true;
        } else
            return false;

    }
}

