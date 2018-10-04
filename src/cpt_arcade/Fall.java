package cpt_arcade;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JLabel;

public class Fall extends JLabel implements KeyListener {

    static int ScreenLength = 700, ScreenHeight = 700;
    static int timer = 0, UserY = 300, UserX = 240, sizeOfUser = 20, TheFallTimer = 0, delay = 10, gamePoint = 0;
    static boolean startGame = false, removePanel = false, movementRight = false, movementLeft = false, TheFall = false, gameOver = false;
    static ArrayList<Fall_Panel> panel = new ArrayList<Fall_Panel>();
    static ArrayList<Integer> panelY = new ArrayList<Integer>();

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.red);
        g.fillRect(0, 0, ScreenLength, ScreenHeight);
        timer += 1;
        if (timer % 100 == 0) {
            panel.add(new Fall_Panel(((int) (Math.random() * 660 + 1)), ScreenHeight, 40, 20));
            panelY.add(ScreenHeight);
        }
        for (int i = 0; i < panelY.size(); i++) {
            g.setColor(Color.black);
            g.fillRect(0, panelY.get(i), ScreenLength, 20);
            panelY.set(i, panelY.get(i) - 1);
            if (panelY.get(i) == -20) {
                removePanel = true;
            }
        }
        for (Fall_Panel num : panel) {
            g.setColor(Color.red);
            g.fillRect(num.getBoxX(), num.getBoxY(), num.getBoxLength(), num.getBoxHeight());
            num.setBoxY(num.getBoxY() - 1);
        }
        if (removePanel) {
            panel.remove(0);
            panelY.remove(0);
            removePanel = false;
            gamePoint += 5;
        }
        if (panelY.size() == 7) {
            startGame = true;
        }
        if (startGame) {
            g.setColor(Color.blue);
            g.fillRect(UserX, (UserY - sizeOfUser), sizeOfUser, sizeOfUser);
            UserY -= 1;
            if (movementRight) {
                movementRight();
            }
            if (movementLeft) {
                movementLeft();
            }
            //collision check
            TheFallCheck();
            if (TheFall) {
                TheFall();
            }
        }
        if (delay > 1 && timer % 700 == 0) {
            delay -= 1;
        }
        TheEnd();
    }

    public static void GameOver() {
        int ScreenLength = 700;
        ScreenHeight = 700;
        int timer = 0;
        UserY = 300;
        UserX = 240;
        sizeOfUser = 20;
        TheFallTimer = 0;
        delay = 10;
        gamePoint = 0;
        startGame = false;
        removePanel = false;
        movementRight = false;
        movementLeft = false;
        TheFall = false;
        gameOver = false;
        panel = new ArrayList<Fall_Panel>();
        panelY = new ArrayList<Integer>();
    }

    public static void TheEnd() {
        if (UserY == 0) {
            gameOver = true;
        }
    }

    public static void TheFall() {
        UserY += 4;
        TheFallTimer += 1;
        if (TheFallTimer == 25) {
            TheFallTimer = 0;
            TheFall = false;
        }
    }

    public static void TheFallCheck() {
        for (Fall_Panel num : panel) {
            if (num.getBoxY() == UserY && UserX > num.getBoxX() && (UserX + sizeOfUser) < (num.getBoxX() + num.getBoxLength())) {
                TheFall = true;
            }
        }
    }

    public static void movementRight() {
        if ((UserX + sizeOfUser) < ScreenLength) {
            UserX += 3;
        }
    }

    public static void movementLeft() {
        if (UserX > 0) {
            UserX -= 3;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movementRight = true;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movementLeft = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            movementRight = false;
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            movementLeft = false;
        }
    }
}
