package com.AirplaneWar;

public class Heroplane extends FlyingObject {
    private int speed;      //英雄机的速度
    private int life = 1;       //生命值
    private int doubleFire = 1;     //
    private int wk = 0;
    private int mouseX;
    private int mouseY;

    public Heroplane(int x, int y) {
        this.x = x;
        this.y = y;
        image = ShootGame.hero0;
        width = image.getWidth();
        height = image.getHeight();
    }

    @Override
    public void step() {
        wk++;
        if ((wk / 100) % 2 == 0) {
            image = ShootGame.hero1;
        } else {
            image = ShootGame.hero0;
        }
    }

    @Override
    public boolean outOfBorder() {
        mouseX = x + (image.getWidth() / 2);
        mouseY = y + (image.getHeight() / 2);
        if (mouseX <= 0 || mouseY <= 0 || mouseX >= ShootGame.WIDTH || mouseY >= ShootGame.HEIGHT) {
//            System.out.println("英雄机越界！");
            return true;
        } else
            return false;
    }

    public int getDoubleFire() {
        return doubleFire;
    }

    public void setDoubleFire(int doubleFire) {
        if (doubleFire < 8) {
            this.doubleFire = doubleFire;
        }
    }

    public void moveTo(int x, int y) {
        this.x = x - (image.getWidth() / 2);
        this.y = y - (image.getHeight() / 2);
    }

    public boolean hitEnemy(FlyingObject flyingObject) {
        if (this.x <= flyingObject.x + flyingObject.width && this.x + this.width >= flyingObject.x && this.y <= flyingObject.y + flyingObject.height && this.y + this.height >= flyingObject.y)
            return true;
        else
            return false;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
