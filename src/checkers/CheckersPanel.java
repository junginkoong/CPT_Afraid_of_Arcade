
package checkers;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/*
CheckersPanel.java (For Checkers)

Description: Takes logic from the logic class, and draws it on a JPanel

Name: Antony Girgis

Date Created: 28 May 2016

Last Modified: 29 May 2016
 */


public class CheckersPanel extends JPanel {
//Uses paintcomponent to draw on the JPanel
    @Override
    protected void paintComponent(Graphics g) {
//Allows me to directly access variables from my checkers class. Since creating
//an object of my checkers class would run the game and doesn't work properly.
        Checkers checkers = Checkers.checkers;
        super.paintComponent(g);
//Fills the background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, checkers.jframe.getWidth(), checkers.jframe.getHeight());
//Draws the board and the pieces on it accordingly        
        for (int i = 0; i < 8; i++) {
            for (int z = 0; z < 8; z++) {
                if (checkers.board[i][z] == 0) {
                    g.setColor(new Color(133, 66, 12));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                } else if (checkers.board[i][z] == 1) {
                    g.setColor(new Color(237, 203, 173));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                } else if (checkers.board[i][z] == 2) {
                    g.setColor(new Color(237, 203, 173));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.BLACK);
                    g.fillOval(30 + z * 80, 30 + i * 80, 80, 80);
                } else if (checkers.board[i][z] == 3) {
                    g.setColor(new Color(237, 203, 173));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.WHITE);
                    g.fillOval(30 + z * 80, 30 + i * 80, 80, 80);
                } else if (checkers.board[i][z] == 4) {
                    g.setColor(new Color(237, 203, 173));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.BLACK);
                    g.fillOval(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.WHITE);
                    g.drawOval(30 + z * 80 + 10, 30 + i * 80 + 10, 60, 60);
                    g.drawOval(30 + z * 80 + 20, 30 + i * 80 + 20, 40, 40);
                    g.drawOval(30 + z * 80 + 30, 30 + i * 80 + 30, 20, 20);
                } else if (checkers.board[i][z] == 5) {
                    g.setColor(new Color(237, 203, 173));
                    g.fillRect(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.WHITE);
                    g.fillOval(30 + z * 80, 30 + i * 80, 80, 80);
                    g.setColor(Color.BLACK);
                    g.drawOval(30 + z * 80 + 10, 30 + i * 80 + 10, 60, 60);
                    g.drawOval(30 + z * 80 + 20, 30 + i * 80 + 20, 40, 40);
                    g.drawOval(30 + z * 80 + 30, 30 + i * 80 + 30, 20, 20);

                }

            }
        }
//Draws the piece you pick up on your mouse's x and y coordinates
        if (checkers.pickedUpBlack) {
            g.setColor(Color.BLACK);
            g.fillOval(checkers.mouseX - 40, checkers.mouseY - 60, 80, 80);
        }
        if (checkers.pickedUpWhite) {
            g.setColor(Color.WHITE);
            g.fillOval(checkers.mouseX - 40, checkers.mouseY - 60, 80, 80);
        }
        if (checkers.pickedUpBlackKing) {
            g.setColor(Color.BLACK);
            g.fillOval(checkers.mouseX - 40, checkers.mouseY - 60, 80, 80);
            g.setColor(Color.WHITE);
            g.drawOval(checkers.mouseX - 30, checkers.mouseY - 50, 60, 60);
            g.drawOval(checkers.mouseX - 20, checkers.mouseY - 40, 40, 40);
            g.drawOval(checkers.mouseX - 10, checkers.mouseY - 30, 20, 20);
        }
        if (checkers.pickedUpWhiteKing) {
            g.setColor(Color.WHITE);
            g.fillOval(checkers.mouseX - 40, checkers.mouseY - 60, 80, 80);
            g.setColor(Color.BLACK);
            g.drawOval(checkers.mouseX - 30, checkers.mouseY - 50, 60, 60);
            g.drawOval(checkers.mouseX - 20, checkers.mouseY - 40, 40, 40);
            g.drawOval(checkers.mouseX - 10, checkers.mouseY - 30, 20, 20);
        }
        Font font = new Font("Arial", Font.PLAIN, 20);
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.fillRect (checkers.jframe.getWidth()-150,0,150,30);
        g.setColor(Color.WHITE);
        g.drawString("SAVE & EXIT",checkers.jframe.getWidth()-135 , 22);
    }

}
