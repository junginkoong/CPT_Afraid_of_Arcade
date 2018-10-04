package cpt_arcade;

public class DefenceBullet {
    
   private String direction;
   private int bulletX;
   private int bulletY;

    public DefenceBullet() {
    }

    public DefenceBullet(String direction, int bulletX, int bulletY) {
        this.direction = direction;
        this.bulletX = bulletX;
        this.bulletY = bulletY;
    }

    public String getDirection() {
        return direction;
    }

    public int getBulletX() {
        return bulletX;
    }

    public int getBulletY() {
        return bulletY;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setBulletX(int bulletX) {
        this.bulletX = bulletX;
    }

    public void setBulletY(int bulletY) {
        this.bulletY = bulletY;
    }
   
}
