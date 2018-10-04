package cpt_arcade;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Snake extends JPanel implements KeyListener {

    public static int x = 25, y = 25, f = 0, t = 0, constantx = 0, constanty = 25, directionR = 0, directionL = 0, directionU = 0, directionD = 0, randomBox = 0, gamePoint = 0;
    public static int[][] blocks = new int[50][50];
    public static ArrayList<Integer> length = new ArrayList<Integer>();
    public static ArrayList<Integer> coordinateX = new ArrayList<Integer>();
    public static ArrayList<Integer> coordinateY = new ArrayList<Integer>();
    public static boolean paused = false, gameOver = false;

    public int randomBoxX() {
        return (int) (Math.random() * 46 + 2);
    }

    public int randomBoxY() {
        return (int) (Math.random() * 46 + 2);
    }

    public void right() {
        directionR = 1;
    }

    public void left() {
        directionL = 1;
    }

    public void up() {
        directionU = 1;
    }

    public void down() {
        directionD = 1;
    }

    public void paint(Graphics g) {
        int xCoor = 0, yCoor = 0;
        if (!paused) {
            super.paint(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            addKeyListener(this);
            //background color
            g.setColor(Color.black);
            g.fillRect(0, 0, 515, 540);
            //lines
            for (int i = 0; i < 50; i++) {
                for (int z = 0; z < 50; z++) {
                    g.setColor(new Color(255, 0, 0));
                    g.drawRect(i * 10, z * 10, 10, 10);
                }
            }
            //boarder line
            g.setColor(Color.white);
            g.drawRect(20, 20, 460, 460);
            //initial size
            if (length.size() == 0) {
                for (int i = 0; i < 5; i++) {
                    length.add(0);
                }
            }
            //random box appears
            if (randomBox == 0) {
                do {
                    xCoor = randomBoxX();
                    yCoor = randomBoxY();
                    if (blocks[yCoor][xCoor] == 0) {
                        randomBox = 1;
                        blocks[yCoor][xCoor] = 2;
                    }
                } while (blocks[yCoor][xCoor] != 2);
            }
            //Direction 
            if (directionR == 1) {
                x += 1;
            } else if (directionL == 1) {
                x -= 1;
            } else if (directionU == 1) {
                y -= 1;
            } else if (directionD == 1) {
                y += 1;
            }
            //eat the box
            if (blocks[y][x] == 2) {
                length.add(0);
                randomBox = 0;
                gamePoint += 50;
            }
            //end game
            if (blocks[y][x] == 1 && coordinateX.size() > 5) {
                gameOver = true;
            }
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 50; j++) {
                    if (blocks[i][j] == 1) {
                        gameOver = true;
                    }
                    if (blocks[j][i] == 1) {
                        gameOver = true;
                    }
                }
            }
            for (int i = 48; i < 50; i++) {
                for (int j = 0; j < 50; j++) {
                    if (blocks[i][j] == 1) {
                        gameOver = true;
                    }
                    if (blocks[j][i] == 1) {
                        gameOver = true;
                    }
                }
            }
            // create box
            blocks[y][x] = 1;
            // save to array
            coordinateX.add(x);
            coordinateY.add(y);
            // decrease size
            if (coordinateX.size() > length.size()) {
                blocks[coordinateY.remove(0)][coordinateX.remove(0)] = 0;
            }
            if (x > 1) {
                // create box for where there is 1
                for (int w = 0; w < 50; w++) {
                    for (int z = 0; z < 50; z++) {
                        //snake blocks
                        if (blocks[w][z] == 1) {
                            g.setColor(new Color(174, 223, 255));
                            g.fillRect(z * 10, w * 10, 10, 10);
                        }
                        //eating food design
                        if (blocks[w][z] == 2) {
                            g.setColor(Color.white);
                            g.fillRect(z * 10, w * 10, 10, 10);
                        }
                    }
                }
            }
        }

    }

    public static void GameOver() {
        x = 25; 
        y = 25; 
        f = 0; 
        t = 0; 
        constantx = 0; 
        constanty = 25; 
        directionR = 0;
        directionL = 0; 
        directionU = 0; 
        directionD = 0; 
        randomBox = 0;
        gamePoint = 0;
        blocks = new int[50][50];
        length = new ArrayList<Integer>();
        coordinateX = new ArrayList<Integer>();
        coordinateY = new ArrayList<Integer>();
        paused = false; 
        gameOver = false;
    }

    public void resetDirection() {
        directionR = 0;
        directionL = 0;
        directionU = 0;
        directionD = 0;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (directionL != 1) {
                resetDirection();
                right();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (directionR != 1) {
                resetDirection();
                left();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (directionD != 1) {
                resetDirection();
                up();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (directionU != 1) {
                resetDirection();
                down();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            paused = !paused;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (directionL != 1) {
                resetDirection();
                right();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (directionR != 1) {
                resetDirection();
                left();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            if (directionD != 1) {
                resetDirection();
                up();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            if (directionU != 1) {
                resetDirection();
                down();
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {

        }
    }
}
