package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private int worldWidth  = displayWidth +200 ;
    private int worldHeight = displayHeight +200 ;
    private int fireRate = 5;
    private long lastFiredBullet;
    private long lastFiredEnemyBullet;
    private int bulletCooldown = 1000;
    private int EnemyBulletCooldown = 1000;
    private long lastEnemyMove;
    private int enemyMoveCooldown = 100;
    private long lastSpawnedAstroid;
    private long lastSpawnedEnemy;
    private int enemySpawnCooldown = 10000;
    private int astroidCooldown = 5000;
    private final GameKeys keys = new GameKeys();


    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public int getWorldWidth() {
        return worldWidth;
    }
    public int getWorldHeight() {
        return worldHeight;
    }

    public int getFireRate() {
        return fireRate;
    }

    public void setFireRate(int fireRate) {
        this.fireRate = fireRate;
    }

    public long getLastFiredBullet() {
        return lastFiredBullet;
    }

    public void setLastFiredBullet(long lastFiredBullet) {
        this.lastFiredBullet = lastFiredBullet;
    }

    public int getBulletCooldown() {
        return bulletCooldown;
    }

    public void setBulletCooldown(int bulletCooldown) {
        this.bulletCooldown = bulletCooldown;
    }

    public long getLastSpawnedAstroid() {
        return lastSpawnedAstroid;
    }

    public void setLastSpawnedAstroid(long lastSpawnedAstroid) {
        this.lastSpawnedAstroid = lastSpawnedAstroid;
    }

    public int getAstroidCooldown() {
        return astroidCooldown;
    }

    public void setAstroidCooldown(int astroidCooldown) {
        this.astroidCooldown = astroidCooldown;
    }

    public int getEnemyBulletCooldown() {
        return EnemyBulletCooldown;
    }

    public void setEnemyBulletCooldown(int enemyBulletCooldown) {
        EnemyBulletCooldown = enemyBulletCooldown;
    }

    public long getLastFiredEnemyBullet() {
        return lastFiredEnemyBullet;
    }

    public void setLastFiredEnemyBullet(long lastFiredEnemyBullet) {
        this.lastFiredEnemyBullet = lastFiredEnemyBullet;
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

    public long getLastSpawnedEnemy() {
        return lastSpawnedEnemy;
    }

    public void setLastSpawnedEnemy(long lastSpawnedEnemy) {
        this.lastSpawnedEnemy = lastSpawnedEnemy;
    }

    public int getEnemySpawnCooldown() {
        return enemySpawnCooldown;
    }

    public void setEnemySpawnCooldown(int enemySpawnCooldown) {
        this.enemySpawnCooldown = enemySpawnCooldown;
    }
}
