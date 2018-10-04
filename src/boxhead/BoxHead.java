/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxhead;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author AntonysMac
 */
public class BoxHead implements ActionListener, KeyListener {

    public JFrame jframe;

    public Dimension dim;

    public Toolkit toolkit;

    public static BoxHead boxHead;

    public Timer timer = new Timer(20, this);

    public BoxPanel renderPanel;

    public static final int UP = 0, RIGHT = 1, DOWN = 2, LEFT = 3;

    public ArrayList<Character> enemies = new ArrayList<>();

    public ArrayList<Bullets> bullets = new ArrayList<>();

    public int ticks = 0, playerSpeed = 4, enemySpeed = 1, size = 20, bulletSpeed = 10, assaultRifleTime = 2, pistolTime = 5, shotgunTime = 5, reloadTimer = pistolTime, reload = reloadTimer, spawnTimer = 150, spawn = spawnTimer, acceleration = 1, personalSpace = 5, knockBack = 10, enemyHealth = 6, characterHealth = 30, maxEnemies = 300, gamePoint = 0;

    public double yOverXTurning = 1;

    public boolean gameOver = false, instructions = true;

    public ArrayList<Character> players = new ArrayList<>();

    public BoxHead() {
        jframe = new JFrame("Box-Head");
        jframe.setVisible(true);
        jframe.setResizable(false);
        dim = toolkit.getDefaultToolkit().getScreenSize();
        jframe.setSize(dim.width, dim.height - 100);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.addKeyListener(this);
        jframe.add(renderPanel = new BoxPanel());
        players.add(new Character(jframe.getWidth() / 2, jframe.getHeight() / 2, size, playerSpeed, characterHealth, 0, pistolTime));
        timer.start();

    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        renderPanel.repaint();

        spawn--;
        ticks++;
        if (!gameOver) {
            if (ticks == 500) {
                instructions = false;
            }
            for (int f = 0; f < players.size(); f++) {
                players.get(f).setReload(players.get(f).getReload() - 1);
                if (players.get(f).isUp()) {
                    players.get(f).setY(players.get(f).getY() - players.get(f).getSpeed());
                }
                if (players.get(f).isDown()) {
                    players.get(f).setY(players.get(f).getY() + players.get(f).getSpeed());
                }
                if (players.get(f).isRight()) {
                    players.get(f).setX(players.get(f).getX() + players.get(f).getSpeed());
                }
                if (players.get(f).isLeft()) {
                    players.get(f).setX(players.get(f).getX() - players.get(f).getSpeed());
                }
                if (players.get(f).getX() - players.get(f).getSize() / 2 < 0) {
                    players.get(f).setX(players.get(f).getX() + players.get(f).getSpeed());
                }
                if (players.get(f).getX() + players.get(f).getSize() / 2 > jframe.getWidth()) {
                    players.get(f).setX(players.get(f).getX() - players.get(f).getSpeed());
                }
                if (players.get(f).getY() - players.get(f).getSize() / 2 < 0) {
                    players.get(f).setY(players.get(f).getY() + players.get(f).getSpeed());
                }
                if (players.get(f).getY() + players.get(f).getSize() / 2 > jframe.getHeight() - 20) {
                    players.get(f).setY(players.get(f).getY() - players.get(f).getSpeed());
                }
                if (spawn <= 0 && enemies.size() < maxEnemies) {
                    gamePoint += 1;
                    if (spawnTimer > 20) {
                        spawnTimer -= 10;
                    }
                    if (players.get(0).getHealth() < characterHealth) {
                        players.get(0).setHealth(players.get(0).getHealth() + 1);
                    }
                    if (players.size() == 2 && players.get(1).getHealth() < characterHealth) {
                        players.get(1).setHealth(players.get(1).getHealth() + 1);
                    }
                    spawn = spawnTimer;
                    int spawnLocation = (int) (Math.random() * 4);
                    if (spawnLocation == 0) {
                        enemies.add(new Character((int) (Math.random() * jframe.getWidth()), 0, size, enemySpeed, enemyHealth, (int) (Math.random() * players.size()), pistolTime));
                    } else if (spawnLocation == 1) {
                        enemies.add(new Character(jframe.getWidth(), (int) (Math.random() * jframe.getHeight()), size, enemySpeed, enemyHealth, (int) (Math.random() * players.size()), pistolTime));
                    } else if (spawnLocation == 2) {
                        enemies.add(new Character((int) (Math.random() * jframe.getWidth()), jframe.getHeight(), size, enemySpeed, enemyHealth, (int) (Math.random() * players.size()), pistolTime));
                    } else if (spawnLocation == 3) {
                        enemies.add(new Character(0, (int) (Math.random() * jframe.getHeight()), size, enemySpeed, enemyHealth, (int) (Math.random() * players.size()), pistolTime));
                    }
                }
                for (int i = 0; i < enemies.size(); i++) {
                    if (players.size() == 1) {
                        enemies.get(i).setPlayerToChase(0);
                    }
                    if (enemies.get(i).getPlayerToChase() == 0) {
                        chasing(0, i);
                    }

                    if (enemies.get(i).getPlayerToChase() == 1) {
                        chasing(1, i);
                    }
                    if (enemies.get(i).isUp()) {
                        enemies.get(i).setY(enemies.get(i).getY() - enemies.get(i).getSpeed());
                    }
                    if (enemies.get(i).isDown()) {
                        enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getSpeed());
                    }
                    if (enemies.get(i).isRight()) {
                        enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getSpeed());
                    }
                    if (enemies.get(i).isLeft()) {
                        enemies.get(i).setX(enemies.get(i).getX() - enemies.get(i).getSpeed());
                    }

                    for (int z = 0; z < bullets.size(); z++) {
                        if (enemies.get(i).isCollided(bullets.get(z).getX(), bullets.get(z).getY(), bullets.get(z).getX() + bullets.get(z).getSize(), bullets.get(z).getY() + bullets.get(z).getSize())) {
                            enemies.get(i).setSpeed(-knockBack);
                            bullets.remove(z);
                            if (players.get(f).isPistol()) {
                                enemies.get(i).setHealth(enemies.get(i).getHealth() - 6);
                            } else if (players.get(f).isAssaultRifle() || players.get(f).isgGun()) {
                                enemies.get(i).setHealth(enemies.get(i).getHealth() - 1);
                            } else if (players.get(f).isShotgun()) {
                                enemies.get(i).setHealth(enemies.get(i).getHealth() - 2);
                            }
                        }
                    }

                    if (enemies.get(i).getSpeed() < enemySpeed) {
                        enemies.get(i).setSpeed(enemies.get(i).getSpeed() + acceleration);
                    }
                    if (enemies.get(i).getSpeed() > enemySpeed) {
                        enemies.get(i).setSpeed(enemies.get(i).getSpeed() - 1);
                    }
                    for (int z = 0; z < enemies.size(); z++) {

                        if (z != i) {
                            boolean collisionWithOtherEnemies = enemies.get(i).isCollided(enemies.get(z).getX() - enemies.get(z).getSize() / 2 - personalSpace, enemies.get(z).getY() - enemies.get(z).getSize() / 2 - personalSpace, enemies.get(z).getX() + enemies.get(z).getSize() / 2 + personalSpace, enemies.get(z).getY() + enemies.get(z).getSize() / 2 + personalSpace);
                            boolean collisionWithPlayer = enemies.get(i).isCollided(players.get(f).getX() - players.get(f).getSize() / 2, players.get(f).getY() - players.get(f).getSize() / 2, players.get(f).getX() + players.get(f).getSize() / 2, players.get(f).getY() + players.get(f).getSize() / 2);
                            if (enemies.get(i).getX() > enemies.get(z).getX()) {
                                if (collisionWithOtherEnemies) {
                                    enemies.get(i).setX(enemies.get(i).getX() + enemies.get(i).getSpeed());
                                }
                            }
                            if (enemies.get(i).getX() < enemies.get(z).getX()) {
                                if (collisionWithOtherEnemies) {
                                    enemies.get(i).setX(enemies.get(i).getX() - enemies.get(i).getSpeed());
                                }
                            }
                            if (enemies.get(i).getY() > enemies.get(z).getY()) {
                                if (collisionWithOtherEnemies) {
                                    enemies.get(i).setY(enemies.get(i).getY() + enemies.get(i).getSpeed());
                                }
                            }
                            if (enemies.get(i).getY() < enemies.get(z).getY()) {
                                if (collisionWithPlayer) {
                                    enemies.get(i).setY(enemies.get(i).getY() - enemies.get(i).getSpeed());
                                }
                            }
                            if (enemies.get(i).getX() > players.get(f).getX()) {
                                if (collisionWithPlayer) {
                                    enemies.get(i).setSpeed(-knockBack);
                                    players.get(f).setHealth(players.get(f).getHealth() - 1);
                                }
                            }
                            if (enemies.get(i).getX() < players.get(f).getX()) {
                                if (collisionWithPlayer) {
                                    enemies.get(i).setSpeed(-knockBack);
                                    players.get(f).setHealth(players.get(f).getHealth() - 1);
                                }
                            }
                            if (enemies.get(i).getY() > players.get(f).getY()) {
                                if (collisionWithPlayer) {
                                    enemies.get(i).setSpeed(-knockBack);
                                    players.get(f).setHealth(players.get(f).getHealth() - 1);
                                }
                            }
                            if (enemies.get(i).getY() < players.get(f).getY()) {
                                if (collisionWithPlayer) {
                                    enemies.get(i).setSpeed(-knockBack);
                                    players.get(f).setHealth(players.get(f).getHealth() - 1);
                                }
                            }
                        }
                    }
                    if (enemies.get(i).getHealth() <= 0) {
                        enemies.remove(i);
                        gamePoint += 10;
                    }

                }
                if (players.get(f).isShot() && players.get(f).getReload() <= 0) {
                    players.get(f).setReload(players.get(f).getReloadTimer());
                    if (!players.get(f).isAssaultRifle() && !players.get(f).isgGun()) {
                        players.get(f).setShot(false);
                    }
                    if (!players.get(f).isgGun()) {
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        if (players.get(f).isUp()) {
                            bullets.get(bullets.size() - 1).setUp(true);
                            if (players.get(f).isShotgun() && !players.get(f).isDown() && !players.get(f).isRight() && !players.get(f).isLeft()) {
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.get(bullets.size() - 1).setUp(true);
                                bullets.get(bullets.size() - 1).setRight(true);
                                bullets.get(bullets.size() - 2).setUp(true);
                                bullets.get(bullets.size() - 2).setLeft(true);
                            }
                        }
                        if (players.get(f).isDown()) {
                            bullets.get(bullets.size() - 1).setDown(true);
                            if (players.get(f).isShotgun() && !players.get(f).isUp() && !players.get(f).isRight() && !players.get(f).isLeft()) {
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.get(bullets.size() - 1).setDown(true);
                                bullets.get(bullets.size() - 1).setRight(true);
                                bullets.get(bullets.size() - 2).setDown(true);
                                bullets.get(bullets.size() - 2).setLeft(true);
                            }
                        }
                        if (players.get(f).isRight()) {
                            bullets.get(bullets.size() - 1).setRight(true);
                            if (players.get(f).isShotgun() && !players.get(f).isUp() && !players.get(f).isDown() && !players.get(f).isLeft()) {
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.get(bullets.size() - 1).setRight(true);
                                bullets.get(bullets.size() - 1).setUp(true);
                                bullets.get(bullets.size() - 2).setRight(true);
                                bullets.get(bullets.size() - 2).setDown(true);
                            }
                        }
                        if (players.get(f).isLeft()) {
                            bullets.get(bullets.size() - 1).setLeft(true);
                            if (players.get(f).isShotgun() && !players.get(f).isUp() && !players.get(f).isRight() && !players.get(f).isDown()) {
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                bullets.get(bullets.size() - 1).setLeft(true);
                                bullets.get(bullets.size() - 1).setUp(true);
                                bullets.get(bullets.size() - 2).setLeft(true);
                                bullets.get(bullets.size() - 2).setDown(true);
                            }
                        }
                        if (!players.get(f).isLeft() && !players.get(f).isRight() && !players.get(f).isDown() && !players.get(f).isUp()) {
                            if (players.get(f).getFacing() == UP) {
                                bullets.get(bullets.size() - 1).setUp(true);
                                if (players.get(f).isShotgun()) {
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.get(bullets.size() - 1).setUp(true);
                                    bullets.get(bullets.size() - 1).setRight(true);
                                    bullets.get(bullets.size() - 2).setUp(true);
                                    bullets.get(bullets.size() - 2).setLeft(true);
                                }
                            } else if (players.get(f).getFacing() == DOWN) {
                                bullets.get(bullets.size() - 1).setDown(true);
                                if (players.get(f).isShotgun()) {
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.get(bullets.size() - 1).setDown(true);
                                    bullets.get(bullets.size() - 1).setRight(true);
                                    bullets.get(bullets.size() - 2).setDown(true);
                                    bullets.get(bullets.size() - 2).setLeft(true);
                                }
                            } else if (players.get(f).getFacing() == LEFT) {
                                bullets.get(bullets.size() - 1).setLeft(true);
                                if (players.get(f).isShotgun()) {
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.get(bullets.size() - 1).setLeft(true);
                                    bullets.get(bullets.size() - 1).setUp(true);
                                    bullets.get(bullets.size() - 2).setLeft(true);
                                    bullets.get(bullets.size() - 2).setDown(true);
                                }

                            } else if (players.get(f).getFacing() == RIGHT) {
                                bullets.get(bullets.size() - 1).setRight(true);
                                if (players.get(f).isShotgun()) {
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                                    bullets.get(bullets.size() - 1).setRight(true);
                                    bullets.get(bullets.size() - 1).setUp(true);
                                    bullets.get(bullets.size() - 2).setRight(true);
                                    bullets.get(bullets.size() - 2).setDown(true);
                                }
                            }
                        }
                    } else {
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.add(new Bullets(players.get(f).getX(), players.get(f).getY()));
                        bullets.get(bullets.size() - 1).setUp(true);
                        bullets.get(bullets.size() - 2).setRight(true);
                        bullets.get(bullets.size() - 2).setUp(true);
                        bullets.get(bullets.size() - 3).setRight(true);
                        bullets.get(bullets.size() - 4).setRight(true);
                        bullets.get(bullets.size() - 4).setDown(true);
                        bullets.get(bullets.size() - 5).setDown(true);
                        bullets.get(bullets.size() - 6).setLeft(true);
                        bullets.get(bullets.size() - 6).setDown(true);
                        bullets.get(bullets.size() - 7).setLeft(true);
                        bullets.get(bullets.size() - 8).setLeft(true);
                        bullets.get(bullets.size() - 8).setUp(true);

                    }

                }
                for (int i = 0; i < bullets.size(); i++) {
                    if (bullets.get(i).isUp()) {
                        bullets.get(i).setY(bullets.get(i).getY() - bulletSpeed);
                    }
                    if (bullets.get(i).isDown()) {
                        bullets.get(i).setY(bullets.get(i).getY() + bulletSpeed);
                    }
                    if (bullets.get(i).isRight()) {
                        bullets.get(i).setX(bullets.get(i).getX() + bulletSpeed);
                    }
                    if (bullets.get(i).isLeft()) {
                        bullets.get(i).setX(bullets.get(i).getX() - bulletSpeed);
                    }
                    if (bullets.get(i).getX() < 0 || bullets.get(i).getX() > jframe.getWidth() || bullets.get(i).getY() < 0 || bullets.get(i).getY() > jframe.getHeight()) {
                        bullets.remove(i);
                    }
                }
                if (players.get(f).getHealth() <= 0) {
                    System.out.println("Score: " + gamePoint);
                    players.remove(f);
                    if (players.size() == 0) {
                        gameOver = true;
                    }
                }

            }
        }
    }

    public void chasing(int player, int i) {
        double ratio;
        if ((players.get(player).getX() - enemies.get(i).getX()) != 0) {
            ratio = (double) (players.get(player).getY() - enemies.get(i).getY()) / (double) (players.get(player).getX() - enemies.get(i).getX());
        } else {
            ratio = 1;
        }
        if (enemies.get(i).getX() < players.get(player).getX()) {
            enemies.get(i).setRight(true);
            enemies.get(i).setLeft(false);
        }
        if (enemies.get(i).getX() > players.get(player).getX()) {
            enemies.get(i).setLeft(true);
            enemies.get(i).setRight(false);
        }
        if (enemies.get(i).getX() == players.get(player).getX()) {
            enemies.get(i).setRight(false);
            enemies.get(i).setLeft(false);
        }
        if (enemies.get(i).getY() < players.get(player).getY()) {
            enemies.get(i).setUp(false);
            if (ratio >= yOverXTurning || ratio <= -yOverXTurning) {
                enemies.get(i).setDown(true);
            } else {
                enemies.get(i).setDown(false);
            }
        }
        if (enemies.get(i).getY() > players.get(player).getY()) {
            enemies.get(i).setDown(false);
            if (ratio >= yOverXTurning || ratio <= -yOverXTurning) {
                enemies.get(i).setUp(true);
            } else {
                enemies.get(i).setUp(false);
            }
        }
        if (enemies.get(i).getY() == players.get(player).getY()) {
            enemies.get(i).setUp(false);
            enemies.get(i).setDown(false);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (players.size() > 0) {
            if (i == KeyEvent.VK_BACK_SLASH && players.size() < 2) {
                players.add(new Character(jframe.getWidth() / 2, jframe.getHeight() / 2, size, playerSpeed, characterHealth, 0, pistolTime));
            }
            if (i == KeyEvent.VK_UP) {
                players.get(0).setUp(true);
            }
            if (i == KeyEvent.VK_LEFT) {
                players.get(0).setLeft(true);
            }
            if (i == KeyEvent.VK_RIGHT) {
                players.get(0).setRight(true);
            }
            if (i == KeyEvent.VK_DOWN) {
                players.get(0).setDown(true);
            }
            if (i == KeyEvent.VK_SPACE) {
                if (players.get(0).isAssaultRifle() || players.get(0).isgGun()) {
                    players.get(0).setShot(!players.get(0).isShot());
                } else {
                    players.get(0).setShot(true);
                }
            }
            if (i == KeyEvent.VK_COMMA) {
                players.get(0).setPistol(true);
                players.get(0).setShotgun(false);
                players.get(0).setAssaultRifle(false);
                players.get(0).setgGun(false);
                players.get(0).setReloadTimer(pistolTime);
            }
            if (i == KeyEvent.VK_PERIOD) {
                players.get(0).setPistol(false);
                players.get(0).setShotgun(true);
                players.get(0).setAssaultRifle(false);
                players.get(0).setgGun(false);
                players.get(0).setReloadTimer(shotgunTime);
            }
            if (i == KeyEvent.VK_SLASH) {
                players.get(0).setPistol(false);
                players.get(0).setShotgun(false);
                players.get(0).setAssaultRifle(true);
                players.get(0).setgGun(false);
                players.get(0).setReloadTimer(assaultRifleTime);
            }
            if (i == KeyEvent.VK_G) {
                if (enemies.size() >= 1) {
                    enemies.remove(0);
                    gamePoint += 10;
                }
                players.get(0).setPistol(false);
                players.get(0).setShotgun(false);
                players.get(0).setAssaultRifle(false);
                players.get(0).setgGun(true);
                players.get(0).setReloadTimer(0);
            }
            if (players.size() == 2) {
                if (i == KeyEvent.VK_W) {
                    players.get(1).setUp(true);
                }
                if (i == KeyEvent.VK_A) {
                    players.get(1).setLeft(true);
                }
                if (i == KeyEvent.VK_D) {
                    players.get(1).setRight(true);
                }
                if (i == KeyEvent.VK_S) {
                    players.get(1).setDown(true);
                }
                if (i == KeyEvent.VK_T) {
                    if (players.get(1).isAssaultRifle() || players.get(1).isgGun()) {
                        players.get(1).setShot(!players.get(1).isShot());
                    } else {
                        players.get(1).setShot(true);
                    }
                }
                if (i == KeyEvent.VK_1) {
                    players.get(1).setPistol(true);
                    players.get(1).setShotgun(false);
                    players.get(1).setAssaultRifle(false);
                    players.get(1).setgGun(false);
                    players.get(1).setReloadTimer(pistolTime);
                }
                if (i == KeyEvent.VK_2) {
                    players.get(1).setPistol(false);
                    players.get(1).setShotgun(true);
                    players.get(1).setAssaultRifle(false);
                    players.get(1).setgGun(false);
                    players.get(1).setReloadTimer(shotgunTime);
                }
                if (i == KeyEvent.VK_3) {
                    players.get(1).setPistol(false);
                    players.get(1).setShotgun(false);
                    players.get(1).setAssaultRifle(true);
                    players.get(1).setgGun(false);
                    players.get(1).setReloadTimer(assaultRifleTime);
                }
                if (i == KeyEvent.VK_0) {
                    if (enemies.size() >= 1) {
                        enemies.remove(0);
                        gamePoint += 10;
                    }
                    players.get(1).setPistol(false);
                    players.get(1).setShotgun(false);
                    players.get(1).setAssaultRifle(false);
                    players.get(1).setgGun(true);
                    players.get(1).setReloadTimer(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (players.size() > 0) {
            if (i == KeyEvent.VK_UP) {
                players.get(0).setUp(false);
                if (!players.get(0).isDown() && !players.get(0).isLeft() && !players.get(0).isRight()) {
                    players.get(0).setFacing(UP);
                }
            }
            if (i == KeyEvent.VK_LEFT) {
                players.get(0).setLeft(false);
                if (!players.get(0).isDown() && !players.get(0).isUp() && !players.get(0).isRight()) {
                    players.get(0).setFacing(LEFT);
                }
            }
            if (i == KeyEvent.VK_RIGHT) {
                players.get(0).setRight(false);
                if (!players.get(0).isDown() && !players.get(0).isLeft() && !players.get(0).isUp()) {
                    players.get(0).setFacing(RIGHT);
                }
            }
            if (i == KeyEvent.VK_DOWN) {
                players.get(0).setDown(false);
                if (!players.get(0).isUp() && !players.get(0).isLeft() && !players.get(0).isRight()) {
                    players.get(0).setFacing(DOWN);
                }
            }
            if (players.size() == 2) {
                if (i == KeyEvent.VK_W) {
                    players.get(1).setUp(false);
                    if (!players.get(1).isDown() && !players.get(1).isLeft() && !players.get(1).isRight()) {
                        players.get(1).setFacing(UP);
                    }
                }
                if (i == KeyEvent.VK_A) {
                    players.get(1).setLeft(false);
                    if (!players.get(1).isDown() && !players.get(1).isUp() && !players.get(1).isRight()) {
                        players.get(1).setFacing(LEFT);
                    }
                }
                if (i == KeyEvent.VK_D) {
                    players.get(1).setRight(false);
                    if (!players.get(1).isDown() && !players.get(1).isLeft() && !players.get(1).isUp()) {
                        players.get(1).setFacing(RIGHT);
                    }
                }
                if (i == KeyEvent.VK_S) {
                    players.get(1).setDown(false);
                    if (!players.get(1).isUp() && !players.get(1).isLeft() && !players.get(1).isRight()) {
                        players.get(1).setFacing(DOWN);
                    }
                }
            }
        }
    }
}
//color scheme
