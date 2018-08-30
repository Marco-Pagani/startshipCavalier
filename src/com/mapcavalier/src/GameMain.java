package com.mapcavalier.src;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameMain extends JApplet {

    public final static int GAME_WIDTH = 1900;
    public final static int GAME_HEIGHT = 950;
    Starship player;
    Background bg1, bg2;

    public GameMain() {
        add(new GameMain.StagePanel());
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Starship Cavalier Beta 1.7");
        GameMain myApplet = new GameMain();
        frame.add(myApplet);
        myApplet.init();
        myApplet.start();

        frame.setSize(GAME_WIDTH, GAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);




    }

    private class StagePanel extends JPanel {

        //Create all variables
        EnemyShip[] enemies = new EnemyShip[10];
        Laser[] enLasers = new Laser[10];
        EntitySprite[] explosions = new EntitySprite[10];
        boolean[] fired = new boolean[10];
        Starship player;
        boolean left, right;
        Background bg1, bg2, overlay;
        EntitySprite gui;
        Laser pL1;
        Font font, lose;
        Timer myTimer, enTimer;
        int diff;
        AnimationSprite ani;
        int eX;
        int eY = -200;
        AudioClip BackgroundMusic;

        public StagePanel() {

            //Instantiate all assets
            player = new Starship(400, 710, this.getClass().getResource("images/playerShip.png"));
            bg1 = new Background(0, 0, this.getClass().getResource("images/bg.png"));
            bg2 = new Background(0, GAME_HEIGHT, this.getClass().getResource("images/bg.png"));
            gui = new EntitySprite(0, 810, this.getClass().getResource("images/gui.png"));
            pL1 = new Laser(1000, 0, this.getClass().getResource("images/bullet.png"));
            font = new Font("OCR A Extended", Font.BOLD, 23);
            lose = new Font("Minecraft", Font.BOLD, 55);
            ani = new AnimationSprite(1, 2);
            overlay = new Background(0, 0, this.getClass().getResource("images/damage/" + player.hp + ".png"));
            BackgroundMusic = Applet.newAudioClip(this.getClass().getResource("images/BackgroundMusic.wav"));

            for (int i = 0; i < enemies.length; i++) {
                enemies[i] = new EnemyShip(0, -150, this.getClass().getResource("images/enemyShip.png"));

            }

            for (int i = 0; i < enLasers.length; i++) {
                enLasers[i] = new Laser(0, -150, this.getClass().getResource("images/enBolt.png"));
            }

            for (int i = 0; i < explosions.length; i++) {
                explosions[i] = new EntitySprite(eX, eY, this.getClass().getResource("images/explosion/" + (i + 1) + ".png"));
            }


            this.setFocusable(true);
            addKeyListener(new MyKeyListener());
            myTimer = new Timer(33, new MyTimerListener());
            enTimer = new Timer(10000, new enTimerListener());

            myTimer.start();
                   BackgroundMusic.loop();
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(font);
            bg1.draw(g);
            bg2.draw(g);
            pL1.draw(g);
            player.draw(g);

            for (int i = 0; i < diff; i++) {
                enemies[i].draw(g);
            }

            for (int i = 0; i < enLasers.length; i++) {
                enLasers[i].draw(g);
            }


            explosions[ani.currentFrame].draw(g);


            gui.draw(g);
            g.drawString("" + player.score, 100, 855);
            g.setColor(Color.GREEN);
            g.fillRect(120, 880, (50 * player.hp), 15);
            overlay.draw(g);
            g.setFont(lose);
            g.setColor(Color.CYAN);
            if (player.hp == 0) {
                g.drawString("Score: " + player.score, 270, 600);
            }


        }

        protected class MyTimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {

                

                for (int i = 0; i < explosions.length; i++) {
                    explosions[i] = new EntitySprite(eX, eY, this.getClass().getResource("images/explosion/" + (i + 1) + ".png"));
                }

                for (int i = 0; i < diff; i++) {
                    if (enemies[i].y < -112) {
                        enemies[i].x = (int) (Math.random() * 800 + 1);

                        enemies[i].y = (int) (Math.random() * -150 + 1);
                    }
                    if (enemies[i].y > 25 && !fired[i]) {
                        enemies[i].fire(enLasers[i], enemies[i]);
                        fired[i] = true;

                    }
                    if (enemies[i].y < 100) {
                        enemies[i].moveY(10);
                    }

                    if (fired[i] == true) {
                        enLasers[i].moveY(20);
                    }
                    if (enLasers[i].y > (Math.random() * 960 + 900) || player.hitTest(enLasers[i].x + 2, enLasers[i].y + 8)) {
                        fired[i] = false;
                    }
                }

                for (int i = 0; i < enemies.length; i++) {
                    if (enemies[i].hitTest(pL1.x + 2, pL1.y)) {
                        eX = enemies[i].x + 10;
                        eY = enemies[i].y + 8;
                        enemies[i].x = (int) (Math.random() * 800 + 1);
                        enemies[i].y = (int) (Math.random() * -150 + 1);
                        pL1.y = -200;
                        ani.currentFrame = 1;
                        if (player.hp > 0) {
                            player.score++;
                        }
                        
                    }

                }

                for (int i = 0; i < enLasers.length; i++) {
                    if (player.hitTest(enLasers[i].x + 2, enLasers[i].y + 8) && player.hp > 0) {
                        player.hp--;
                    }
                }
                ani.animate(ani.currentFrame, explosions.length);


                if (ani.currentFrame == 9) {
                    eY = -200;
                }
                repaint();

                overlay = new Background(0, 0, this.getClass().getResource("images/damage/" + player.hp + ".png"));
                bg1.moveY(10);
                bg2.moveY(10);
                bg1.recycle(bg1, bg2);

                if (left == true && player.x >= 6) {
                    player.moveX(-10);
                }
                if (right == true && player.x <= 900 - player.width - 6) {
                    player.moveX(10);
                }

                pL1.moveY(-30);

                if (player.score > 50) {
                    diff = 10;
                } else if (player.score > 45) {
                    diff = 9;
                } else if (player.score > 40) {
                    diff = 8;
                } else if (player.score > 35) {
                    diff = 7;
                } else if (player.score > 30) {
                    diff = 6;
                } else if (player.score > 25) {
                    diff = 5;
                } else if (player.score > 20) {
                    diff = 4;
                } else if (player.score > 15) {
                    diff = 3;
                } else if (player.score > 10) {
                    diff = 2;
                } else if (player.score > 5) {
                    diff = 2;
                } else {
                    diff = 1;
                }

            }
        }

        protected class enTimerListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
            }
        }

        protected class MyKeyListener implements KeyListener {

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    left = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    right = true;
                }
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (pL1.y <= -5) {
                        pL1.shoot(player, pL1);
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    left = false;
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    right = false;
                }
                if (e.getKeyCode () == KeyEvent.VK_DOWN) {
                    if (player.hp < 5) {
                        player.hp++;
                    }
                }
            }
        }
    }
}
