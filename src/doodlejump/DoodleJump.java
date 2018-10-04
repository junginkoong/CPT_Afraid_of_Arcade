package doodlejump;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

public class DoodleJump implements ActionListener, KeyListener {

    public JFrame jframe;

    public static DoodleJump doodleJump;

    public DoodlePanel renderPanel;

    public Dimension dim;

    public Toolkit toolkit;

    public Timer timer = new Timer(20, this);

    public static final int SCALE= 1, MAXJUMP = 100/(1+(SCALE/2)), NORMALJUMP = 30/(1+(SCALE/2));

    public int ticks = 0, speedy = MAXJUMP, acceleration = 1, speedx = 0, topPlatformLocation = 10, topPlatformLocationBreakable = 0, maxHeight, platformSize = 10/SCALE, platformSpacing = 10, breakablePlatformSpacing, lowerLimit, maxPlatformSpacing = 300/SCALE, xLocation = 0, gamePoint = -MAXJUMP*500/SCALE, terminalSideSpeed = 50, movementSpeed = 3, probability = 40, playerSize=20;

    public boolean up = true, right = false, left = false, endGame = false, pause = false, gameOver=false;

    Doodle player;

    public ArrayList<Platform> platforms = new ArrayList<>();

    public ArrayList<Platform> breakablePlatforms = new ArrayList<>();

    public DoodleJump() {
        jframe = new JFrame("Doodle Jump");
        jframe.setVisible(true);
        jframe.setSize(400/SCALE, 700/SCALE);
        jframe.setResizable(false);
        dim = toolkit.getDefaultToolkit().getScreenSize();
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);

