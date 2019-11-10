package com.AirplaneWar;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Timer;

public class ShootGame extends JPanel {
    public static BufferedImage hero0;
    public static BufferedImage hero1;
    public static BufferedImage bee;
    public static BufferedImage airplane;
    public static BufferedImage pause;
    public static BufferedImage start;
    public static BufferedImage gameover;
    public static BufferedImage bullet;
    public static BufferedImage background;
    public static BufferedImage treasure;
    public static BufferedImage BigBoss;


    public static int score;
    public static final int START = 0;
    public static final int RUNNING = 1;
    public static final int PAUSE = 2;
    public static final int GAMEOVER = 3;
    public static int state = START;


    static {

        try {
            hero0 = ImageIO.read(ShootGame.class.getResource("/images/hero0.png"));
            hero1 = ImageIO.read(ShootGame.class.getResource("/images/hero1.png"));
            bee = ImageIO.read(ShootGame.class.getResource("/images/bee.png"));
            airplane = ImageIO.read(ShootGame.class.getResource("/images/airplane.png"));
            pause = ImageIO.read(ShootGame.class.getResource("/images/pause.png"));
            start = ImageIO.read(ShootGame.class.getResource("/images/start.png"));
            gameover = ImageIO.read(ShootGame.class.getResource("/images/gameover.png"));
            bullet = ImageIO.read(ShootGame.class.getResource("/images/bullet.png"));
            background = ImageIO.read(ShootGame.class.getResource("/images/background.png"));
            treasure = ImageIO.read(ShootGame.class.getResource("/images/treasure.png"));
            BigBoss = ImageIO.read(ShootGame.class.getResource("/images/BigBoss.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final int WIDTH = start.getWidth();
    public static final int HEIGHT = start.getHeight();
    private Heroplane heroplane = new Heroplane(start.getWidth() / 2, start.getHeight());
    //        private FlyingObject[] flyings = {};
    private LinkedList flyings = new LinkedList();
    //        private Bullet[] bullets = {};
    private LinkedList bullets = new LinkedList();


    public void paint(Graphics g) {
        g.drawImage(background, 0, 0, null);
        g.drawImage(heroplane.image, heroplane.x, heroplane.y, null);
//        for (int i = 0; i < flyings.length; i++) {
//            g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
//        }
        for (Iterator iter = flyings.iterator(); iter.hasNext(); ) {
            FlyingObject f = (FlyingObject) iter.next();
            g.drawImage(f.image, f.x, f.y, null);
        }
//        for (int i = 0; i < bullets.length; i++) {
//            g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
//        }
        for (Iterator iter = bullets.iterator(); iter.hasNext(); ) {
            FlyingObject f = (FlyingObject) iter.next();
            g.drawImage(f.image, f.x, f.y, null);
        }
        //画火力画生命
        g.drawString("得分：" + score + "  " + "生命值：" + heroplane.getLife() + "  " + "火力：" + heroplane.getDoubleFire(), 10, 20);
        switch (state) {
            case START:
                g.drawImage(start, 0, 0, null);
                break;
//            case RUNNING:
//                break;
            case PAUSE:
                g.drawImage(pause, 0, 0, null);
                break;
            case GAMEOVER:
                g.drawImage(gameover, 0, 0, null);
                break;
            default:
                break;
        }

    }//显示

//    int refresh = 10;//调整整体刷新率（1~10）,而英雄机的刷新率要在英雄机类中改变

    int wc = 0;//调整敌机或蜜蜂的生成速率
//    int cc = 150; //控制敌机生成速率

    public void enermyMaker() {
        wc++;
//        if (cc >= 10)
//            cc--;
//        if (wc % (100 / refresh) == 0) {
        if (wc % (30) == 0) {
            FlyingObject f = nextOne();
            flyings.add(f);
//            flyings = Arrays.copyOf(flyings, flyings.length + 1);
//            flyings[flyings.length - 1] = f;
//            System.out.println(flyings.length);
        }
    }//生成敌机

    public FlyingObject nextOne() {
        Random random = new Random();
        int type = random.nextInt(100);
        if (0 <= type && type < 5) {
            return new BigEnemyPlane();
        } else if (5 <= type && type < 7) {
            return new Treasure();
        } else if (7 <= type && type < 10) {
            return new Bee();
        } else {
            return new EnemyPlane();
        }
    }//生成下一个敌机单位

    int wk = 0;//调整每次生成子弹的速率

    public void bulletMaker() {
        wk++;
//        if (wk % (300 / refresh) == 0) {
        if (wk % (30) == 0) {
            Bullet[] bs = nextBullet();
//            bullets = Arrays.copyOf(bullets, bullets.length + bs.length);
//            for (int i = 0; i < bs.length; i++) {
//                bullets[bullets.length - bs.length + i] = bs[i];
//            }
            for (int i = 0; i < bs.length; i++)
                bullets.add(bs[i]);

        }
    }//生成子弹

    public Bullet[] nextBullet() {
        int n = heroplane.getDoubleFire();//几条子弹
//        System.out.println(n);

        Bullet[] bs = new Bullet[n];
        for (int i = 0; i < n; i++) {
            bs[i] = new Bullet(heroplane.x - 3 + (i + 1) * (heroplane.width / (1 + n)), heroplane.y);
        }
        return bs;
    }//生成下一个子弹

    int ww = 0;//调整每次运动的刷新率

    public void stepAction() {
        heroplane.step();
        ww++;
        if (heroplane.outOfBorder()) {
            //Pause
        }
//        if (ww % (5000 / refresh) == 0) {
        if (ww % (500) == 0) {
            if (heroplane.getDoubleFire() > 1)
                heroplane.setDoubleFire(heroplane.getDoubleFire() - 1);
        }
//        if (ww % (10 / refresh) == 0) {
        if (ww % (1) == 0) {
//            FlyingObject[] f = new FlyingObject[flyings.length];
//            int fcont = 0;
//            Bullet[] b = new Bullet[bullets.length];
//            int bcont = 0;
//            for (int i = 0; i < flyings.size(); i++) {
//                flyings[i].step();
//                if (!flyings[i].outOfBorder()) {
//                    //如果没有越界就复制
//                    f[fcont] = flyings[i];
//                    fcont++;
//                }
            for (Iterator iter = flyings.iterator(); iter.hasNext(); ) {
                FlyingObject f = (FlyingObject) iter.next();
                f.step();
                if (f.outOfBorder())
                    iter.remove();

            }
//            for (int i = 0; i < bullets.length; i++) {
//                bullets[i].step();
//                if (!bullets[i].outOfBorder()) {
//                    //如果没有越界就复制
//                    b[bcont] = bullets[i];
//                    bcont++;
//                }
//            }
//            flyings = Arrays.copyOf(f, fcont);
//            bullets = Arrays.copyOf(b, bcont);
            for (Iterator iter = bullets.iterator(); iter.hasNext(); ) {
                Bullet f = (Bullet) iter.next();
                f.step();
                if (f.outOfBorder())
                    iter.remove();

            }
        }
    }//所有单位下一步

    public void gameRule() {
        for (Iterator iter1 = bullets.iterator(); iter1.hasNext(); ) {
            Bullet b = (Bullet) iter1.next();
            for (Iterator iter2 = flyings.iterator(); iter2.hasNext(); ) {
//                Bullet b = (Bullet) iter1.next();
                FlyingObject f = (FlyingObject) iter2.next();
                if (b.hit(f)) {
                    //撞击成功
                    if (f instanceof EnemyPlane) {
                        //撞上了敌机
                        EnemyPlane enemyPlane = (EnemyPlane) f;
                        score += enemyPlane.getScore();//返回得分
                    }
                    if (f instanceof BigEnemyPlane) {
                        //撞上了敌机
                        BigEnemyPlane bigenemyPlane = (BigEnemyPlane) f;
                        score += bigenemyPlane.getScore();//返回得分
                    }
                    if (f instanceof Bee) {
                        //撞上了蜜蜂
                        Bee bee = (Bee) f;
                        if (bee.getAwardType() == 1) {//如果是加火力
//                        if(true){
//                            if (heroplane.getDoubleFire() <= 4) {
                            heroplane.setDoubleFire(2 * heroplane.getDoubleFire());//火力*2
//                            }
                        } else if (bee.getAwardType() == 0) {//如果是加生命
                            heroplane.setLife(heroplane.getLife() + 1);
//                            System.out.println(heroplane.getLife());
                        }
                    }
                    if (f instanceof Treasure) {
                        Treasure treasure = (Treasure) f;
                        heroplane.setDoubleFire(4 * heroplane.getDoubleFire());
                        heroplane.setLife(heroplane.getLife() + 2);
                    }
//                    for (int k = i; k < bullets.length - 1; k++) {//子弹清除
//                        bullets[k] = bullets[k + 1];
//                    }
//                    bullets = Arrays.copyOf(bullets, bullets.length - 1);
//                    for (int k = j; k < flyings.length - 1; k++) {//飞行物清楚
//                        flyings[k] = flyings[k + 1];
//                    }
//                    flyings = Arrays.copyOf(flyings, flyings.length - 1);
//                    i--;
//                    j--;
                    iter1.remove();
                    iter2.remove();

                }
            }
        }
        for (Iterator iter = flyings.iterator(); iter.hasNext(); ) {
            FlyingObject f = (FlyingObject) iter.next();
            if (heroplane.hitEnemy(f)) {
                //Hit!
//                System.out.println("Game Over!");
                heroplane.setLife(heroplane.getLife() - 1);
                heroplane.setDoubleFire(1);
//                for (int k = i; k < flyings.length - 1; k++) {//飞行物清楚
//                    flyings[k] = flyings[k + 1];
//                }
//                flyings = Arrays.copyOf(flyings, flyings.length - 1);
//                i--;
                iter.remove();
            }
        }
    }

    public void checkGameOverAction() {
        if (isGameOver()) {
            state = GAMEOVER;
        }
    }

    public boolean isGameOver() {
        if (heroplane.getLife() <= 0)
            return true;
        else
            return false;
    }

    public void action() {
        //这里是鼠标监听事件
        MouseAdapter l = new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
//                super.mouseEntered(e);
                if (state == PAUSE) {
                    state = RUNNING;
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
//                super.mouseExited(e);
                if (state == RUNNING) {
                    state = PAUSE;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
                if (state == START) {
                    state = RUNNING;
                }
                if (state == GAMEOVER) {
                    //清除上一局数据
                    heroplane = new Heroplane(start.getWidth() / 2, start.getHeight());
//                    bullets = new Bullet[0];
                    bullets.clear();
//                    flyings = new FlyingObject[0];
                    flyings.clear();
                    score = 0;
                    state = RUNNING;
                }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
//                super.mouseMoved(e);
                if (state == RUNNING) {
                    int x = e.getX();
                    int y = e.getY();
                    heroplane.moveTo(x, y);
                }
            }
        };

        this.addMouseListener(l);//侦听鼠标点击操作
        this.addMouseMotionListener(l);//侦听鼠标滑动操作
        //这里是定时事件
        Timer time = new Timer();
        time.schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == RUNNING) {
                    enermyMaker();//生产敌人对象并添加进数组
                    bulletMaker();//子弹产生器
                    stepAction();
                    gameRule();
                    checkGameOverAction();
                }
                repaint();
            }
//        }, refresh, refresh);//屏幕的刷新率
        }, 10, 10);//屏幕的刷新率
    }//所有单位的刷新

    public static void main(String[] args) {
        JFrame frame = new JFrame("飞机大战");//创建框架
        ShootGame s = new ShootGame();//创建画板
        frame.add(s);
        frame.setSize(WIDTH, HEIGHT);
        frame.setAlwaysOnTop(true);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        s.action();//调用定时器进行添加敌人or蜜蜂
    }

}


