/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.Timer;
import sun.applet.Main;

public class BrickBreaker implements ActionListener, KeyListener {

    public JFrame jframe;

    public static BrickBreaker brickBreaker;

    public static Mixer mixer;

    public static Clip clip;

    public BrickPanel renderPanel;

    public Dimension dim;

    public Timer timer = new Timer(20, this);

    public Point paddle, ball, paddle2;

    public static final int RIGHT = 1, LEFT = 2, UP = 3, DOWN = 4, STATIC = 0, INVISIBLEBLOCK = 0;

    public int fastSpeed = 8, midSpeed = 6, slowSpeed = 4, brickSize = 10, ballScale = 7, paddleSize = 20, speedOfColourChange = 10, ticks = 0, ballDirectionx = RIGHT, ballDirectiony = UP, speedx = slowSpeed, speedy = fastSpeed, extraBit = 8, whenToChangeColour = 10, level = 0, nightOrDayCounter = 0, beginner = 0, lives = 3, angle = 3, gamePoint1 = 0, gamePoint = 0, gamePoint2 = 0, shiftDownLevel = 0, shiftRightLevel = 0, paddleSpeed = 0, paddle2Speed = 0, acceleration = 1;

    public int[][] blocksVisible;

    public int[] starsX, starsY;

    public Point[][] blocks;

    public boolean paddleDirectionLeft = false, paddleDirectionRight = false, paddle2DirectionLeft = false, paddle2DirectionRight = false, colour = true, night = true, gameStart = false, onePlayer = true, aim = true, player1sBall = true, pongMode = false, twoPlayerPong = false, gameOver=false;

    public BrickBreaker() {
        dim = Toolkit.getDefaultToolkit().getScreenSize();
        jframe = new JFrame("Brick Breaker");
        jframe.setVisible(true);
        jframe.setResizable(false);
        jframe.setSize(1000, 700);
        paddle = new Point(jframe.getWidth() / 2, jframe.getHeight() - 100);
        paddle2 = new Point(jframe.getWidth() / 2, 70);
        ball = new Point(paddle.x + 3 * paddleSize, paddle.y + ballScale);
        blocksVisible = new int[1][1];
        blocks = new Point[1][1];
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new BrickPanel());
        jframe.addKeyListener(this);
        starsX = new int[500];
        starsY = new int[500];
        for (int i = 0; i < starsX.length; i++) {
            starsX[i] = (int) (Math.random() * 1000);
            starsY[i] = (int) (Math.random() * 700);
        }
        for (int i = 0; i < blocks.length; i++) {
            for (int z = 0; z < blocks[0].length; z++) {
                blocks[i][z] = new Point(50 + shiftDownLevel + 3 * brickSize * i, 20 + brickSize * z);
            }
        }

        timer.start();

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
        