        jframe.add(renderPanel = new DoodlePanel());
        jframe.addKeyListener(this);
        timer.start();
        player = new Doodle(new Point(jframe.getWidth() / 2 - 10, jframe.getHeight() - 40), playerSize/SCALE);
        platforms.add(new Platform(new Point((int) (Math.random() * (jframe.getWidth() - 6 * platformSize)), 0), platformSize, 1, 6));
        breakablePlatforms.add(new Platform(new Point((int) (Math.random() * (jframe.getWidth() - 6 * platformSize)), 0), platformSize, 1, 6));
         lowerLimit = jframe.getHeight();
         maxHeight=jframe.getHeight()/4;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {
        ticks++;
        renderPanel.repaint();
        if (ticks % 1 == 0 && !endGame && !pause) {
            breakablePlatformSpacing = (int) (100 + Math.random() * 1000);

            if (ticks % 20 == 0 && platformSpacing <= maxPlatformSpacing) {
                platformSpacing++;
            }

            if (player.getLocation().x + player.getSize() / 2 < 0) {
                player.setLocation(new Point(jframe.getWidth() - player.getSize() / 2, player.getLocation().y));
            }
            if (player.getLocation().x + player.getSize() / 2 > jframe.getWidth()) {
                player.setLocation(new Point(0 - player.getSize() / 2, player.getLocation().y));
            }
            player.setLocation(new Point(player.getLocation().x + speedx, player.getLocation().y));
            if (right) {
                speedx += acceleration;
            } else if (speedx > 0) {
                speedx -= acceleration;
            }
            if (left) {
                speedx -= acceleration;
            } else if (speedx < 0) {
                speedx += acceleration;
            }

            if (up) {
                player.setLocation(new Point(player.getLocation().x, player.getLocation().y - speedy));
                speedy -= acceleration;
            } else {
                player.setLocation(new Point(player.getLocation().x, player.getLocation().y + speedy));
                speedy += acceleration;
            }
            if (speedy <= 0) {
                up = false;
            }
            if (player.getLocation().y < maxHeight) {
                player.setLocation(new Point(player.getLocation().x, maxHeight));
            }

            if (speedy > NORMALJUMP && !up) {
                speedy = NORMALJUMP;
            }
            if (speedx > terminalSideSpeed) {
                speedx = terminalSideSpeed;
            }
            if (speedx < -terminalSideSpeed) {
                speedx = -terminalSideSpeed;
            }
//            if (player.getLocation().y > jframe.getHeight() - 40) {
//                up = true;
//                speedy = NORMALJUMP;
//            }
            if (platforms.get(0).getLocation().y < 0) {
//                System.out.println((gamePoint / 1000)-50);
                endGame();
                startGame();
                endGame = true;

            }
            if (platforms.get(0).getLocation().y > lowerLimit && platforms.size() > 2) {
                platforms.remove(0);
            }
            if (breakablePlatforms.get(0).getLocation().y > lowerLimit && breakablePlatforms.size() > 2) {
                breakablePlatforms.remove(0);
            }
            for (int i = 0; i < platforms.size(); i++) {
                if (up && player.getLocation().y == maxHeight) {
                    platforms.get(i).setLocation(new Point(platforms.get(i).getLocation().x, platforms.get(i).getLocation().y + speedy));
                    gamePoint += speedy;
                }
                if (!up && player.getLocation().y > jframe.getHeight() - 40) {
                    platforms.get(i).setLocation(new Point(platforms.get(i).getLocation().x, platforms.get(i).getLocation().y - speedy));

                }
                if (!up && platforms.get(i).isCollided(player.getLocation(), player.getSize())) {
                    player.setLocation(new Point(player.getLocation().x,platforms.get(i).getLocation().y-playerSize));
                    up = true;
                    speedy = NORMALJUMP;
                    if (platforms.get(i).getType() == 17) {
                        speedy = MAXJUMP / 2;
                    }
                }

                if (platforms.get(i).getType() <= 4) {
                    if (platforms.get(i).getLocation().x + 6 * platformSize >= jframe.getWidth()) {
                        platforms.get(i).setRight(false);
                    }
                    if (platforms.get(i).getLocation().x <= 0) {
                        platforms.get(i).setRight(true);
                    }
                    if (platforms.get(i).isRight()) {
                        platforms.get(i).setLocation(new Point(platforms.get(i).getLocation().x + movementSpeed, platforms.get(i).getLocation().y));
                    } else {
                        platforms.get(i).setLocation(new Point(platforms.get(i).getLocation().x - movementSpeed, platforms.get(i).getLocation().y));
                    }
                }
                if (platforms.get(i).getType() > 4 && platforms.get(i).getType() <= 9) {
                    if (platforms.get(i).getLocation().y <= platforms.get(i).getMaxHeight() || platforms.get(i).getLocation().y <= 0) {
                        platforms.get(i).setUp(false);
                    }
                    if (platforms.get(i).getLocation().y >= platforms.get(i).getMinHeight()) {
                        platforms.get(i).setUp(true);
                    }
                    if (platforms.get(i).isUp()) {
                        platforms.get(i).moveUpAndDown(new Point(platforms.get(i).getLocation().x, platforms.get(i).getLocation().y - movementSpeed));
                    } else {
                        platforms.get(i).moveUpAndDown(new Point(platforms.get(i).getLocation().x, platforms.get(i).getLocation().y + movementSpeed));
                    }
                }
                if (platforms.get(i).getType() > 9 && platforms.get(i).getType() <= 16) {
                    platforms.get(i).setColorChanger(platforms.get(i).getColorChanger() + 1);
                    if (platforms.get(i).getColorChanger() > 90) {
                        platforms.get(i).setSize(platforms.get(i).getSize() - 1);
                        platforms.get(i).setLocation(new Point(platforms.get(i).getLocation().x + platforms.get(i).getWidth() / 2, platforms.get(i).getLocation().y));
                    }
                    if (platforms.get(i).getSize() <= 0) {
                        platforms.remove(i);
                    }

                }

            }

            for (int i = 0; i < breakablePlatforms.size(); i++) {
                if (breakablePlatforms.get(i).getType() <= 4) {
                    if (breakablePlatforms.get(i).getLocation().x + 6 * platformSize >= jframe.getWidth()) {
                        breakablePlatforms.get(i).setRight(false);
                    }
                    if (breakablePlatforms.get(i).getLocation().x <= 0) {
                        breakablePlatforms.get(i).setRight(true);
                    }
                    if (breakablePlatforms.get(i).isRight()) {
                        breakablePlatforms.get(i).setLocation(new Point(breakablePlatforms.get(i).getLocation().x + movementSpeed, breakablePlatforms.get(i).getLocation().y));
                    } else {
                        breakablePlatforms.get(i).setLocation(new Point(breakablePlatforms.get(i).getLocation().x - movementSpeed, breakablePlatforms.get(i).getLocation().y));
                    }
                }
                if (breakablePlatforms.get(i).getType() > 4 && breakablePlatforms.get(i).getType() <= 9) {
                    if (breakablePlatforms.get(i).getLocation().y <= breakablePlatforms.get(i).getMaxHeight() || breakablePlatforms.get(i).getLocation().y <= 0) {
                        breakablePlatforms.get(i).setUp(false);
                    }
                    if (breakablePlatforms.get(i).getLocation().y >= breakablePlatforms.get(i).getMinHeight()) {
                        breakablePlatforms.get(i).setUp(true);
                    }
                    if (breakablePlatforms.get(i).isUp()) {
                        breakablePlatforms.get(i).moveUpAndDown(new Point(breakablePlatforms.get(i).getLocation().x, breakablePlatforms.get(i).getLocation().y - movementSpeed));
                    } else {
                        breakablePlatforms.get(i).moveUpAndDown(new Point(breakablePlatforms.get(i).getLocation().x, breakablePlatforms.get(i).getLocation().y + movementSpeed));
                    }
                }
                if (up && player.getLocation().y == maxHeight) {
                    breakablePlatforms.get(i).setLocation(new Point(breakablePlatforms.get(i).getLocation().x, breakablePlatforms.get(i).getLocation().y + speedy));

                }
                if (!up && player.getLocation().y > jframe.getHeight() - 40) {
                    breakablePlatforms.get(i).setLocation(new Point(breakablePlatforms.get(i).getLocation().x, breakablePlatforms.get(i).getLocation().y - speedy));

                }
                if (!up && breakablePlatforms.get(i).isCollided(player.getLocation(), player.getSize())) {
                    breakablePlatforms.get(i).setSnap(true);
                }
                if (breakablePlatforms.get(i).isSnap()) {
                    breakablePlatforms.get(i).setLocation(new Point(breakablePlatforms.get(i).getLocation().x, breakablePlatforms.get(i).getLocation().y + breakablePlatforms.get(i).getDownSpeed()));
                }

            }
            if (platforms.size() > 0 && breakablePlatforms.size() > 0) {
                topPlatformLocation = platforms.get(platforms.size() - 1).getLocation().y;
                topPlatformLocationBreakable = breakablePlatforms.get(breakablePlatforms.size() - 1).getLocation().y;

                if (topPlatformLocation > platformSpacing + (Math.random() * platformSpacing / 2)) {
                    do {
                        xLocation = (int) (Math.random() * (jframe.getWidth() - 6 * platformSize));
                        if (xLocation + 6 * platformSize < platforms.get(platforms.size() - 1).getLocation().x || xLocation > platforms.get(platforms.size() - 1).getLocation().x + 6 * platformSize) {
                            break;
                        }

                    } while (true);
                    platforms.add(new Platform(new Point(xLocation, 0), platformSize, (int) (Math.random() * probability), 6));
                }
                if (topPlatformLocationBreakable > breakablePlatformSpacing) {
                    do {
                        xLocation = (int) (Math.random() * (jframe.getWidth() - 6 * platformSize));
                        if (xLocation + 6 * platformSize < platforms.get(platforms.size() - 1).getLocation().x || xLocation > platforms.get(platforms.size() - 1).getLocation().x + 6 * platformSize) {
                            break;
                        }

                    } while (true);

                    breakablePlatforms.add(new Platform(new Point(xLocation, 0), platformSize, (int) (Math.random() * probability), 6));
                }
            }
        }
    }

