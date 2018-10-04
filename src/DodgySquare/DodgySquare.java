/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DodgySquare;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author AntonysMac
 */
public class DodgySquare implements MouseListener, MouseMotionListener, ActionListener, KeyListener {

    public JFrame jframe;

    public Dimension dim;

    public Toolkit toolkit;

    public DodgyPanel renderPanel;

    public static DodgySquare dodgySquare;

    public Timer timer;

    boolean gameOver = false, gameStart = false, gameEnd = false, instructions=true;

    public int gameWidth = 440, gameHeight = 590, widthLocation = 0, heightLocation = 0, mouseX = 0, mouseY = 0, speed = 5, currentScore=0, ticks, gamePoint=0;

    public ArrayList<Square> squares;

    public Square player;

    public DodgySquare() {
        jframe = new JFrame("Dodgy Square");
        jframe.setVisible(true);
        jframe.setResizable(false);
        dim = toolkit.getDefaultToolkit().getScreenSize();
        jframe.setSize(600, dim.height - 100);
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new DodgyPanel());
        timer = new Timer(20, this);
        jframe.addMouseListener(this);
        jframe.addMouseMotionListener(this);
        jframe.addKeyListener(this);
        timer.start();
        widthLocation = jframe.getWidth() / 2 - gameWidth / 2;
        heightLocation = jframe.getHeight() / 2 - gameHeight / 2;
        startGame();
    }

    public void startGame() {
        player = new Square(70, 70, widthLocation + gameWidth / 2, heightLocation + gameHeight / 2, false, false, false, false);
        squares = new ArrayList<>();
        squares.add(new Square(95, 100, widthLocation + 30, heightLocation + 30, false, true, false, true));
        squares.add(new Square(90, 80, widthLocation + gameWidth - 30, heightLocation + 30, false, true, true, false));
        squares.add(new Square(125, 30, widthLocation + gameWidth - 30, heightLocation + gameHeight - 100, true, false, true, false));
        squares.add(new Square(50, 95, widthLocation + 30, heightLocation + gameHeight - 30, true, false, false, true));
        squares.get(0).setX(squares.get(0).getX() + squares.get(0).getWidth() / 2);
        squares.get(1).setX(squares.get(1).getX() - squares.get(1).getWidth() / 2);
        squares.get(2).setX(squares.get(2).getX() - squares.get(2).getWidth() / 2);
        squares.get(3).setX(squares.get(3).getX() + squares.get(3).getWidth() / 2);
        squares.get(0).setY(squares.get(0).getY() + squares.get(0).getHeight() / 2);
        squares.get(1).setY(squares.get(1).getY() + squares.get(1).getHeight() / 2);
        squares.get(2).setY(squares.get(2).getY() - squares.get(2).getHeight() / 2);
        squares.get(3).setY(squares.get(3).getY() - squares.get(3).getHeight() / 2);
        if(currentScore>gamePoint){
            gamePoint=currentScore;
        }
        currentScore=0;
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
        ticks++;
        if (gameStart) {
            if (ticks%2==0){
                currentScore++;
            }
            player.setX(mouseX);
            player.setY(mouseY);
            for (int i = 0; i < squares.size(); i++) {
                if (squares.get(i).isUp()) {
                    squares.get(i).setY(squares.get(i).getY() - speed);
                }
                if (squares.get(i).isDown()) {
                    squares.get(i).setY(squares.get(i).getY() + speed);
                }
                if (squares.get(i).isLeft()) {
                    squares.get(i).setX(squares.get(i).getX() - speed);
                }
                if (squares.get(i).isRight()) {
                    squares.get(i).setX(squares.get(i).getX() + speed);
                }
                if (squares.get(i).getX() - squares.get(i).getWidth() / 2 < widthLocation - 30) {
                    squares.get(i).setLeft(false);
                    squares.get(i).setRight(true);
                }
                if (squares.get(i).getX() + squares.get(i).getWidth() / 2 > widthLocation + gameWidth + 30) {
                    squares.get(i).setLeft(true);
                    squares.get(i).setRight(false);
                }
                if (squares.get(i).getY() - squares.get(i).getHeight() / 2 < heightLocation - 30) {
                    squares.get(i).setUp(false);
                    squares.get(i).setDown(true);
                }
                if (squares.get(i).getY() + squares.get(i).getHeight() / 2 > heightLocation + gameHeight + 30) {
                    squares.get(i).setUp(true);
                    squares.get(i).setDown(false);
                }
                if (player.getX()+player.getWidth()/2>widthLocation+gameWidth||player.getX()-player.getWidth()/2<widthLocation||player.getY()+player.getHeight()/2>heightLocation+gameHeight||player.getY()-player.getHeight()/2<heightLocation||player.isCollided(squares.get(i).getX() - squares.get(i).getWidth() / 2, squares.get(i).getY() - squares.get(i).getHeight() / 2, squares.get(i).getX() + squares.get(i).getWidth() / 2, squares.get(i).getY() + squares.get(i).getHeight() / 2)) {
                    startGame();
                    instructions=true;
                    gameStart = false;
                }
                
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getX() > player.getX() - player.getWidth() / 2 && e.getX() < player.getX() + player.getWidth() / 2) {
            if (e.getY() > player.getY() - player.getHeight() / 2 && e.getY() < player.getY() + player.getHeight() / 2 && !gameStart) {
                gameStart = true;
                instructions=false;
            }
        }
        if (e.getX()>jframe.getWidth()-220&&e.getX()<jframe.getWidth()&&e.getY()<50&&e.getY()>0){
            gameOver=true;
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

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY() - 20;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int i=e.getKeyCode();
        if (i==KeyEvent.VK_G&&squares.size()>1){
            squares.remove(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

}
