/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerstack;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author AntonysMac
 */
public class TowerPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        TowerStack towerStack = TowerStack.towerStack;
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, towerStack.jframe.getWidth(), towerStack.jframe.getHeight());
        for (int i = 0; i < towerStack.blocks.size(); i++) {
            g.setColor(Color.WHITE);
            g.fillRect(towerStack.blocks.get(i).getX(), towerStack.blocks.get(i).getY(), towerStack.blocks.get(i).getWidth(), towerStack.blocks.get(i).getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(towerStack.blocks.get(i).getX(), towerStack.blocks.get(i).getY(), towerStack.blocks.get(i).getWidth(), towerStack.blocks.get(i).getHeight());
        }
        for (int i = 0; i < towerStack.dropBlocks.size(); i++) {
            g.setColor(Color.WHITE);
            g.fillRect(towerStack.dropBlocks.get(i).getX(), towerStack.dropBlocks.get(i).getY(), towerStack.dropBlocks.get(i).getWidth(), towerStack.dropBlocks.get(i).getHeight());
            g.setColor(Color.BLACK);
            g.drawRect(towerStack.dropBlocks.get(i).getX(), towerStack.dropBlocks.get(i).getY(), towerStack.dropBlocks.get(i).getWidth(), towerStack.dropBlocks.get(i).getHeight());
        }
        String text = "" + towerStack.currentScore;
        String text2 = "Best: " + towerStack.gamePoint;
        Font font = new Font("Arial", Font.PLAIN, 30);
        g.setFont(font);
        int stringWidth = g.getFontMetrics().stringWidth(text);
        g.setColor(Color.WHITE);
        g.drawString(text, towerStack.jframe.getWidth() / 2 - stringWidth / 2, 30);
        g.drawString(text2, 20, 30);
        g.fillRect(towerStack.jframe.getWidth() - 220, 0, 220, 40);
        g.setColor(Color.BLACK);
        g.drawString("SAVE & EXIT", towerStack.jframe.getWidth() - 200, 30);
        if (towerStack.instructions) {
            String text3 = "Spacebar";
            int stringWidth3 = g.getFontMetrics().stringWidth(text3);
            g.setColor(Color.BLACK);
            g.drawString(text3, towerStack.jframe.getWidth() / 2 - stringWidth3 / 2,towerStack.jframe.getHeight()-50 );

        }
    }

}
