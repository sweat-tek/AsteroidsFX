package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;


import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private final double shootingProbability = 0.35; // 35% chance to shoot per frame

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            // Balanced random rotation: -5, 0, or +5
            int[] rotationValues = {-5, 0, 5}; // change here to change rotation
            enemy.setRotation(enemy.getRotation() + rotationValues[random.nextInt(rotationValues.length)]);

            // Move forward slightly
            double speed = 1.5;
            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) * speed;
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) * speed;

            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            // Keep enemy inside screen bounds
            if (enemy.getX() < 0) enemy.setX(1);
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(gameData.getDisplayWidth() - 1);
            if (enemy.getY() < 0) enemy.setY(1);
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(gameData.getDisplayHeight() - 1);

            // Random shooting
            if (random.nextDouble() < shootingProbability) {
                shootBullet(enemy, gameData, world);
            }
        }
    }

    private void shootBullet(Entity enemy, GameData gameData, World world) {
        getBulletSPIs().stream().findFirst().ifPresent(
                spi -> world.addEntity(spi.createBullet(enemy, gameData))
        );
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
