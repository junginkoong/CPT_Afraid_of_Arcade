package cpt_arcade;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class Duet extends JPanel implements KeyListener {

    //Direction variable
    static int DirectionX = 0, DirectionY = 0, speed = 1, circleDiameter = 30, timer = 0, backgroundTimer = 0;
    static int xBlue = 125, yBlue = 500, xRed = 325, yRed = 500;
    static int color = 200, point = 0, StringHSX = 110, StringHSY = 100, colorTimer = 0, delay = 4, endingCircle = 0, endingColor = 0, gamePoint = 0;
    static boolean KeyPressedRight = false, KeyPressedLeft = false, backgroundCheck = false, MusicDelay = true, TimerCheck = false, gameOver = true, musicPlay = true;
    static ArrayList<Integer> numberBox = new ArrayList<Integer>();
    static ArrayList<Integer> coordinateY = new ArrayList<Integer>();
    static ArrayList<Integer> coordinateX = new ArrayList<Integer>();
    static ArrayList<Integer> lengthBox = new ArrayList<Integer>();
    static ArrayList<Integer> heightBox = new ArrayList<Integer>();
    static ArrayList<Boolean> TrueFalseBox = new ArrayList<Boolean>();
    static ArrayList<Integer> endingBox = new ArrayList<Integer>();

    public void paint(Graphics g) {
        int randNum = 0;
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        addKeyListener(this);
        if (TimerCheck) {
            backgroundTimer += 1;
        }
//---------------------------------------Red Flash Background----------------------------------------
        if (MusicDelay) {
            if (timer % 1100 == 0) {
                backgroundCheck = !backgroundCheck;
                if (!backgroundCheck) {
                    MusicDelay = false;
                    TimerCheck = true;
                }
            }
        } else if (backgroundTimer % 200 == 0) {
            backgroundCheck = !backgroundCheck;
        }
        if (backgroundCheck) {
            g.setColor(Color.black);
            g.fillRect(0, 0, 500, 700);
            color = 200;
        } else {
            g.setColor(new Color(color, 0, 0));
            g.fillRect(0, 0, 500, 700);
            if (color != 0) {
                color -= 1;
            }
        }
//------------------------------------------moving circles (key)----------------------------------------------------------
        g.setColor(Color.blue);
        g.fillOval(xBlue, yBlue, circleDiameter, circleDiameter);
        g.setColor(Color.red);
        g.fillOval(xRed, yRed, circleDiameter, circleDiameter);
        if (KeyPressedRight) {
            RightRedBallMoveX();
            RightRedBallMoveY();
        }
        if (KeyPressedLeft) {
            LeftBlueBallMoveX();
            LeftBlueBallMoveY();
        }
//-------------------------------assign boxs value and save in array------------------------------------
        timer += 1;
        if (coordinateY.size() == 0 || timer % 200 == 0) {
            randNum = randomBox();
            numberBox.add(randNum);
            coordinateY.add(-25);
            // point +=1;
            if (randNum >= 1 && randNum <= 4) {
                TrueFalseBox.add(true);
            }
            if (randNum == 1) {
                coordinateX.add(175);
                lengthBox.add(125);
                heightBox.add(25);
            } else if (randNum == 2) {
                coordinateX.add(0);
                lengthBox.add(235);
                heightBox.add(25);
            } else if (randNum == 3) {
                coordinateX.add(265);
                lengthBox.add(235);
                heightBox.add(25);
            } else if (randNum == 4) {
                if (randomBox4() == 2) {
                    coordinateX.add(135);
                } else {
                    coordinateX.add(250);
                }
                lengthBox.add(115);
                heightBox.add(23);
            } else if (randNum == 5) {
                if (randomBox4() == 2) {
                    coordinateX.add(135);
                    TrueFalseBox.add(true);
                } else {
                    coordinateX.add(240);
                    TrueFalseBox.add(false);
                }
                lengthBox.add(125);
                heightBox.add(25);
            }
        }
//--------------------------------------------------draw the box--------------------------------------------------------
        g.setColor(Color.white);
        for (int i = 0; i < numberBox.size(); i++) {
            g.fillRect(coordinateX.get(i), coordinateY.get(i), lengthBox.get(i), heightBox.get(i));
            coordinateY.set(i, coordinateY.get(i) + 1);

            //condition for changing direction of rotation - Box4
            if (numberBox.get(i) == 4 && TrueFalseBox.get(i)) {
                coordinateX.set(i, coordinateX.get(i) + 1);
                coordinateY.set(i, coordinateY.get(i) - 1);
                lengthBox.set(i, lengthBox.get(i) - 2);
                heightBox.set(i, heightBox.get(i) + 2);
            } else if (numberBox.get(i) == 4 && !TrueFalseBox.get(i)) {
                coordinateX.set(i, coordinateX.get(i) - 1);
                coordinateY.set(i, coordinateY.get(i) + 1);
                lengthBox.set(i, lengthBox.get(i) + 2);
                heightBox.set(i, heightBox.get(i) - 2);
            }
            if (numberBox.get(i) == 4 && lengthBox.get(i) == 23) {
                TrueFalseBox.set(i, !TrueFalseBox.get(i));
            } else if (numberBox.get(i) == 4 && heightBox.get(i) == 23) {
                TrueFalseBox.set(i, !TrueFalseBox.get(i));
            }

            //condition for moveing the block right and left - Box5
            if (numberBox.get(i) == 5 && TrueFalseBox.get(i)) {
                coordinateX.set(i, coordinateX.get(i) + 1);
            } else if (numberBox.get(i) == 5 && !TrueFalseBox.get(i)) {
                coordinateX.set(i, coordinateX.get(i) - 1);
            }
            if (numberBox.get(i) == 5 && coordinateX.get(i) + lengthBox.get(i) == 500) {
                TrueFalseBox.set(i, !TrueFalseBox.get(i));
            } else if (numberBox.get(i) == 5 && coordinateX.get(i) == 0) {
                TrueFalseBox.set(i, !TrueFalseBox.get(i));
            }

        }
//-------------------------------------------------- remove box-----------------------------------------------
        if (coordinateY.get(0) >= 700) {
            coordinateY.remove(0);
            numberBox.remove(0);
            coordinateX.remove(0);
            lengthBox.remove(0);
            heightBox.remove(0);
            TrueFalseBox.remove(0);
            point += 1;
            gamePoint += 5;
        }
        // collision
        collisionDetection(xRed, yRed, xBlue, yBlue);
        // point label
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.ITALIC, 25));
        g.drawString(Integer.toString(point), 230, 50);
        // String label
        if (point == 0) {
            g.setColor(new Color(255 - colorTimer / 2, 0, 0));
            g.setFont(new Font("Serif", Font.ITALIC, 40));
            g.drawString("HIDE AND SEEK", StringHSX, StringHSY);
            g.drawString("HERE I COME", StringHSX + 15, StringHSY + 50);
            StringHSY += 1;
            if (colorTimer <= 510) {
                colorTimer += 1;
            }
        } else if (point == 1) {
            StringHSX = 90;
            StringHSY = 0;
            colorTimer = 0;
        }
        if (point > 50 && point < 70) {
            g.setColor(new Color(255 - colorTimer / 2, 0, 0));
            g.drawString("I CAN HEAR YOUR FOOT STEPS", StringHSX, StringHSY);
            StringHSY += 1;
            if (colorTimer <= 510) {
                colorTimer += 1;
            }
        }
    }

    public static void GameOver() {
        DirectionX = 0;
        DirectionY = 0;
        speed = 1;
        circleDiameter = 30;
        timer = 0;
        backgroundTimer = 0;
        xBlue = 125;
        yBlue = 500;
        xRed = 325;
        yRed = 500;
        color = 200;
        point = 0;
        StringHSX = 110;
        StringHSY = 100;
        colorTimer = 0;
        delay = 4;
        endingCircle = 0;
        endingColor = 0;
        gamePoint = 0;
        KeyPressedRight = false;
        KeyPressedLeft = false;
        backgroundCheck = false;
        MusicDelay = true;
        TimerCheck = false;
        numberBox = new ArrayList<Integer>();
        coordinateY = new ArrayList<Integer>();
        coordinateX = new ArrayList<Integer>();
        lengthBox = new ArrayList<Integer>();
        heightBox = new ArrayList<Integer>();
        TrueFalseBox = new ArrayList<Boolean>();
        endingBox = new ArrayList<Integer>();
    }

    public void collisionDetection(int xRed, int yRed, int xBlue, int yBlue) {
        int CenterRedCircleX = xRed + circleDiameter / 2, CenterRedCircleY = yRed + circleDiameter / 2, CenterBlueCircleX = xBlue + circleDiameter / 2, CenterBlueCircleY = yBlue + circleDiameter / 2;
        int LengthBox, CoordinateBoxY, CoordinateBoxX, HeightBox;
        for (int i = 0; i < numberBox.size(); i++) {
            HeightBox = heightBox.get(i) + 2 * (circleDiameter / 2);
            LengthBox = lengthBox.get(i) + 2 * (circleDiameter / 2);
            CoordinateBoxY = coordinateY.get(i) - circleDiameter / 2;
            CoordinateBoxX = coordinateX.get(i) - circleDiameter / 2;

            if (CenterRedCircleY >= CoordinateBoxY && CenterRedCircleY <= (CoordinateBoxY + HeightBox) && CoordinateBoxX < CenterRedCircleX && CenterRedCircleX < (CoordinateBoxX + LengthBox)) {
                gameOver = true;
            } else if (CenterRedCircleX >= CoordinateBoxX && CenterRedCircleX <= (CoordinateBoxX + LengthBox) && CoordinateBoxY < CenterRedCircleY && CenterRedCircleY < (CoordinateBoxY + HeightBox)) {
                gameOver = true;
            }
            //BLUE CIRCLE CHECK
            if (CenterBlueCircleY >= CoordinateBoxY && CenterBlueCircleY <= (CoordinateBoxY + HeightBox) && CoordinateBoxX < CenterBlueCircleX && CenterBlueCircleX < (CoordinateBoxX + LengthBox)) {
                gameOver = true;
            } else if (CenterBlueCircleX >= CoordinateBoxX && CenterBlueCircleX <= (CoordinateBoxX + LengthBox) && CoordinateBoxY < CenterBlueCircleY && CenterBlueCircleY < (CoordinateBoxY + HeightBox)) {
                gameOver = true;
            }

        }

    }

    public int randomBox() {
        return (int) (Math.random() * 5 + 1);
    }

    public int randomBox4() {
        return (int) (Math.random() * 2 + 1);
    }

    //methods for blue and red ball moving        
    public void RightRedBallMoveX() {
        if (xRed == 125) {
            DirectionX = 1;
        }
        if (xRed == 325) {
            DirectionX = 0;
        }
        if (DirectionX == 0) {
            xRed -= speed;
            xBlue += speed;
        }
        if (DirectionX == 1) {
            xRed += speed;
            xBlue -= speed;
        }
    }

    public void RightRedBallMoveY() {
        if (yRed == 600) {
            DirectionY = 1;
        }
        if (yRed == 400) {
            DirectionY = 0;
        }
        if (DirectionY == 0) {
            yRed += speed;
            yBlue -= speed;
        }
        if (DirectionY == 1) {
            yRed -= speed;
            yBlue += speed;
        }
    }

    public void LeftBlueBallMoveX() {
        if (xRed == 125) {
            DirectionX = 0;
        }
        if (xRed == 325) {
            DirectionX = 1;
        }
        if (DirectionX == 0) {
            xRed += speed;
            xBlue -= speed;
        }
        if (DirectionX == 1) {
            xRed -= speed;
            xBlue += speed;
        }
    }

    public void LeftBlueBallMoveY() {
        if (yRed == 600) {
            DirectionY = 0;
        }
        if (yRed == 400) {
            DirectionY = 1;
        }
        if (DirectionY == 0) {
            yRed -= speed;
            yBlue += speed;
        }
        if (DirectionY == 1) {
            yRed += speed;
            yBlue -= speed;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            KeyPressedRight = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            KeyPressedLeft = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            KeyPressedRight = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            KeyPressedLeft = false;
        }
    }
}
