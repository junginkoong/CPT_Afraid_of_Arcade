package cpt_arcade;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ArcadeCharacter extends ArcadeMap implements KeyListener {

    public static int characterX = 624, characterY = 576, speed = 8, BlockH = 0, BlockV = 0, gameNumber = 0, gamePoint = 0, gameCoin = 0;
    public static boolean moveRight = false, moveLeft = false, moveUp = false, moveDown = false, checkNumber = false, runGame = false, CharacterStill = true, CharacterFootStep = false, coinConversion = false, convert = true, play = true;
    public static String CharacterMove = "up";

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        // character momvement
        if (CharacterMove.equals("up") && CharacterStill) {
            g2d.drawImage(CharacterUpStill.getImage(), characterX, characterY, this);
        } else if (CharacterMove.equals("down") && CharacterStill) {
            g2d.drawImage(CharacterDownStill.getImage(), characterX, characterY, this);
        } else if (CharacterMove.equals("right") && CharacterStill) {
            g2d.drawImage(CharacterRightStill.getImage(), characterX, characterY, this);
        } else if (CharacterMove.equals("left") && CharacterStill) {
            g2d.drawImage(CharacterLeftStill.getImage(), characterX, characterY, this);
        } else if (CharacterMove.equals("up") && !CharacterStill && CharacterFootStep) {
            g2d.drawImage(CharacterUpRightLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("up") && !CharacterStill && !CharacterFootStep) {
            g2d.drawImage(CharacterUpLeftLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("down") && !CharacterStill && CharacterFootStep) {
            g2d.drawImage(CharacterDownRightLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("down") && !CharacterStill && !CharacterFootStep) {
            g2d.drawImage(CharacterDownLeftLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("right") && !CharacterStill && CharacterFootStep) {
            g2d.drawImage(CharacterRightRightLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("right") && !CharacterStill && !CharacterFootStep) {
            g2d.drawImage(CharacterRightLeftLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("left") && !CharacterStill && CharacterFootStep) {
            g2d.drawImage(CharacterLeftRightLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        } else if (CharacterMove.equals("left") && !CharacterStill && !CharacterFootStep) {
            g2d.drawImage(CharacterLeftLeftLeg.getImage(), characterX, characterY, this);
            CharacterFootStep = !CharacterFootStep;
        }
        // collision
        collision();

        if (moveRight) {
            moveRight();
        } else if (moveLeft) {
            moveLeft();
        } else if (moveUp) {
            moveUp();
        } else if (moveDown) {
            moveDown();
        }

        if (BlockH == 16) {
            mapCollision[characterLocationY][characterLocationX] = 0;
            characterLocationX += 1;
            mapCollision[characterLocationY][characterLocationX] = 2;
            BlockH = 0;
        }
        if (BlockH == -16) {
            mapCollision[characterLocationY][characterLocationX] = 0;
            characterLocationX -= 1;
            mapCollision[characterLocationY][characterLocationX] = 2;
            BlockH = 0;
        }
        if (BlockV == 16) {
            mapCollision[characterLocationY][characterLocationX] = 0;
            characterLocationY += 1;
            mapCollision[characterLocationY][characterLocationX] = 2;
            BlockV = 0;
        }
        if (BlockV == -16) {
            mapCollision[characterLocationY][characterLocationX] = 0;
            characterLocationY -= 1;
            mapCollision[characterLocationY][characterLocationX] = 2;
            BlockV = 0;
        }
        if (checkNumber) {
            checkGame();
            checkNumber = false;
            if (gameNumber > 3) {
                runGame = true;
            }
        }
        //draw game point here
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Game Point: " + gamePoint, 1000, 50);
        g.drawString("Game Coin: " + gameCoin, 100, 50);
    }

    public static void checkGame() {
        for (int i = -1; i < 3; i++) {
            for (int z = -1; z < 3; z++) {
                if (mapCollision[characterLocationY + z][characterLocationX + i] > 2 && mapCollision[characterLocationY + z][characterLocationX + i] != 50) {
                    if (play) {
                        gameNumber = mapCollision[characterLocationY + z][characterLocationX + i];
                        if (gameNumber != 10) {
                            gameCoin -= 1;
                        }
                        if (gameCoin < 0) {
                            gameCoin += 1;
                            gameNumber = 0;
                        }
                        play = false;
                    }
                }
                if (mapCollision[characterLocationY + z][characterLocationX + i] == 50) {
                    if (convert && gamePoint >= 50) {
                        gameCoin += 1;
                        gamePoint -= 50;
                        convert = false;
                    }
                }
            }
        }
    }

    public static void collision() {
        if (moveRight) {
            if (mapCollision[characterLocationY][characterLocationX + 2] >= 1 || mapCollision[characterLocationY + 1][characterLocationX + 2] >= 1) {
                moveRight = false;
            }
        }
        if (moveLeft) {
            if (mapCollision[characterLocationY][characterLocationX - 1] >= 1 || mapCollision[characterLocationY + 1][characterLocationX - 1] >= 1) {
                moveLeft = false;
            }
        }
        if (moveUp) {
            if (mapCollision[characterLocationY - 1][characterLocationX] >= 1 || mapCollision[characterLocationY - 1][characterLocationX + 1] >= 1) {
                moveUp = false;
            }
        }
        if (moveDown) {
            if (mapCollision[characterLocationY + 2][characterLocationX] >= 1 || mapCollision[characterLocationY + 2][characterLocationX + 1] >= 1) {
                moveDown = false;
            }
        }
    }

    public static void moveRight() {
        characterX += speed;
        BlockH += speed;
    }

    public static void moveLeft() {
        characterX -= speed;
        BlockH -= speed;
    }

    public static void moveUp() {
        characterY -= speed;
        BlockV -= speed;
    }

    public static void moveDown() {
        characterY += speed;
        BlockV += speed;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_G) {
            if (gameCoin == 0) {
                gameCoin = 1;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = true;
            CharacterMove = "right";
            CharacterStill = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = true;
            CharacterMove = "left";
            CharacterStill = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp = true;
            CharacterMove = "up";
            CharacterStill = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown = true;
            CharacterMove = "down";
            CharacterStill = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            checkNumber = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight = false;
            CharacterStill = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft = false;
            CharacterStill = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            moveUp = false;
            CharacterStill = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            moveDown = false;
            CharacterStill = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            convert = true;
            play = true;
        }
    }

}
