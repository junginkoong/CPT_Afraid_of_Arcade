package cpt_arcade;

public class Fall_Panel {
    private int boxX;
    private int boxY;
    private int boxLength;
    private int boxHeight;

    public Fall_Panel() {
    }

    public Fall_Panel(int boxX, int boxY, int boxLength, int boxHeight) {
        this.boxX = boxX;
        this.boxY = boxY;
        this.boxLength = boxLength;
        this.boxHeight = boxHeight;
    }

    public int getBoxX() {
        return boxX;
    }

    public int getBoxY() {
        return boxY;
    }

    public int getBoxLength() {
        return boxLength;
    }

    public int getBoxHeight() {
        return boxHeight;
    }

    public void setBoxX(int boxX) {
        this.boxX = boxX;
    }

    public void setBoxY(int boxY) {
        this.boxY = boxY;
    }

    public void setBoxLength(int boxLength) {
        this.boxLength = boxLength;
    }

    public void setBoxHeight(int boxHeight) {
        this.boxHeight = boxHeight;
    }
}
