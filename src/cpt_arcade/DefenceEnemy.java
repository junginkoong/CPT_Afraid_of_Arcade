package cpt_arcade;

public class DefenceEnemy {
    private String Direction;
    private int enemyX;
    private int enemyY;
    private int enemySpeed;
    private int enemyLife;

    public DefenceEnemy() {
    }

    public DefenceEnemy(String Direction, int enemyX, int enemyY, int enemySpeed, int enemyLife) {
        this.Direction = Direction;
        this.enemyX = enemyX;
        this.enemyY = enemyY;
        this.enemySpeed = enemySpeed;
        this.enemyLife = enemyLife;
    }

    public String getDirection() {
        return Direction;
    }

    public int getEnemyX() {
        return enemyX;
    }

    public int getEnemyY() {
        return enemyY;
    }

    public int getEnemySpeed() {
        return enemySpeed;
    }

    public int getEnemyLife() {
        return enemyLife;
    }

    public void setDirection(String Direction) {
        this.Direction = Direction;
    }

    public void setEnemyX(int enemyX) {
        this.enemyX = enemyX;
    }

    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public void setEnemyLife(int enemyLife) {
        this.enemyLife = enemyLife;
    }
}
