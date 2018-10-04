/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doodlejump;

import java.awt.Point;

public class Doodle {

    private Point location;
    private int size;
    public static Doodle doodle;

    public Doodle(Point location, int size) {
        this.location = location;
        this.size = size;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

}
