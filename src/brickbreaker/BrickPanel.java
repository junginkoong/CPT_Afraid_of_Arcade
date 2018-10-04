/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 *
 * @author AntonysMac
 */
public class BrickPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        BrickBreaker brickBreaker = BrickBreaker.brickBreaker;
        if (brickBreaker.level == 0) {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 700);
            String text = "Number Of Players?(Type 1 or 2)";
            Font font = new Font("Luminari", Font.ITALIC, 30);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(text, brickBreaker.jframe.getWidth() / 2 - text.length() * 6, brickBreaker.jframe.getHeight() / 2 - 1);
        }
        if (brickBreaker.level==1){
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, 1000, 700);
            String text = "1: BrickBreaker  2: Pong";
            Font font = new Font("Luminari", Font.ITALIC, 30);
            g.setFont(font);
            g.setColor(Color.WHITE);
            g.drawString(text, brickBreaker.jframe.getWidth() / 2 - text.length() * 6, brickBreaker.jframe.getHeight() / 2 - 1);
        }
        if (brickBreaker.level == 2) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 1000, 700);
            g.setColor(Color.white);
            for (int i = 0; i < brickBreaker.starsX.length; i++) {
                g.fillRect(brickBreaker.starsX[i], brickBreaker.starsY[i], (int) (Math.random() * 3) + 1, (int) (Math.random() * 3) + 1);
            }
        }
        if (brickBreaker.level == 3) {
            g.setColor(new Color(125 - brickBreaker.nightOrDayCounter, 240 - 3 * brickBreaker.nightOrDayCounter, 255 - brickBreaker.nightOrDayCounter));
            g.fillRect(0, 0, 1000, 700);
            g.setColor(new Color(255, 204, 0));
            if (brickBreaker.onePlayer){
            g.fillRect(0, 200, 1000, 700);
            }
            else{
                g.fillRect(0, 300, 1000, 700);
            }
        }
        if (brickBreaker.level == 2) {
            for (int i = 0; i < 11; i++) {
                g.setColor(new Color(250 - 25 * i, 250 - 25 * i, 250 - 25 * i));
                g.fillOval(brickBreaker.ball.x + i, brickBreaker.ball.y + i, brickBreaker.ballScale - 2 * i, brickBreaker.ballScale - 2 * i);
            }
            if (brickBreaker.onePlayer) {
                for (int z = 0; z < brickBreaker.lives; z++) {
                    for (int i = 0; i < 11; i++) {
                        g.setColor(new Color(250 - 25 * i, 250 - 25 * i, 250 - 25 * i));
                        g.fillOval(brickBreaker.jframe.getWidth() - 110 + 30 * z + i, brickBreaker.jframe.getHeight() - 43 + i, brickBreaker.ballScale - 2 * i, brickBreaker.ballScale - 2 * i);
                    }
                }
            }
        } else {
            for (int i = 0; i < 11; i++) {
                g.setColor(new Color(25 * i, 25 * i, 25 * i));
                g.fillOval(brickBreaker.ball.x + i, brickBreaker.ball.y + i, brickBreaker.ballScale - 2 * i, brickBreaker.ballScale - 2 * i);
            }
            if (brickBreaker.onePlayer) {
                for (int z = 0; z < brickBreaker.lives; z++) {
                    for (int i = 0; i < 11; i++) {
                        g.setColor(new Color(25 * i, 25 * i, 25 * i));
                        g.fillOval(brickBreaker.jframe.getWidth() - 110 + 30 * z + i, brickBreaker.jframe.getHeight() - 43 + i, brickBreaker.ballScale - 2 * i, brickBreaker.ballScale - 2 * i);
                    }
                }
            }
        }
        g.setColor(new Color(0, 191, 255));
        g.fillRoundRect(brickBreaker.paddle.x, brickBreaker.paddle.y, 6 * brickBreaker.paddleSize, brickBreaker.paddleSize, brickBreaker.paddleSize, brickBreaker.paddleSize);
        for (int i = 0; i < 11; i++) {
            g.setColor(new Color(55 + 20 * i, 205 + 5 * i, 255));
            g.fillRoundRect(brickBreaker.paddle.x + i * 4, brickBreaker.paddle.y + i, 6 * brickBreaker.paddleSize - i * 8, brickBreaker.paddleSize - i * 2, brickBreaker.paddleSize, brickBreaker.paddleSize);
        }
        if (!brickBreaker.onePlayer) {
            g.setColor(new Color(255, 55, 55));
            g.fillRoundRect(brickBreaker.paddle2.x, brickBreaker.paddle2.y, 6 * brickBreaker.paddleSize, brickBreaker.paddleSize, brickBreaker.paddleSize, brickBreaker.paddleSize);
            for (int i = 0; i < 11; i++) {
                g.setColor(new Color(255, 55 + 20 * i, 55 + 20 * i));
                g.fillRoundRect(brickBreaker.paddle2.x + i * 4, brickBreaker.paddle2.y + i, 6 * brickBreaker.paddleSize - i * 8, brickBreaker.paddleSize - i * 2, brickBreaker.paddleSize, brickBreaker.paddleSize);
            }
        }

        if (brickBreaker.aim) {
            if (brickBreaker.player1sBall) {
                g.setColor(Color.RED);
                if (brickBreaker.angle == 1) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y, brickBreaker.ball.x - brickBreaker.paddleSize * 3 + brickBreaker.ballScale / 2, brickBreaker.ball.y - 50);
                }
                if (brickBreaker.angle == 2) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y, brickBreaker.ball.x - brickBreaker.paddleSize + brickBreaker.ballScale / 2, brickBreaker.ball.y - 75);
                }
                if (brickBreaker.angle == 3) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y, brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y - 100);
                }
                if (brickBreaker.angle == 4) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y, brickBreaker.ball.x + brickBreaker.paddleSize + brickBreaker.ballScale / 2, brickBreaker.ball.y - 75);
                }
                if (brickBreaker.angle == 5) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y, brickBreaker.ball.x + brickBreaker.paddleSize * 3 + brickBreaker.ballScale / 2, brickBreaker.ball.y - 50);
                }
            } else {
                g.setColor(Color.RED);
                if (brickBreaker.angle == 1) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + brickBreaker.ballScale, brickBreaker.ball.x - brickBreaker.paddleSize * 3 + brickBreaker.ballScale / 2, brickBreaker.ball.y + 50);
                }
                if (brickBreaker.angle == 2) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + brickBreaker.ballScale, brickBreaker.ball.x - brickBreaker.paddleSize + brickBreaker.ballScale / 2, brickBreaker.ball.y + 75);
                }
                if (brickBreaker.angle == 3) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + brickBreaker.ballScale, brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + 100);
                }
                if (brickBreaker.angle == 4) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + brickBreaker.ballScale, brickBreaker.ball.x + brickBreaker.paddleSize + brickBreaker.ballScale / 2, brickBreaker.ball.y + 75);
                }
                if (brickBreaker.angle == 5) {
                    g.drawLine(brickBreaker.ball.x + brickBreaker.ballScale / 2, brickBreaker.ball.y + brickBreaker.ballScale, brickBreaker.ball.x + brickBreaker.paddleSize * 3 + brickBreaker.ballScale / 2, brickBreaker.ball.y + 50);
                }
            }
        }

