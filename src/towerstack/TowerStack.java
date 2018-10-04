/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerstack;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author AntonysMac
 */
public class TowerStack implements ActionListener, KeyListener, MouseListener {

    public JFrame jframe;

    public static TowerStack towerStack;

    public TowerPanel renderPanel;

    public Timer timer;

    public Dimension dim;

    public Toolkit toolkit;

    public boolean gameOver = false, instructions = true;

    ArrayList<Block> blocks = new ArrayList<>();

    ArrayList<Block> dropBlocks = new ArrayList<>();

    public int maxHeight = 300, verticalSpeed = 5, currentScore = 0, gamePoint = 0, acceleration = 1;

    public TowerStack() {
        jframe = new JFrame("Tower Stack");
        jframe.setVisible(true);
        jframe.setResizable(false);
        dim = toolkit.getDefaultToolkit().getScreenSize();
        jframe.setSize(900, dim.height - 100);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new TowerPanel());
        timer = new Timer(20, this);
        jframe.addKeyListener(this);
        jframe.addMouseListener(this);
        blocks.add(new Block(0, 0, 700, 100, false, false, false));
        blocks.get(0).setX(jframe.getWidth() / 2 - blocks.get(0).getWidth() / 2);
        blocks.get(0).setY(jframe.getHeight() - blocks.get(0).getHeight());
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
        currentScore = blocks.size();
        for (int i = 0; i < blocks.size(); i++) {
            if (!blocks.get(i).isMoving()) {
                blocks.get(i).setRight(false);
                blocks.get(i).setLeft(false);
            }
            if (blocks.get(i).isRight()) {
                blocks.get(i).setX(blocks.get(i).getX() + blocks.get(i).getSpeed());
            }
            if (blocks.get(i).isLeft()) {
                blocks.get(i).setX(blocks.get(i).getX() - blocks.get(i).getSpeed());
            }
            if (blocks.get(i).getX() < 0) {
                blocks.get(i).setLeft(false);
                blocks.get(i).setRight(true);
            }
            if (blocks.get(i).getX() + blocks.get(i).getWidth() > jframe.getWidth()) {
                blocks.get(i).setLeft(true);
                blocks.get(i).setRight(false);
            }
            if (blocks.get(blocks.size() - 1).getY() < maxHeight) {
                blocks.get(i).setY(blocks.get(i).getY() + verticalSpeed);
            }
            if (blocks.get(blocks.size() - 1).getWidth() < 0) {
                instructions = true;
                if (currentScore > gamePoint) {
                    gamePoint = currentScore;
                }
                blocks = new ArrayList<>();
                blocks.add(new Block(0, 0, 700, 100, false, false, false));
                blocks.get(0).setX(jframe.getWidth() / 2 - blocks.get(0).getWidth() / 2);
                blocks.get(0).setY(jframe.getHeight() - blocks.get(0).getHeight());
            }
        }
        for (int i = 0; i < dropBlocks.size(); i++) {
            dropBlocks.get(i).setY(dropBlocks.get(i).getY() + dropBlocks.get(i).getDownSpeed());
            dropBlocks.get(i).setDownSpeed(dropBlocks.get(i).getDownSpeed() + acceleration);
            if (dropBlocks.get(i).getY() > jframe.getHeight()) {
                dropBlocks.remove(i);
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i = e.getKeyCode();
        if (i == KeyEvent.VK_G && blocks.size() > 1) {
            blocks.get(blocks.size() - 1).setWidth(700);
            blocks.get(blocks.size() - 1).setX(jframe.getWidth() / 2 - blocks.get(0).getWidth() / 2);
            blocks.get(blocks.size() - 2).setWidth(700);
            blocks.get(blocks.size() - 2).setX(jframe.getWidth() / 2 - blocks.get(0).getWidth() / 2);

        }
        if (i == KeyEvent.VK_SPACE) {
            instructions = false;
            if (blocks.get(blocks.size() - 1).isMoving()) {
                blocks.get(blocks.size() - 1).setMoving(false);
            }
            if (blocks.size() > 1 && blocks.get(blocks.size() - 1).getX() < blocks.get(blocks.size() - 2).getX()) {
                dropBlocks.add(new Block(blocks.get(blocks.size() - 1).getX(), blocks.get(blocks.size() - 1).getY(), blocks.get(blocks.size() - 2).getX() - blocks.get(blocks.size() - 1).getX(), 100, false, false, false));
                blocks.get(blocks.size() - 1).setWidth(blocks.get(blocks.size() - 1).getWidth() - (blocks.get(blocks.size() - 2).getX() - blocks.get(blocks.size() - 1).getX()));
                blocks.get(blocks.size() - 1).setX(blocks.get(blocks.size() - 2).getX());

            }
            if (blocks.size() > 1 && blocks.get(blocks.size() - 1).getX() > blocks.get(blocks.size() - 2).getX()) {
                blocks.get(blocks.size() - 1).setWidth(blocks.get(blocks.size() - 1).getWidth() - (blocks.get(blocks.size() - 1).getX() - blocks.get(blocks.size() - 2).getX()));
                dropBlocks.add(new Block(blocks.get(blocks.size() - 1).getX() + blocks.get(blocks.size() - 1).getWidth(), blocks.get(blocks.size() - 1).getY(), blocks.get(blocks.size() - 1).getX() - blocks.get(blocks.size() - 2).getX(), 100, false, false, false));
            }
            blocks.add(new Block(0, blocks.get(blocks.size() - 1).getY() - blocks.get(blocks.size() - 1).getHeight(), blocks.get(blocks.size() - 1).getWidth(), 100, true, false, true));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > jframe.getWidth() - 220 && e.getX() < jframe.getWidth() && e.getY() < 50 && e.getY() > 0) {
            gameOver = true;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
