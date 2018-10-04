package cpt_arcade;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Defence extends JPanel implements KeyListener {

    public static int gameTimer = 0, level = 0, bulletSpeed = 1, nexusHealth = 10, gamePoint = 0;
    public static String shootingBoxD = "up";
    public static boolean bulletRight = false, bulletLeft = false, bulletUp = false, bulletDown = false, Shoot = false, gameOver = false;
    static ArrayList<DefenceEnemy> enemy = new ArrayList<DefenceEnemy>();
    static ArrayList<DefenceBullet> bullet = new ArrayList<DefenceBullet>();

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        gameTimer += 1;
        //BackGround color
        g.setColor(Color.black);
        g.fillRect(0, 0, 700, 700);
        //Enemy spawn base
        g.setColor(new Color(0, 51, 0));
        g.fillRect(300, 0, 100, 25);
        g.fillRect(300, 635, 100, 25);
        g.fillRect(0, 300, 25, 100);
        g.fillRect(660, 300, 25, 100);
        //Enemy Path
        g.setColor(new Color(139, 69, 19));
        g.fillRect(330, 25, 40, 610);
        g.fillRect(25, 330, 635, 40);
        //Defence Tower
        g.setColor(Color.white);
        g.fillRect(325, 325, 50, 50);
        //enemy spawn
        if (gameTimer % 50 == 0) {
            enemyDirection();
        }
        for (DefenceEnemy num : enemy) {
            if (num.getEnemyLife() == 1) {
                g.setColor(Color.red);
            } else if (num.getEnemyLife() == 2) {
                g.setColor(Color.blue);
            } else if (num.getEnemyLife() == 3) {
                g.setColor(Color.yellow);
            } else if (num.getEnemyLife() == 4) {
                g.setColor(Color.green);
            } else if (num.getEnemyLife() == 5) {
                g.setColor(Color.white);
            }
            g.fillOval(num.getEnemyX(), num.getEnemyY(), 20, 20);
            if (num.getDirection().equals("up")) {
                num.setEnemyY(num.getEnemyY() + num.getEnemySpeed());
            } else if (num.getDirection().equals("down")) {
                num.setEnemyY(num.getEnemyY() - num.getEnemySpeed());
            } else if (num.getDirection().equals("right")) {
                num.setEnemyX(num.getEnemyX() - num.getEnemySpeed());
            } else if (num.getDirection().equals("left")) {
                num.setEnemyX(num.getEnemyX() + num.getEnemySpeed());
            }
        }
        //bullet spawn
        if (Shoot && bulletSpeed == 1) {
            bulletDirection();
            bulletSpeed = 2;
        } else if (bulletSpeed > 1) {
            bulletSpeed += 1;
            if (bulletSpeed == 10) {
                bulletSpeed = 1;
            }
        }
        for (DefenceBullet num : bullet) {
            g.setColor(Color.white);
            g.fillOval(num.getBulletX(), num.getBulletY(), 10, 10);
            if (num.getDirection().equals("up")) {
                num.setBulletY(num.getBulletY() - 5);
            } else if (num.getDirection().equals("down")) {
                num.setBulletY(num.getBulletY() + 5);
            } else if (num.getDirection().equals("right")) {
                num.setBulletX(num.getBulletX() + 5);
            } else if (num.getDirection().equals("left")) {
                num.setBulletX(num.getBulletX() - 5);
            }
        }
        //shooting box
        g.setColor(Color.white);
        if (shootingBoxD.equals("up")) {
            g.fillRect(345, 315, 10, 10);
        } else if (shootingBoxD.equals("down")) {
            g.fillRect(345, 375, 10, 10);
        } else if (shootingBoxD.equals("right")) {
            g.fillRect(375, 345, 10, 10);
        } else if (shootingBoxD.equals("left")) {
            g.fillRect(315, 345, 10, 10);
        }
        // remove bullet if it goes out of bounce
        for (int i = 0; i < bullet.size(); i++) {
            if (bullet.get(i).getBulletX() < 25 || bullet.get(i).getBulletX() > 655 || bullet.get(i).getBulletY() < 25 || bullet.get(i).getBulletY() > 630) {
                bullet.remove(i);
            }
        }
        // remove enemy when hit box
        for (int i = 0; i < enemy.size(); i++) {
            if (enemy.get(i).getEnemyX() > 305 && enemy.get(i).getEnemyX() < 375 && enemy.get(i).getEnemyY() > 305 && enemy.get(i).getEnemyY() < 375) {
                enemy.remove(i);
                nexusHealth -= enemy.get(i).getEnemyLife();
            }
        }

        //increase difficulty
        if (gameTimer % 500 == 0 && level < 5) {
            level += 1;
        }

        //label nexus health
        for (int i = 0; i < nexusHealth; i++) {
            g.setColor(Color.red);
            g.fillRect(50 + (i * 21), 600, 20, 20);
        }

        //game over once health = 0
        if (nexusHealth <= 0) {
            gameOver = true;
        }
        enemyBulletCollision();
    }

    public static void GameOver() {
        gameTimer = 0;
        level = 0;
        bulletSpeed = 1;
        nexusHealth = 10;
        gamePoint = 0;
        shootingBoxD = "up";
        bulletRight = false;
        bulletLeft = false;
        bulletUp = false;
        bulletDown = false;
        Shoot = false;
        enemy = new ArrayList<DefenceEnemy>();
        bullet = new ArrayList<DefenceBullet>();
    }

    public static void enemyBulletCollision() {
        for (int i = 0; i < enemy.size(); i++) {
            for (int z = 0; z < bullet.size(); z++) {
                if (enemy.get(i).getDirection().equals(bullet.get(z).getDirection())) { 
                    if (enemy.get(i).getDirection().equals("up")) {
                        if (bullet.get(z).getBulletY() < (enemy.get(i).getEnemyY() + 20)) {
                            bullet.remove(z);
                            enemy.get(i).setEnemyLife(enemy.get(i).getEnemyLife() - 1);
                            gamePoint += 1;
                        }
                    } else if (enemy.get(i).getDirection().equals("down")) {
                        if ((bullet.get(z).getBulletY() + 10) > (enemy.get(i).getEnemyY())) {
                            bullet.remove(z);
                            enemy.get(i).setEnemyLife(enemy.get(i).getEnemyLife() - 1);
                            gamePoint += 1;
                        }
                    } else if (enemy.get(i).getDirection().equals("right")) {
                        if ((bullet.get(z).getBulletX() + 10) > (enemy.get(i).getEnemyX())) {
                            bullet.remove(z);
                            enemy.get(i).setEnemyLife(enemy.get(i).getEnemyLife() - 1);
                            gamePoint += 1;
                        }
                    } else if (enemy.get(i).getDirection().equals("left")) {
                        if (bullet.get(z).getBulletX() < (enemy.get(i).getEnemyX() + 20)) {
                            bullet.remove(z);
                            enemy.get(i).setEnemyLife(enemy.get(i).getEnemyLife() - 1);
                            gamePoint += 1;
                        }
                    }
                }
                if (enemy.get(i).getEnemyLife() == 0) {
                    enemy.remove(i);
                }
            }
        }
    }

    public static void bulletDirection() {
        String direction = "";
        int bulletX = 0, bulletY = 0;
        if (shootingBoxD.equals("right")) {
            direction = "right";
            bulletX = 375;
            bulletY = 345;
        } else if (shootingBoxD.equals("left")) {
            direction = "left";
            bulletX = 315;
            bulletY = 345;
        } else if (shootingBoxD.equals("up")) {
            direction = "up";
            bulletX = 345;
            bulletY = 315;
        } else if (shootingBoxD.equals("down")) {
            direction = "down";
            bulletX = 345;
            bulletY = 375;
        }
        bullet.add(new DefenceBullet(direction, bulletX, bulletY));
    }

    public static void enemyDirection() {
        int randomNum = (int) (Math.random() * 4 + 1);
        int enemyX = 0, enemyY = 0, enemySpeed = 0, enemyLife = 0;
        String direction;
        if (randomNum == 4) {
            direction = "up";
            enemyX = 340;
            enemyY = -300;
        } else if (randomNum == 3) {
            direction = "down";
            enemyX = 340;
            enemyY = 940;
        } else if (randomNum == 2) {
            direction = "left";
            enemyX = -300;
            enemyY = 340;
        } else {
            direction = "right";
            enemyX = 965;
            enemyY = 340;
        }
        enemySpeed = 1 + (int) (Math.random() * level + 0);
        enemyLife = 1 + (int) (Math.random() * level + 0);
        enemy.add(new DefenceEnemy(direction, enemyX, enemyY, enemySpeed, enemyLife));
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            bulletRight = true;
            shootingBoxD = "right";
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            bulletLeft = true;
            shootingBoxD = "left";
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bulletUp = true;
            shootingBoxD = "up";
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            bulletDown = true;
            shootingBoxD = "down";
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Shoot = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            bulletRight = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            bulletLeft = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            bulletUp = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            bulletDown = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            Shoot = false;
        }
    }
}
