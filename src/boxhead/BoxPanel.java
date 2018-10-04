/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxhead;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author AntonysMac
 */
public class BoxPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        BoxHead boxHead = BoxHead.boxHead;
        super.paintComponent(g);

            g.setColor(new Color(226,201,255));
            g.fillRect(0, 0, boxHead.jframe.getWidth(), boxHead.jframe.getHeight());
            
        for (int f = 0; f < boxHead.players.size(); f++) {
            g.setColor(Color.YELLOW);
            g.fillRect(boxHead.players.get(f).getX() - boxHead.players.get(f).getSize() / 2, boxHead.players.get(f).getY() - boxHead.players.get(f).getSize() / 2, boxHead.players.get(f).getSize(), boxHead.players.get(f).getSize());
            g.setColor(Color.RED);
            g.fillRect(10, 10 + 25 * f, 20 * boxHead.characterHealth, 20);
            g.setColor(Color.GREEN);
            g.fillRect(10, 10 + 25 * f, 20 * boxHead.players.get(f).health, 20);
            
             g.setColor(Color.BLACK);
            for (int i = 0; i < boxHead.bullets.size(); i++) {
                g.fillOval(boxHead.bullets.get(i).getX(), boxHead.bullets.get(i).getY(), boxHead.bullets.get(i).getSize(), boxHead.bullets.get(i).getSize());
            }
            g.setColor(Color.RED);
            for (int i = 0; i < boxHead.enemies.size(); i++) {
                g.fillRect(boxHead.enemies.get(i).getX() - boxHead.players.get(f).getSize() / 2, boxHead.enemies.get(i).getY() - boxHead.players.get(f).getSize() / 2, boxHead.enemies.get(i).getSize(), boxHead.enemies.get(i).getSize());
            }
        }
        String text;
        text = "Score: " + boxHead.gamePoint;
        Font font = new Font("Luminari", Font.PLAIN, 30);
        g.setFont(font);
        g.setColor(Color.black);
        g.drawString(text, 20, boxHead.jframe.getHeight() - 30);

        if (boxHead.instructions) {
            String text1 = "Player 1";
            String text2 = "Movement: Arrow Keys   Switch Weapons: '<   >  ?'  Shoot:    SPACEBAR  ";
            String text3 = "Player 2";
            String text4 = "Add-Player:  BackSlash     Movement:    WASD     Switch Weapons: '1, 2, 3'   Shoot:   T  ";
            font = new Font("Arial", Font.PLAIN, 30);
            g.setFont(font);
            int stringWidth1 = g.getFontMetrics().stringWidth(text);
            int stringWidth2 = g.getFontMetrics().stringWidth(text2);
            int stringWidth3 = g.getFontMetrics().stringWidth(text3);
            int stringWidth4 = g.getFontMetrics().stringWidth(text4);
            g.setColor(Color.BLACK);
            g.drawString(text1, boxHead.jframe.getWidth() / 2 - stringWidth1 / 2, boxHead.jframe.getHeight() / 2 - 100);
            g.drawString(text2, boxHead.jframe.getWidth() / 2 - stringWidth2 / 2, boxHead.jframe.getHeight() / 2 - 50);
            g.drawString(text3, boxHead.jframe.getWidth() / 2 - stringWidth3 / 2, boxHead.jframe.getHeight() / 2 + 50);
            g.drawString(text4, boxHead.jframe.getWidth() / 2 - stringWidth4 / 2, boxHead.jframe.getHeight() / 2 + 100);
        }

    }

}