        if (lives < 0 || level == 4) {
            gameOver=true;
        }
        if (gamePoint1>=300&&pongMode){
            System.out.println("Player 1 wins!");
            gameOver=true;
            
        }
        if (gamePoint2>=300&&pongMode){
            System.out.println("Player 2 wins!");
            gameOver=true;
            
        }
        renderPanel.repaint();
        if (gameStart) {
            aim = false;
        }
        if (aim) {
            paddleSpeed = 0;
            paddle2Speed = 0;
            if (player1sBall) {
                ballDirectiony = UP;
            } else {
                ballDirectiony = DOWN;
            }
            if (angle == 1) {
                ballDirectionx = LEFT;
                speedx = fastSpeed;
            } else if (angle == 2) {
                ballDirectionx = LEFT;
                speedx = slowSpeed;
            } else if (angle == 3) {
                ballDirectionx = STATIC;

            } else if (angle == 4) {
                ballDirectionx = RIGHT;
                speedx = slowSpeed;
            } else if (angle == 5) {
                ballDirectionx = RIGHT;
                speedx = fastSpeed;
            }
            if (pongMode) {
                speedy = fastSpeed;
            }
        }

//        if (ticks == whenToChangeColour) {
//            colour = !colour;
//            whenToChangeColour += speedOfColourChange;
//        }
        if (ticks % 1 == 0 && gameStart) {
            if (checkForWin()) {
                if (!pongMode) {
                    level++;
                    levelChanger(level);
                }
                if (pongMode && level < 2) {
                    level++;
                    levelChanger(level);
                }
            }
            if (pongMode && ticks % 100 == 0) {
                speedy++;
            }
            if (ticks % 30 == 0) {
                gamePoint += 1 * level;
            }
            if (level == 2) {
                if (ticks % 30000 == 0) {
                }
            }
            if (level == 3) {
                if (ticks % 15000 == 0) {
                }
            }
            if (nightOrDayCounter < 1) {
                night = true;
            }
            if (nightOrDayCounter >= 80) {
                night = false;
            }
            if (night && ticks % 1 == 0) {
                nightOrDayCounter++;
            } else if (ticks % 1 == 0) {
                nightOrDayCounter--;
            }

            if (paddleDirectionLeft) {
                paddleSpeed -= 2 * acceleration;
            }
            if (paddleDirectionRight) {
                paddleSpeed += 2 * acceleration;
            }
            if (paddle2DirectionRight) {
                paddle2Speed += 2 * acceleration;
            }
            if (paddle2DirectionLeft) {
                paddle2Speed -= 2 * acceleration;
            }
            if (paddleSpeed > 0) {
                paddleSpeed -= acceleration;
            }
            if (paddleSpeed < 0) {
                paddleSpeed += acceleration;
            }
            if (paddle2Speed > 0) {
                paddle2Speed -= acceleration;
            }
            if (paddle2Speed < 0) {
                paddle2Speed += acceleration;
            }
            if (paddle.x + 6 * paddleSize <= jframe.getWidth() && paddle.x >= 0) {
                paddle = new Point(paddle.x + paddleSpeed, paddle.y);
            }
            if (!onePlayer && paddle2.x + 6 * paddleSize <= jframe.getWidth() && paddle2.x >= 0) {
                paddle2 = new Point(paddle2.x + paddle2Speed, paddle2.y);
            }
            if (paddle.x + 6 * paddleSize >= jframe.getWidth()) {
                paddle = new Point(jframe.getWidth() - 6 * paddleSize - 1, paddle.y);
                paddleSpeed = 0;
            }
            if (paddle.x <= 0) {
                paddle = new Point(1, paddle.y);
                paddleSpeed = 0;
            }
            if (!onePlayer && paddle2.x + 6 * paddleSize >= jframe.getWidth()) {
                paddle2 = new Point(jframe.getWidth() - 6 * paddleSize - 1, paddle2.y);
                paddle2Speed = 0;
            }
            if (!onePlayer && paddle2.x <= 0) {
                paddle2 = new Point(1, paddle2.y);
                paddle2Speed = 0;
            }
            if (twoPlayerPong && ballDirectiony == UP && ball.x + ballScale / 2 > paddle2.x + 3 * paddleSize) {
                paddle2DirectionRight = true;
                paddle2DirectionLeft = false;
            }
            if (twoPlayerPong && ballDirectiony == UP && ball.x + ballScale / 2 < paddle2.x + 3 * paddleSize) {
                paddle2DirectionRight = false;
                paddle2DirectionLeft = true;
            }
            if (twoPlayerPong && ballDirectiony == DOWN) {
                paddle2DirectionRight = false;
                paddle2DirectionLeft = false;
            }
           

            if (ballDirectiony == UP) {

                for (int i = 0; i < blocks.length; i++) {
                    for (int z = 0; z < blocks[0].length; z++) {
                        if (ball.y <= blocks[i][z].y + brickSize + extraBit && ball.y >= blocks[i][z].y + brickSize - extraBit && ball.x + ballScale / 2 >= blocks[i][z].x && ball.x + ballScale / 2 <= blocks[i][z].x + 3 * brickSize && blocksVisible[z][i] > 0) {
                            ballDirectiony = DOWN;
                            gamePoint += 10 * level;
                            if (blocksVisible[z][i] < 4) {
                                blocksVisible[z][i]--;
                                blockMusic();
                            } else {
                                if (level == 3) {
                                    blocksVisible[z][i]--;
                                    gamePoint += 10 * level;
                                }
                                gamePoint -= 10 * level;
                                metalMusic();
                            }
                        }
                    }
                }
                if (!onePlayer) {
                    if (ball.x + ballScale >= paddle2.x && ball.x + ballScale / 2 <= paddle2.x + 1 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = fastSpeed;
                        ballDirectionx = LEFT;

                    }
                    if (ball.x + ballScale / 2 > paddle2.x + 1 * paddleSize && ball.x + ballScale / 2 <= paddle2.x + 2 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = midSpeed;
                        ballDirectionx = LEFT;

                    }
                    if (ball.x + ballScale / 2 > paddle2.x + 2 * paddleSize && ball.x + ballScale / 2 <= paddle2.x + 3 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = slowSpeed;
                        ballDirectionx = LEFT;

                    }
                    if (ball.x + ballScale / 2 > paddle2.x + 3 * paddleSize && ball.x + ballScale / 2 <= paddle2.x + 4 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = slowSpeed;
                        ballDirectionx = RIGHT;

                    }
                    if (ball.x + ballScale / 2 > paddle2.x + 4 * paddleSize && ball.x + ballScale / 2 <= paddle2.x + 5 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = midSpeed;
                        ballDirectionx = RIGHT;

                    }
                    if (ball.x + ballScale / 2 > paddle2.x + 5 * paddleSize && ball.x <= paddle2.x + 6 * paddleSize && ball.y >= paddle2.y && ball.y <= paddle2.y + paddleSize) {
                        ball=(new Point(ball.x, paddle2.y+paddleSize));
                        ballDirectiony = DOWN;
                        speedx = fastSpeed;
                        ballDirectionx = RIGHT;

                    }
                }

                if (ball.y >= 0) {
                    ball = new Point(ball.x, ball.y - speedy);
                } else if (onePlayer) {
                    ballDirectiony = DOWN;
                } else {
                    paddle = new Point(jframe.getWidth() / 2, jframe.getHeight() - 100);
                    paddle2 = new Point(jframe.getWidth() / 2, 70);
                    ball = new Point(paddle2.x + 3 * paddleSize, paddle2.y + paddleSize);
                    gameStart = false;
                    gamePoint1 += gamePoint;
                    gamePoint = 0;
                    aim = true;
                    player1sBall = false;
                }

            }
            if (ballDirectiony == DOWN) {
                for (int i = 0; i < blocks.length; i++) {
                    for (int z = 0; z < blocks[0].length; z++) {
                        if (ball.y + ballScale <= blocks[i][z].y + extraBit && ball.y + ballScale >= blocks[i][z].y - extraBit && ball.x + ballScale / 2 >= blocks[i][z].x && ball.x + ballScale / 2 <= blocks[i][z].x + 3 * brickSize && blocksVisible[z][i] > 0) {
                            ballDirectiony = UP;
                            gamePoint += 10 * level;
                            if (blocksVisible[z][i] < 4) {
                                blocksVisible[z][i]--;
                                blockMusic();

                            } else {
                                if (level == 3) {
                                    blocksVisible[z][i]--;
                                    gamePoint += 10 * level;
                                }
                                metalMusic();
                                gamePoint -= 10 * level;
                            }
                        }
                    }
                }
                if (ball.x + ballScale >= paddle.x && ball.x + ballScale / 2 <= paddle.x + 1 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = fastSpeed;
                    ballDirectionx = LEFT;
                    

                }
                if (ball.x + ballScale / 2 > paddle.x + 1 * paddleSize && ball.x + ballScale / 2 <= paddle.x + 2 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = midSpeed;
                    ballDirectionx = LEFT;

                }
                if (ball.x + ballScale / 2 > paddle.x + 2 * paddleSize && ball.x + ballScale / 2 <= paddle.x + 3 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = slowSpeed;
                    ballDirectionx = LEFT;

                }
                if (ball.x + ballScale / 2 > paddle.x + 3 * paddleSize && ball.x + ballScale / 2 <= paddle.x + 4 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = slowSpeed;
                    ballDirectionx = RIGHT;

                }
                if (ball.x + ballScale / 2 > paddle.x + 4 * paddleSize && ball.x + ballScale / 2 <= paddle.x + 5 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = midSpeed;
                    ballDirectionx = RIGHT;

                }
                if (ball.x + ballScale / 2 > paddle.x + 5 * paddleSize && ball.x <= paddle.x + 6 * paddleSize && ball.y + ballScale >= paddle.y && ball.y + ballScale <= paddle.y + paddleSize) {
                    ball=(new Point(ball.x, paddle.y-ballScale));
                    ballDirectiony = UP;
                    speedx = fastSpeed;
                    ballDirectionx = RIGHT;

                }

                if (ball.y <= jframe.getHeight() - 2.5 * brickSize) {
                    ball = new Point(ball.x, ball.y + speedy);
                } else {
                    paddle = new Point(jframe.getWidth() / 2, jframe.getHeight() - 100);
                    paddle2 = new Point(jframe.getWidth() / 2, 70);
                    ball = new Point(paddle.x + 3 * paddleSize, paddle.y - ballScale);
                    gameStart = false;
                    if (onePlayer) {
                        lives--;
                    } else {
                        gamePoint2 += gamePoint;
                        gamePoint = 0;
                    }
                    aim = true;
                    player1sBall = true;
                }
            }
            if (ballDirectionx == RIGHT) {
                for (int i = 0; i < blocks.length; i++) {
                    for (int z = 0; z < blocks[0].length; z++) {
                        if (ball.x + ballScale >= blocks[i][z].x - extraBit && ball.x + ballScale <= blocks[i][z].x + extraBit && ball.y >= blocks[i][z].y && ball.y + ballScale / 2 <= blocks[i][z].y + brickSize && blocksVisible[z][i] > 0) {
                            ballDirectionx = LEFT;
                            gamePoint += 10 * level;
                            if (blocksVisible[z][i] < 4) {
                                blocksVisible[z][i]--;
                                blockMusic();
                            } else {
                                if (level == 3) {
                                    blocksVisible[z][i]--;
                                    gamePoint += 10 * level;
                                }
                                metalMusic();
                                gamePoint -= 10 * level;
                            }
                        }
                    }
                }
                if (ball.x <= jframe.getWidth() - brickSize) {
                    ball = new Point(ball.x + speedx, ball.y);
                } else {
                    ballDirectionx = LEFT;
                }
            }
            if (ballDirectionx == LEFT) {
                for (int i = 0; i < blocks.length; i++) {
                    for (int z = 0; z < blocks[0].length; z++) {
                        if (ball.x >= blocks[i][z].x + 3 * brickSize - extraBit && ball.x <= blocks[i][z].x + 3 * brickSize + extraBit && ball.y + ballScale / 2 >= blocks[i][z].y && ball.y + ballScale / 2 <= blocks[i][z].y + brickSize && blocksVisible[z][i] > 0) {
                            gamePoint += 10 * level;
                            ballDirectionx = RIGHT;
                            if (blocksVisible[z][i] < 4) {
                                blocksVisible[z][i]--;
                                blockMusic();
                            } else {
                                if (level == 3) {
                                    blocksVisible[z][i]--;
                                    gamePoint += 10 * level;
                                }
                                gamePoint -= 10 * level;
                                metalMusic();
                            }
                        }
                    }
                }

                if (ball.x >= 0) {
                    ball = new Point(ball.x - speedx, ball.y);
                } else {
                    ballDirectionx = RIGHT;
                }
            }
            beginner++;
            if (beginner == 1) {
                gameStart = false;
                aim = true;
                ball = new Point(paddle.x + 3 * paddleSize, paddle.y - ballScale);
                gamePoint--;
                player1sBall = true;
            }
        }
    }

    public boolean checkForWin() {
        for (int i = 0; i < blocksVisible.length; i++) {
            for (int z = 0; z < blocksVisible[0].length; z++) {
                if (blocksVisible[i][z] != 4 && blocksVisible[i][z] != 0) {
                    return false;
                }
            }
        }
        if (level > 1 && !pongMode) {
        }
        return true;
    }

    public void levelChanger(int level) {
        paddle = new Point(jframe.getWidth() / 2, jframe.getHeight() - 100);

        ticks = 0;
        beginner = 0;

        if (level == 2) {
            if (!onePlayer) {
                shiftDownLevel = 230;
                shiftRightLevel = 240;
            }
            blocks = new Point[15][15];
            ballScale = 10;
            paddleSize = 20;
            if (onePlayer) {
                brickSize = 20;
            } else if (!pongMode) {
                brickSize = 10;
                ballScale = 5;
            }
            ball = new Point(paddle.x + 3 * paddleSize, paddle.y - ballScale);
            player1sBall = true;
            for (int i = 0; i < blocks.length; i++) {
                for (int z = 0; z < blocks[0].length; z++) {
                    blocks[i][z] = new Point(shiftRightLevel + 50 + 3 * brickSize * i, shiftDownLevel + 20 + brickSize * z);
                }
            }
            blocksVisible = new int[][]{
                {3, 1, 1, 2, 3, 2, 1, 4, 1, 2, 3, 2, 1, 1, 3},
                {2, 3, 3, 1, 2, 3, 2, 1, 2, 3, 2, 1, 3, 3, 2},
                {0, 2, 3, 3, 1, 2, 3, 2, 3, 2, 1, 3, 3, 2, 0},
                {0, 0, 2, 4, 3, 1, 2, 3, 2, 1, 3, 4, 2, 0, 0},
                {0, 0, 0, 2, 3, 3, 1, 2, 1, 3, 3, 2, 0, 0, 0},
                {0, 0, 0, 0, 2, 3, 3, 1, 3, 3, 2, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 4, 4, 4, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 4, 4, 4, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 2, 4, 4, 4, 2, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 2, 3, 3, 1, 3, 3, 2, 0, 0, 0, 0},
                {0, 0, 0, 2, 3, 3, 1, 2, 1, 3, 3, 2, 0, 0, 0},
                {0, 0, 2, 4, 3, 1, 2, 3, 2, 1, 3, 4, 2, 0, 0},
                {0, 2, 3, 3, 1, 2, 3, 2, 3, 2, 1, 3, 3, 2, 0},
                {2, 3, 3, 1, 2, 3, 2, 1, 2, 3, 2, 1, 3, 3, 2},
                {3, 1, 1, 2, 3, 2, 1, 4, 1, 2, 3, 2, 1, 1, 3},};
        } else if (level == 3) {
            if (!onePlayer) {
                shiftDownLevel = 200;
                shiftRightLevel = 270;
            }
            speedy=slowSpeed;
            slowSpeed/=2;
            midSpeed/=2;
            fastSpeed/=2;
            blocks = new Point[33][44];
            paddleSize = 15;
            if (onePlayer) {
                brickSize = 10;
                ballScale = 8;
            } else if (!pongMode) {
                brickSize = 5;
                ballScale = 4;
            }
            player1sBall = true;
            ball = new Point(paddle.x + 3 * paddleSize, paddle.y - ballScale);
            for (int i = 0; i < blocks.length; i++) {
                for (int z = 0; z < blocks[0].length; z++) {
                    blocks[i][z] = new Point(shiftRightLevel + 5 + 3 * brickSize * i, shiftDownLevel + brickSize * z);
                }
            }
            blocksVisible = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 4, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 3, 3, 3, 3, 3,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0,},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0,},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0,},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0,},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0,},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0,},
                {0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0,},
                {0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,},};
        }
        if (!onePlayer && level == 3) {
            for (int i = 0; i < blocksVisible.length; i++) {
                for (int z = 0; z < blocksVisible[0].length; z++) {
                    if (blocksVisible[i][z] == 4 || blocksVisible[i][z] == 3) {
                        blocksVisible[i][z] = 0;
                    }
                }
            }
        }
        if (pongMode) {
            for (int i = 0; i < blocksVisible.length; i++) {
                for (int z = 0; z < blocksVisible[0].length; z++) {

                    blocksVisible[i][z] = 0;

                }
            }
        }
        gameStart = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT) {

            paddleDirectionLeft = true;
            paddleDirectionRight = false;

        }
        if (i == KeyEvent.VK_RIGHT) {

            paddleDirectionLeft = false;
            paddleDirectionRight = true;

        }
        if (i == KeyEvent.VK_A) {
            if (!onePlayer) {
                paddle2DirectionLeft = true;
                paddle2DirectionRight = false;
            }

        }
        if (i == KeyEvent.VK_D) {
            if (!onePlayer) {
                paddle2DirectionLeft = false;
                paddle2DirectionRight = true;
            }

        }
        if (i == KeyEvent.VK_G) {
            removeABlock();
        }
        if (i == KeyEvent.VK_1) {
            if (!gameStart) {
                if (level == 0) {
                    onePlayer = true;
                    gameStart = true;
                    twoPlayerPong = true;
                } else if (level == 1) {
                    pongMode = false;
                    gameStart = true;

                }
            }
        }
        if (i == KeyEvent.VK_2) {
            if (!gameStart) {
                if (level == 0) {
                    onePlayer = false;
                    gameStart = true;
                    twoPlayerPong = false;
                } else if (level == 1) {
                    pongMode = true;
                    gameStart = true;
                    onePlayer = false;
                }
            }
        }
        if (i == KeyEvent.VK_SPACE) {
            gameStart = !gameStart;
        }
    }

    public void removeABlock() {
        for (int i = 0; i < blocksVisible.length; i++) {
            for (int z = 0; z < blocksVisible[0].length; z++) {
                if (blocksVisible[i][z] != 0 && blocksVisible[i][z] != 4) {
                    blocksVisible[i][z] = 0;
                    break;
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_LEFT) {
            if (aim && angle > 1) {
                angle--;
            }
            paddleDirectionLeft = false;
        }
        if (i == KeyEvent.VK_RIGHT) {
            if (aim && angle < 5) {
                angle++;
            }
            paddleDirectionRight = false;
        }
        if (i == KeyEvent.VK_A) {
            if (aim && angle > 1) {
                angle--;
            }
            paddle2DirectionLeft = false;
        }
        if (i == KeyEvent.VK_D) {
            if (aim && angle < 5) {
                angle++;
            }
            paddle2DirectionRight = false;
        }

    }

    public void blockMusic() {
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        }
        try {
            URL soundURL = Main.class.getResource("/brickbreaker/Clink.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip.open(audioStream);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        clip.start();

    }

    public void metalMusic() {
        Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
        mixer = AudioSystem.getMixer(mixInfos[0]);
        DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);
        try {
            clip = (Clip) mixer.getLine(dataInfo);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        }
        try {
            URL soundURL = Main.class.getResource("/brickbreaker/Clunk.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            clip.open(audioStream);
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        } catch (UnsupportedAudioFileException uafe) {
            uafe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        clip.start();

    }
 
}
//items (OOP), more levels after doodlejump
//Doodle Jump
