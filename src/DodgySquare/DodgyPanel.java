/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DodgySquare;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author AntonysMac
 */
public class DodgyPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        DodgySquare dodgySquare = DodgySquare.dodgySquare;
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, dodgySquare.jframe.getWidth(), dodgySquare.jframe.getHeight());
        g.setColor(Color.WHITE);
        g.fillRect(dodgySquare.widthLocation, dodgySquare.heightLocation, dodgySquare.gameWidth, dodgySquare.gameHeight);
        g.setColor(Color.red);
        g.fillRect(dodgySquare.player.getX() - dodgySquare.player.getWidth() / 2, dodgySquare.player.getY() - dodgySquare.player.getHeight() / 2, dodgySquare.player.getWidth(), dodgySquare.player.getHeight());
        g.setColor(Color.blue);
        for (int i = 0; i < dodgySquare.squares.size(); i++) {
            g.fillRect(dodgySquare.squares.get(i).getX() - dodgySquare.squares.get(i).getWidth() / 2, dodgySquare.squares.get(i).getY() - dodgySquare.squares.get(i).getHeight() / 2, dodgySquare.squares.get(i).getWidth(), dodgySquare.squares.get(i).getHeight());
        }
        String text = "" + dodgySquare.currentScore / 10 + "." + dodgySquare.currentScore % 10;
        String text2 = "Best: " + dodgySquare.gamePoint / 10 + "." + dodgySquare.gamePoint % 10;
        Font font = new Font("Arial", Font.PLAIN, 30);
        g.setFont(font);
        int stringWidth = g.getFontMetrics().stringWidth(text);
        g.setColor(Color.WHITE);
        g.drawString(text, dodgySquare.jframe.getWidth() / 2 - stringWidth / 2, 30);
        g.drawString(text2, 20, 30);
        g.fillRect (dodgySquare.jframe.getWidth()-220,0,220,40);
        g.setColor(Color.BLACK);
        g.drawString("SAVE & EXIT",dodgySquare.jframe.getWidth()-200 , 30);
        if (dodgySquare.instructions) {
            String text3 = "Click Me!";
            int stringWidth3 = g.getFontMetrics().stringWidth(text3);
            g.setColor(Color.BLACK);
            g.drawString(text3, dodgySquare.jframe.getWidth() / 2 - stringWidth3 / 2,dodgySquare.jframe.getHeight()/2+10 );

        }
    }

}
