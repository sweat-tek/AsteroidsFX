package dk.sdu.mmmi.cbse.enemy;


import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;



public class EnemySpaceshipControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemySpaceship : world.getEntities(EnemySpaceship.class)) {
            moveEnemySpaceship(enemySpaceship);
            if (fireBullet(System.currentTimeMillis(), (EnemySpaceship) enemySpaceship)) {
                spawnBullet((EnemySpaceship) enemySpaceship, gameData, world);
            }
            wrapAroundScreen(enemySpaceship, gameData);
        }
    }

    @Override
    public void postProcess(GameData gameData, World world) {

    }

    private void moveEnemySpaceship(Entity enemySpaceship) {
        double changeX = Math.cos(Math.toRadians(enemySpaceship.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemySpaceship.getRotation()));
        enemySpaceship.setX(enemySpaceship.getX() + changeX);
        enemySpaceship.setY(enemySpaceship.getY() + changeY);
    }

    private boolean fireBullet(long currentTime, EnemySpaceship enemySpaceship) {
        long fireNewBulletTime = enemySpaceship.getBulletFiredTime() + 1000;
        if (enemySpaceship.getBulletFiredTime() == 0 || currentTime >= fireNewBulletTime) {
            enemySpaceship.setBulletFiredTime(currentTime);
            return true;
        }
        return false;
    }

    private void spawnBullet(EnemySpaceship enemySpaceship, GameData gameData, World world) {
        for (BulletSPI bullet : getBulletSPIs()) {
            Entity bulletEntity = bullet.createBullet(enemySpaceship, gameData);
            bulletEntity.setRotation(0.1 + Math.random() * 359.9);
            enemySpaceship.addBullet((Bullet) bulletEntity);
            world.addEntity(bulletEntity);
        }
    }

    private void wrapAroundScreen(Entity enemySpaceship, GameData gameData) {
        if (enemySpaceship.getX() < 0) {
            enemySpaceship.setX(gameData.getDisplayWidth());
        } else if (enemySpaceship.getX() > gameData.getDisplayWidth()) {
            enemySpaceship.setX(0);
        }

        if (enemySpaceship.getY() < 0) {
            enemySpaceship.setY(gameData.getDisplayHeight());
        } else if (enemySpaceship.getY() > gameData.getDisplayHeight()) {
            enemySpaceship.setY(0);
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
    }
}