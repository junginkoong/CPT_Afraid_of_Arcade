/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author AntonysMac
 */
public class DoodlePanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        DoodleJump doodleJump = DoodleJump.doodleJump;
        Doodle doodle = doodleJump.player;
        super.paintComponent(g);
        if (!doodleJump.pause && !doodleJump.endGame) {
            for (int i = 0; i < doodleJump.platforms.size(); i++) {
                if (doodleJump.platforms.get(i).getType() <= 4) {
                    g.setColor(new Color(255, 140, 140));
                } else if (doodleJump.platforms.get(i).getType() > 4 && doodleJump.platforms.get(i).getType() <= 9) {
                    g.setColor(new Color(251, 140, 255));
                } else if (doodleJump.platforms.get(i).getType() > 9 && doodleJump.platforms.get(i).getType() <= 16) {
                    g.setColor(new Color(255, (int) (255 - 2.5 * doodleJump.platforms.get(i).getColorChanger()), 0));
                } else if (doodleJump.platforms.get(i).getType() == 17) {
                    g.setColor(Color.BLUE);
                } else {
                    g.setColor(new Color(190, 140, 255));
                }
                g.fillRoundRect(doodleJump.platforms.get(i).getLocation().x, doodleJump.platforms.get(i).getLocation().y, doodleJump.platforms.get(i).getSize() * 6, doodleJump.platforms.get(i).getSize(), 10, 10);
            }
            for (int i = 0; i < doodleJump.breakablePlatforms.size(); i++) {
                g.setColor(new Color(168, 123, 123));
                double angle = Math.PI / 4;
                int shift = (int) (doodleJump.breakablePlatforms.get(i).getSize() * 3.25);
                int x = doodleJump.breakablePlatforms.get(i).getLocation().x;
                int y = doodleJump.breakablePlatforms.get(i).getLocation().y;
                int size = doodleJump.breakablePlatforms.get(i).getSize();
                int[] LeftDiagonalsX;
                int[] LeftDiagonalsY;
                int[] RightDiagonalsX;
                int[] RightDiagonalsY;

                if (doodleJump.breakablePlatforms.get(i).isSnap()) {
                    LeftDiagonalsX = new int[]{x, x + (int) (size * Math.cos(angle)), x + (int) (size * 2.75 * Math.cos(angle)) + (int) (size * Math.cos(angle)), x + (int) (size * 2.75 * Math.cos(angle))};
                    LeftDiagonalsY = new int[]{y, y - (int) (size * Math.sin(angle)), y + (int) (size * 2.75 * Math.sin(angle)) - (int) (size * Math.sin(angle)), y + (int) (size * 2.75 * Math.sin(angle))};
                    RightDiagonalsX = new int[]{x + shift, x + shift + (int) (size * Math.cos(angle)), x + shift + (int) (size * 2.75 * Math.cos(angle)) + (int) (size * Math.cos(angle)), x + shift + (int) (size * 2.75 * Math.cos(angle))};
                    RightDiagonalsY = new int[]{y + (int) (size * 2.75 * Math.sin(angle)) - (int) (size * Math.sin(angle)), y + (int) (size * 2.75 * Math.sin(angle)), y, y - (int) (size * Math.sin(angle))};

                    g.fillPolygon(LeftDiagonalsX, LeftDiagonalsY, 4);
                    g.fillPolygon(RightDiagonalsX, RightDiagonalsY, 4);
                } else {
                    g.fillRect(doodleJump.breakablePlatforms.get(i).getLocation().x, doodleJump.breakablePlatforms.get(i).getLocation().y, (int) (doodleJump.breakablePlatforms.get(i).getSize() * 2.75), doodleJump.breakablePlatforms.get(i).getSize());
                    g.fillRect(doodleJump.breakablePlatforms.get(i).getLocation().x + (int) (doodleJump.breakablePlatforms.get(i).getSize() * 3.25), doodleJump.breakablePlatforms.get(i).getLocation().y, (int) (doodleJump.breakablePlatforms.get(i).getSize() * 2.75), doodleJump.breakablePlatforms.get(i).getSize());
                }
            }

            g.setColor(Color.GRAY);
            g.fillRect(doodle.getLocation().x, doodle.getLocation().y, doodle.getSize(), doodle.getSize());
            String text;
            if (doodleJump.gamePoint/1000<0){
               text = "Score: 0";
            }
            else{
            text = "Score: " + doodleJump.gamePoint / 1000;
            }
            Font font = new Font("Luminari", Font.PLAIN, 30/DoodleJump.SCALE);
            g.setFont(font);
            g.setColor(Color.black);
            g.drawString(text, 20, doodleJump.jframe.getHeight() - 30);
        }
        if (doodleJump.pause) {
            String text = "Paused";
            Font font = new Font("Arial", Font.PLAIN, 30/DoodleJump.SCALE);
            g.setFont(font);
            int stringWidth = g.getFontMetrics().stringWidth(text);
            g.setColor(Color.black);
            g.drawString(text, doodleJump.jframe.getWidth() / 2 - stringWidth / 2, doodleJump.jframe.getHeight() / 2);
        }
        if (doodleJump.endGame) {
            String text = "Game Over";
            String text2= "Score: " + doodleJump.gamePoint / 1000;
            String text3 = "Press Space to Exit";
            Font font = new Font("Arial", Font.PLAIN, 30/DoodleJump.SCALE);
            g.setFont(font);
            int stringWidth = g.getFontMetrics().stringWidth(text);
            int stringWidth2 = g.getFontMetrics().stringWidth(text2);
            int stringWidth3 = g.getFontMetrics().stringWidth(text3);
            g.setColor(Color.black);
            g.drawString(text, doodleJump.jframe.getWidth() / 2 - stringWidth / 2, doodleJump.jframe.getHeight() / 2 - 50/DoodleJump.SCALE);
            g.drawString(text2, doodleJump.jframe.getWidth() / 2 - stringWidth2 / 2, doodleJump.jframe.getHeight() / 2);
            g.drawString(text3, doodleJump.jframe.getWidth() / 2 - stringWidth3 / 2, doodleJump.jframe.getHeight() / 2 + 50/DoodleJump.SCALE);
        }
    }

}