//        if (brickBreaker.colour) {
//            g.setColor(Color.RED);
//        } else {
//            g.setColor(Color.GREEN);
//        }
        for (int i = 0; i < brickBreaker.blocks.length; i++) {
            for (int z = 0; z < brickBreaker.blocks[0].length; z++) {
                if (brickBreaker.blocksVisible[z][i] > 0) {

                    if (brickBreaker.level == 2) {
                        if (brickBreaker.blocksVisible[z][i] == 1) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(25 * f, 245 + f, 255));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 2) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(25 * f, 191 + 6 * f, 255));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 3) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(25 * f, 75 + 18 * f, 255));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 4) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(155 + 10 * f, 155 + 10 * f, 155 + 10 * f));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        }
                    } else if (brickBreaker.level == 3) {
                        if (brickBreaker.blocksVisible[z][i] == 1) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(230, 222 + f / 2, 23 * f));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 2) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(201, 175 + 2 * f, 20 * f));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 3) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(245 + (int) brickBreaker.nightOrDayCounter / 8, 255, 15 + 3 * brickBreaker.nightOrDayCounter));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        } else if (brickBreaker.blocksVisible[z][i] == 4) {
                            for (int f = 0; f < 11; f++) {
                                g.setColor(new Color(225 + 3 * f, 225 + 3 * f, 225 + 3 * f));
                                g.fillRect(brickBreaker.blocks[i][z].x + f, brickBreaker.blocks[i][z].y + f, 3 * brickBreaker.brickSize - 2 * f, brickBreaker.brickSize - 2 * f);
                            }
                        }
                    }
                }
            }
        }
        if (brickBreaker.onePlayer) {
            g.setColor(Color.GRAY);
            String text = "Lives:";
            Font font = new Font("Luminari", Font.PLAIN, 30);
            g.setFont(font);
            g.drawString(text, brickBreaker.jframe.getWidth() - 200, brickBreaker.jframe.getHeight() - 30);
            if (brickBreaker.level < 3) {
                g.setColor(Color.green);
            } else {
                g.setColor(Color.blue);
            }
            text = "Score: " + brickBreaker.gamePoint;
            g.drawString(text, 20, brickBreaker.jframe.getHeight() - 30);
        } else {
            g.setColor(Color.RED);
            String text = "Player 2:" + brickBreaker.gamePoint2;
            Font font = new Font("Luminari", Font.PLAIN, 30);
            g.setFont(font);
            g.drawString(text, brickBreaker.jframe.getWidth() - 200, brickBreaker.jframe.getHeight() - 30);
             g.setColor(new Color(0, 191, 255));
            text = "Player 1: " + brickBreaker.gamePoint1;
            g.drawString(text, 20, brickBreaker.jframe.getHeight() - 30);
        }
    }
}
