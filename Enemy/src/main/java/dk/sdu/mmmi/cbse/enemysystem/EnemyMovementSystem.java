package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyMovementSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            if(canFire((Enemy) enemy, gameData)) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(enemy, gameData));
                            enemy.setLastFiredBullet(System.currentTimeMillis());
                        }
                );
            }
            int random = new Random().nextInt(4);
            if(canMove((Enemy) enemy)) {
                switch (random) {
                    case 1:
                        enemy.setRotation(enemy.getRotation()-5);
                        enemy.setLastEnemyMove(System.currentTimeMillis());
                        break;
                    case 2:
                        enemy.setRotation(enemy.getRotation()+5);
                        enemy.setLastEnemyMove(System.currentTimeMillis());
                        break;
                    case 3:
                        double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
                        double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
                        enemy.setX(enemy.getX() + changeX);
                        enemy.setY(enemy.getY() + changeY);
                        enemy.setLastEnemyMove(System.currentTimeMillis());
                        break;
                }
            }

        }
        if (System.currentTimeMillis() - gameData.getLastSpawnedEnemy() >= gameData.getEnemySpawnCooldown()) {
            Entity enemy = spawnEnemy();
            System.out.println("Added Enemy");
            world.addEntity(enemy);
            gameData.setLastSpawnedEnemy(System.currentTimeMillis());
        }
    }

    private boolean canFire(Entity enemy, GameData gameData) {
        long currentTime = System.currentTimeMillis();
        int fireRate = gameData.getFireRate();
        int bulletCooldown = enemy.getEnemyBulletCooldown();
        long lastFiredBullet = enemy.getLastFiredBullet();
        return (currentTime - lastFiredBullet >= bulletCooldown/fireRate);
    }

    private boolean canMove(Entity enemy) {
        long currentTime = System.currentTimeMillis();
        long lastMove = enemy.getLastEnemyMove();
        int moveCoolDown = enemy.getEnemyMoveCooldown();
        return currentTime - lastMove >= moveCoolDown;
    }

    private Entity spawnEnemy() {
        Entity enemy = new Enemy();
        Random rnd = new Random();
        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemy.setX(rnd.nextInt(700));
        enemy.setY(rnd.nextInt(700));
        enemy.setRadius(8);
        enemy.setRotation(rnd.nextInt(360));
        enemy.setHealth(150);
        return enemy;
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}
