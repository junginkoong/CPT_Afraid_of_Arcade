package checkers;
//Imports

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

/*
Checkers.java

Description: A 2 player game of checkers with kings and legal moves, turns, and
double jumps.

Name: Antony Girgis

Date Created: 28 May 2016

Last Modified: 31 May 2016
 */
//Actionlistener: for the timer class
//MouseMotionListener and MouseListener to click on the pieces and drag them
public class Checkers implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

//JFrame to hold the game
    public JFrame jframe;
//To create an object of the game, and to use in the renderPanel Class
    public static Checkers checkers;
//Allows me to add the JPanel to the jframe so we can see the graphics
    public CheckersPanel renderPanel;
//To get the user's screen size so we can center the game
    public Dimension dim;
//same as above
    public Toolkit toolkit;
//Timer class that uses an action listener that contains everything our game does
    public Timer timer = new Timer(20, this);
//Variables speak for themselves here
    int[][] board, legalMoves;

    public int mouseX = 0, mouseY = 0, initialPieceX = 5, initialPieceY = 5, finalPieceX = 0, finalPieceY = 0, gamePoint = 0;

    public boolean pickedUpBlack = false, pickedUpWhite = false, pickedUpBlackKing = false, pickedUpWhiteKing = false, blacksTurn = true, whitesTurn = false, justJumped = false, gameOver = false;

    public Checkers() {
//Initilizes the jframe, sets size, location, adds the MouseListener and the render panel. I do this at the beginning
//of all my games so its nothing new.
        jframe = new JFrame("Checkers");
        jframe.setVisible(true);
        jframe.setSize(700, 700);
        jframe.setResizable(false);
        dim = toolkit.getDefaultToolkit().getScreenSize();
        jframe.setLocation(dim.width / 2 - jframe.getWidth() / 2, dim.height / 2 - jframe.getHeight() / 2);
        jframe.add(renderPanel = new CheckersPanel());
        jframe.addMouseListener(this);
        jframe.addMouseMotionListener(this);
        jframe.addKeyListener(this);
//Creates the board. 0's you can't move there, 3's are white pieces, 1's are empty squares you can move to
//2's are black pieces
//Although the board is 8x8, I made the array 20x20 so you wouldnt get out of bounds exceptions when you drag a
//Piece below or to the right of the board. I didnt allow movement to these squares though, nor did I allow 
//my JPanel to draw the board there.
        board = new int[][]{
            {3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {3, 0, 3, 0, 3, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 2, 0, 2, 0, 2, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};
//creats a 2D array of legal moves that will update every time you press on a piece.
        legalMoves = new int[20][20];
//starts timer
        timer.start();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

//What happens ever tick of time
    @Override
    public void actionPerformed(ActionEvent e) {
//refreshes the JPanel every tick of time.
        renderPanel.repaint();
//Piece removal once clicked (must be within array boundary)        
        if ((pickedUpBlack || pickedUpWhite || pickedUpBlackKing || pickedUpWhiteKing) && initialPieceX < 8 && initialPieceY < 8 && board[initialPieceY][initialPieceX] != 0) {
            board[initialPieceY][initialPieceX] = 1;
        }
//If a black piece or a white piece reach the opposite end, king them
//4 is black king, 5 is white king
        for (int i = 0; i < board.length; i++) {
            if (board[0][i] == 2) {
                board[0][i] = 4;
            }
            if (board[7][i] == 3) {
                board[7][i] = 5;
            }
        }

    }

//Changes the values in the legalMoves array based on direction. If direction is
//up, changes the value to the top right and the top left of the chosen piece to
//legal.If direction is down, changes the value to the bottom right and the bottom 
//left of the chosen piece to legal.
    public void move(String direction) {
        int directionSwitcher = 1;
        boolean condition = initialPieceY > 0;
        if (direction.equalsIgnoreCase("up")) {
            directionSwitcher = -1;
        }
        if (directionSwitcher == 1) {
            condition = initialPieceY < 7;
        }
        if (condition) {
            if (initialPieceX < 7 && board[initialPieceY + directionSwitcher][initialPieceX + 1] == 1) {
                legalMoves[initialPieceY + directionSwitcher][initialPieceX + 1] = 1;
            }
            if (initialPieceX > 0 && board[initialPieceY + directionSwitcher][initialPieceX - 1] == 1) {
                legalMoves[initialPieceY + directionSwitcher][initialPieceX - 1] = 1;
            }
        }
    }
//Takes in the direction of the jump, the piece colour, and whether it's a king
//or not (because king's can jump in 2 more directions than a normal piece), and
//updates the legal moves array accordingly.

    public void jump(String direction, String pieceColour, String pieceType) {
        boolean condition = initialPieceY < 6;
        int directionSwitcher = 1;
        if (direction.equalsIgnoreCase("up")) {
            directionSwitcher = -1;
            condition = initialPieceY > 1;
        }
        int pieceChanger = 0;
        if (pieceColour.equalsIgnoreCase("black")) {
            pieceChanger = 1;
        }
        if (condition) {
            if (initialPieceX < 6 && (board[initialPieceY + 1 * directionSwitcher][initialPieceX + 1] == 2 + pieceChanger || board[initialPieceY + 1 * directionSwitcher][initialPieceX + 1] == 4 + pieceChanger) && board[initialPieceY + 2 * directionSwitcher][initialPieceX + 2] == 1) {
                if (direction.equals("up") && pieceType.equals("king")) {
                    legalMoves[initialPieceY + 2 * directionSwitcher][initialPieceX + 2] = 4;
                } else {
                    legalMoves[initialPieceY + 2 * directionSwitcher][initialPieceX + 2] = 2;
                }
            }
            if (initialPieceX > 1 && (board[initialPieceY + 1 * directionSwitcher][initialPieceX - 1] == 2 + pieceChanger || board[initialPieceY + 1 * directionSwitcher][initialPieceX - 1] == 4 + pieceChanger) && board[initialPieceY + 2 * directionSwitcher][initialPieceX - 2] == 1) {
                if (direction.equals("up") && pieceType.equals("king")) {
                    legalMoves[initialPieceY + 2 * directionSwitcher][initialPieceX - 2] = 5;
                } else {
                    legalMoves[initialPieceY + 2 * directionSwitcher][initialPieceX - 2] = 3;
                }
            }
        }
    }
//Checks for wins 

    public boolean winChecker(int pieceType) {
        for (int i = 0; i < 8; i++) {
            for (int z = 0; z < 8; z++) {
                if (board[i][z] == pieceType) {
                    return false;
                }
            }
        }
        return true;
    }

    public void legalMovesUpdater() {
        //The following lines of code checks for legal moves for each type of piece, and draws adds them 
//to my move array so that a piece is only moved to a legal move spot and not anywhere a person wants.
//When a person selects a piece, that piece's legal moves and jumps are added to an empty 8 by 8 array.
//Once they release the piece, if the x and y coordinate they release it at is a legal move, it puts it there,
//otherwise, it changes the value of the initial spot to the type of piece that was originally there.
//BLACK PIECE MOVES
        if (board[initialPieceY][initialPieceX] == 2) {
            move("up");
            jump("up", "black", "normal");
        }
//WHITE PIECE MOVES
        if (board[initialPieceY][initialPieceX] == 3) {
            move("down");
            jump("down", "white", "normal");
        }
//BLACK KING MOVES
        if (board[initialPieceY][initialPieceX] == 4) {
            move("down");
            jump("down", "black", "king");
            move("up");
            jump("up", "black", "king");
        }
//WHITE KING MOVES
        if (board[initialPieceY][initialPieceX] == 5) {
            move("down");
            jump("down", "white", "king");
            move("up");
            jump("up", "white", "king");
        }
    }

    public boolean doubleJumpChecker() {
        initialPieceY = finalPieceY;
        initialPieceX = finalPieceX;
        legalMovesUpdater();
        for (int i = 0; i < 8; i++) {
            for (int z = 0; z < 8; z++) {

                if (legalMoves[i][z] == 2 || legalMoves[i][z] == 3 || legalMoves[i][z] == 4 || legalMoves[i][z] == 5) {
                    legalMoves = new int[20][20];
                    return true;
                }
            }
        }
        legalMoves = new int[20][20];
        return false;

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }
//What happens when a player selects a piece

    @Override
//What to do when the mouse is pressed
    public void mousePressed(MouseEvent e) {
        if (e.getX()>jframe.getWidth()-150&&e.getX()<jframe.getWidth()&&e.getY()<50&&e.getY()>0){
            gameOver=true;
        }
//Gets where the mouse is pressed and translates the coordinates into values for my 2D array
        initialPieceX = (e.getX() - 30) / 80;
        initialPieceY = (e.getY() - 30) / 80;

        legalMovesUpdater();

//Allows me to get the exact X and Y coordinates of my mouse so it can draw a piece there when I press on a piece
        mouseX = e.getX();
        mouseY = e.getY();
//Checks what type of piece is present in the place I clicked, and changes the boolean accordingly.
//This makes sure I draw the right piece at my mouse's coordinates and that I actually start drawing
//a piece rather than having nothing on my mouse.
        if (board[initialPieceY][initialPieceX] == 2 && blacksTurn) {
            pickedUpBlack = true;
        } else if (board[initialPieceY][initialPieceX] == 3 && whitesTurn) {
            pickedUpWhite = true;
        } else if (board[initialPieceY][initialPieceX] == 4 && blacksTurn) {
            pickedUpBlackKing = true;
        } else if (board[initialPieceY][initialPieceX] == 5 && whitesTurn) {
            pickedUpWhiteKing = true;
        }

    }
//What happens when you release the piece

    @Override
    public void mouseReleased(MouseEvent e) {
//Gets where the mouse is released and translates the coordinates into values for my 2D array
        finalPieceX = (e.getX() - 30) / 80;
        finalPieceY = (e.getY() - 30) / 80;
//If the move was legal, it changes the array value at the place it was released to black
        if (pickedUpBlack && legalMoves[finalPieceY][finalPieceX] != 0) {
            board[finalPieceY][finalPieceX] = 2;
            blacksTurn = false;
            whitesTurn = true;
//If the move was a top-right jump, remove the piece it jumped
            if (legalMoves[finalPieceY][finalPieceX] == 2) {
                board[initialPieceY - 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
//If the move was a top-left jump, remove the piece it jumped
            if (legalMoves[finalPieceY][finalPieceX] == 3) {
                board[initialPieceY - 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
//If the move was  not legal, it changes the original square back to black
        } else if (pickedUpBlack && legalMoves[finalPieceY][finalPieceX] == 0) {
            board[initialPieceY][initialPieceX] = 2;
        }
//If the move was legal, it changes the array value at the place it was released to white
        if (pickedUpWhite && legalMoves[finalPieceY][finalPieceX] != 0) {
            board[finalPieceY][finalPieceX] = 3;
            blacksTurn = true;
            whitesTurn = false;
//If the move was a bottom-right jump, remove the piece it jumped
            if (legalMoves[finalPieceY][finalPieceX] == 2) {
                board[initialPieceY + 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
//If the move was a bottom-left jump, remove the piece it jumped
            if (legalMoves[finalPieceY][finalPieceX] == 3) {
                board[initialPieceY + 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
//If the move was  not legal, it changes the original square back to white
        } else if (pickedUpWhite && legalMoves[finalPieceY][finalPieceX] == 0) {
            board[initialPieceY][initialPieceX] = 3;
        }
//Same code as above but for black king and white king        
        if (pickedUpBlackKing && legalMoves[finalPieceY][finalPieceX] != 0) {
            board[finalPieceY][finalPieceX] = 4;
            blacksTurn = false;
            whitesTurn = true;
            if (legalMoves[finalPieceY][finalPieceX] == 2) {
                board[initialPieceY + 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 3) {
                board[initialPieceY + 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 4) {
                board[initialPieceY - 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 5) {
                board[initialPieceY - 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
        } else if (pickedUpBlackKing && legalMoves[finalPieceY][finalPieceX] == 0) {
            board[initialPieceY][initialPieceX] = 4;
        }
        if (pickedUpWhiteKing && legalMoves[finalPieceY][finalPieceX] != 0) {
            board[finalPieceY][finalPieceX] = 5;
            blacksTurn = true;
            whitesTurn = false;
            if (legalMoves[finalPieceY][finalPieceX] == 2) {
                board[initialPieceY + 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 3) {
                board[initialPieceY + 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 4) {
                board[initialPieceY - 1][initialPieceX + 1] = 1;
                justJumped = true;
            }
            if (legalMoves[finalPieceY][finalPieceX] == 5) {
                board[initialPieceY - 1][initialPieceX - 1] = 1;
                justJumped = true;
            }
        } else if (pickedUpWhiteKing && legalMoves[finalPieceY][finalPieceX] == 0) {
            board[initialPieceY][initialPieceX] = 5;
        }
//releases the piece so it doesn't keep the piece on my mouse
        pickedUpBlack = false;
        pickedUpWhite = false;
        pickedUpBlackKing = false;
        pickedUpWhiteKing = false;
//Empties the move array so another piece can be picked up
        legalMoves = new int[20][20];
//If there arent anymore black pieces, white wins
        if (winChecker(2) && winChecker(4)) {
            System.out.println("White Wins!");
            gameOver = true;
            gamePoint = 100;
        }
//If there arent anymore white pieces, black wins
        if (winChecker(3) && winChecker(5)) {
            System.out.println("Black Wins!");
            gameOver = true;
            gamePoint = 100;
        }
//If a jump just happened and a double jump is possible, either white or black 
//can move
        if (doubleJumpChecker() && justJumped) {
            blacksTurn = true;
            whitesTurn = true;
        }
//Resets the value of whether a jump just occurred
        justJumped = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
//Continually updates the X and Y location of my mouse as I drag the piece so the
//piece can move with my mouse

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int z = e.getKeyCode();
        if (z == KeyEvent.VK_G) {
            for (int i = 0; i < 8; i++) {
                for (int f = 0; f < 8; f++) {
                    if (board[i][f]==3){
                        board[i][f]=5;
                    }
                    if (board[i][f]==2){
                        board[i][f]=4;
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

//Sounds and music
