/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

import java.awt.Point;

public class Platform {

    int size, type, extraBit=12, maxHeight, minHeight, heightFluctuation=50+(int)(Math.random()*25), width, downSpeed=3, colorChanger=0;
    Point location;
    boolean right=true, up=false, snap=false, disappear=false;

    public Platform(Point location, int size, int type, int width) {
        this.size = size;
        this.location = location;
        this.type=type;
        this.maxHeight=location.y-heightFluctuation;
        this.minHeight=location.y+heightFluctuation;
        this.width=width;
    }

    public void setColorChanger(int colorChanger) {
        this.colorChanger = colorChanger;
    }

    public int getColorChanger() {
        return colorChanger;
    }

    public void setDisappear(boolean disappear) {
        this.disappear = disappear;
    }

    public boolean isDisappear() {
        return disappear;
    }

    public int getWidth() {
        return width;
    }
    
    
    
    public void setRight(boolean right) {
        this.right = right;
    }

    public int getDownSpeed() {
        return downSpeed;
    }

    public void setSnap(boolean snap) {
        this.snap = snap;
    }

    public boolean isSnap() {
        return snap;
    }
    
    
    
    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isUp() {
        return up;
    }
    
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }
    
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
        this.maxHeight=location.y-heightFluctuation;
        this.minHeight=location.y+heightFluctuation;
        if (snap){
            downSpeed++;
        }
    }
    
    public void moveUpAndDown(Point location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isCollided(Point doodleLocation, int doodleSize) {
        if (doodleLocation.x + doodleSize >= location.x && doodleLocation.x <= location.x + width * size && doodleLocation.y + doodleSize >= location.y - extraBit && doodleLocation.y + doodleSize <= location.y + size + extraBit) {
            return true;
        }
        return false;
    }

}
