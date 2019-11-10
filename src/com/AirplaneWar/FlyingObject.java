package com.AirplaneWar;

import java.awt.image.BufferedImage;

public abstract class FlyingObject {
    protected BufferedImage image;    //图像

    protected int x;  //x坐标
    protected int y;  //y坐标

    protected int width;  //图片宽
    protected int height; //图片高

    public abstract void step();
    //飞行物的飞行方法

    public abstract boolean outOfBorder();
    //判断是否越界的方法
}

