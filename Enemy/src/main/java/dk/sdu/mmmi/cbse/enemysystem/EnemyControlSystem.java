package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Random movement
            double randomDirection = Math.random() * 360;
            double speed = 1.0; // Slow movement speed

            double changeX = Math.cos(Math.toRadians(randomDirection)) * speed;
            double changeY = Math.sin(Math.toRadians(randomDirection)) * speed;

            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Random rotation
            enemy.setRotation(enemy.getRotation() + (Math.random() - 0.5) * 10);

            // Random bullet firing (low probability)
            if (Math.random() < 0.005) { // 0.5% chance per frame to fire
                getBulletSPIs().stream().findFirst().ifPresent(
                    spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );
            }

            // Wrap around screen edges
            if (enemy.getX() < 0) {
                enemy.setX(gameData.getDisplayWidth() - 1);
            }
            if (enemy.getX() > gameData.getDisplayWidth()) {
                enemy.setX(1);
            }
            if (enemy.getY() < 0) {
                enemy.setY(gameData.getDisplayHeight() - 1);
            }
            if (enemy.getY() > gameData.getDisplayHeight()) {
                enemy.setY(1);
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
