package com.AirplaneWar;

public class Bullet extends FlyingObject {
    private int speed = 3;      //子弹的速度

    public Bullet(int x, int y) {
        image = ShootGame.bullet;
        image = ShootGame.bullet;
        width = image.getWidth();
        height = image.getHeight();
        this.x = x;
        this.y = y;
    }

    @Override
    public void step() {
        y = y - speed;
    }

    @Override
    public boolean outOfBorder() {
        if (y <= -14) {
//            System.out.println("子弹越界");
            return true;
        } else
            return false;

    }

    public boolean hit(FlyingObject flyingObject) {//传入敌方单位坐标
        if (flyingObject.x <= x && x <= (flyingObject.x + flyingObject.width) && flyingObject.y <= y && y <= (flyingObject.y + flyingObject.height)) {
            return true;
        }else{
            return false;
        }
    }
}

