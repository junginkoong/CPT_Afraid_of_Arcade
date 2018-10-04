/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DodgySquare;

/**
 *
 * @author AntonysMac
 */
public class Square {

    int width, height, x, y;
    boolean up, down, left, right;

    public Square(int width, int height, int x, int y, boolean up, boolean down, boolean left, boolean right) {
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
    }

    public boolean isCollided(int minX, int minY, int maxX, int maxY) {
        if (x + width / 2 > minX && x - width / 2 < maxX && y + height / 2 > minY && y - height / 2 < maxY) {
            return true;
        }
        return false;
    }

    public boolean isDown() {
        return down;
    }

    public boolean isLeft() {
        return left;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

}
