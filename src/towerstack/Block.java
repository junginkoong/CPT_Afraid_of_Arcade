/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package towerstack;

/**
 *
 * @author AntonysMac
 */
public class Block {

    int x, y, width, height, downSpeed = 0, speed = (int) (Math.random() * 5 + 5);
    boolean right, left, moving;

    public Block(int x, int y, int width, int height, boolean right, boolean left, boolean moving) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.right = right;
        this.left = left;
        this.moving = moving;
    }

    public int getDownSpeed() {
        return downSpeed;
    }

    public void setDownSpeed(int downSpeed) {
        this.downSpeed = downSpeed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void setRight(boolean right) {
        this.right = right;
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

    public boolean isLeft() {
        return left;
    }

    public boolean isMoving() {
        return moving;
    }

    public boolean isRight() {
        return right;
    }

}
