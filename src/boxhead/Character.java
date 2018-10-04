/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boxhead;

/**
 *
 * @author AntonysMac
 */
public class Character {

    int x, y, facing = 0, size = 20, speed, health=30, playerToChase, reloadTimer, reload=5;
    boolean up = false, down = false, left = false, right = false, shot = false, dead = false, pistol = true, shotgun = false, assaultRifle = false, gGun = false;

    public Character(int x, int y, int size, int speed, int health,int playerToChase, int reloadTimer) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.speed = speed;
        this.health = health;
        this.playerToChase = playerToChase;
        this.reloadTimer=reloadTimer;
    }

    public void setReload(int reload) {
        this.reload = reload;
    }

    public void setReloadTimer(int reloadTimer) {
        this.reloadTimer = reloadTimer;
    }

    public int getReload() {
        return reload;
    }

    public int getReloadTimer() {
        return reloadTimer;
    }

    public int getPlayerToChase() {
        return playerToChase;
    }

    public void setPlayerToChase(int playerToChase) {
        this.playerToChase = playerToChase;
    }
    
    public void setAssaultRifle(boolean assaultRifle) {
        this.assaultRifle = assaultRifle;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setPistol(boolean pistol) {
        this.pistol = pistol;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public void setShotgun(boolean shotgun) {
        this.shotgun = shotgun;
    }

    public void setgGun(boolean gGun) {
        this.gGun = gGun;
    }

    public boolean isAssaultRifle() {
        return assaultRifle;
    }

    public boolean isDead() {
        return dead;
    }

    public boolean isPistol() {
        return pistol;
    }

    public boolean isShot() {
        return shot;
    }

    public boolean isShotgun() {
        return shotgun;
    }

    public boolean isgGun() {
        return gGun;
    }
    
    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }
    
    

    public boolean isCollided(int minX, int minY, int maxX, int maxY) {
        if (minX <= x + size / 2 && minY <= y + size / 2 && maxX >= x - size / 2 && maxY >= y - size / 2) {
            return true;
        }
        return false;
    }

}