    public void endGame() {
        breakablePlatforms = new ArrayList<>();
        platforms = new ArrayList<>();
        endGame = true;
    }

    public void startGame() {
        player = new Doodle(new Point(jframe.getWidth() / 2 - 10, jframe.getHeight() - 40), 20/SCALE);
        platforms.add(new Platform(new Point((int) (Math.random() * (jframe.getWidth() - 6 * platformSize)), 0), platformSize, 1, 6));
        breakablePlatforms.add(new Platform(new Point((int) (Math.random() * (jframe.getWidth() - 6 * platformSize)), 0), platformSize, 1, 6));
        speedx = 0;
        topPlatformLocation = 10;
        topPlatformLocationBreakable = 0;
        platformSpacing = 10;
        ticks = 0;
        speedy = MAXJUMP;
        up = true;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_G) {
            platforms.add(new Platform(new Point((int) (Math.random() * (jframe.getWidth() - 6 * platformSize)),(int) (Math.random() * (jframe.getHeight())) ), platformSize, 17, 6));
        }
        if (i == KeyEvent.VK_LEFT) {
            left = true;
            right = false;
        }
        if (i == KeyEvent.VK_RIGHT) {
            left = false;
            right = true;
        }
        if (i == KeyEvent.VK_SPACE) {

            if (!endGame) {
                pause = !pause;
            }
            if (endGame) {
                gameOver=true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT) {
            left = false;
        }
        if (i == KeyEvent.VK_RIGHT) {
            right = false;
        }
    }
}
//background,AI, music and sound effects,  write highscores to file, powerups(like jetpack and shield, flying hat), aesthetics of game
