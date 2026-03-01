package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private int health;
    private long lastFiredBullet;
    private int EnemyBulletCooldown = 1000;
    private long lastEnemyMove;
    private int enemyMoveCooldown = 100;

    public int getEnemyBulletCooldown() {
        return EnemyBulletCooldown;
    }

    public void setEnemyBulletCooldown(int enemyBulletCooldown) {
        EnemyBulletCooldown = enemyBulletCooldown;
    }

    public long getLastEnemyMove() {
        return lastEnemyMove;
    }

    public void setLastEnemyMove(long lastEnemyMove) {
        this.lastEnemyMove = lastEnemyMove;
    }

    public int getEnemyMoveCooldown() {
        return enemyMoveCooldown;
    }

    public void setEnemyMoveCooldown(int enemyMoveCooldown) {
        this.enemyMoveCooldown = enemyMoveCooldown;
    }


            

    public String getID() {
        return ID.toString();
    }


    public void setPolygonCoordinates(double... coordinates ) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }
       

    public void setX(double x) {
        this.x =x;
    }

    public double getX() {
        return x;
    }

    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
        
    public float getRadius() {
        return this.radius;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public long getLastFiredBullet() {
        return lastFiredBullet;
    }

    public void setLastFiredBullet(long lastFiredBullet) {
        this.lastFiredBullet = lastFiredBullet;
    }
}
