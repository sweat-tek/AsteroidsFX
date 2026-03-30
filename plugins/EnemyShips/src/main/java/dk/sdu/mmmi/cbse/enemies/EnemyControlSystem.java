package dk.sdu.mmmi.cbse.enemies;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private final int maxEnemies = 5;
    private final float spawnInterval = 1f;
    private float spawnTimer = 0;

    // Per-enemy state tracking
    private final Map<Entity, Float> directionChangeTimers = new HashMap<>();
    private final Map<Entity, Float> shootCooldownTimers = new HashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        spawnTimer += gameData.getDelta();
        spawnEnemiesAggressively(gameData, world);

        for (Entity enemy : world.getEntities(Enemy.class)) {
            handleMovement(enemy, gameData);
            handleBounds(enemy, gameData);
            maybeShoot(enemy, gameData, world);
        }

        // Clean up memory if enemies are destroyed
        directionChangeTimers.keySet().removeIf(e -> !world.getEntities().contains(e));
        shootCooldownTimers.keySet().removeIf(e -> !world.getEntities().contains(e));
    }

    private void spawnEnemiesAggressively(GameData gameData, World world) {
        int currentCount = world.getEntities(Enemy.class).size();

        while (currentCount < maxEnemies && spawnTimer >= spawnInterval) {
            Entity newEnemy = new EnemyPlugin().createEnemy(gameData);
            world.addEntity(newEnemy);

            // Initialize per-enemy timers
            directionChangeTimers.put(newEnemy, 0f);
            shootCooldownTimers.put(newEnemy, 0f);

            spawnTimer -= spawnInterval;
            currentCount++;
        }
    }

    private void handleMovement(Entity enemy, GameData gameData) {
        float timer = directionChangeTimers.getOrDefault(enemy, 0f);
        timer += gameData.getDelta();

        // Random direction change every 1,5 seconds
        if (timer >= 1.5f) {
            int direction = random.nextBoolean() ? 1 : -1;
            enemy.setRotation(enemy.getRotation() + direction * (5 + random.nextInt(6))); // 5–10 degrees
            timer = 0f;
        }
        directionChangeTimers.put(enemy, timer);

        // move forward
        double radians = Math.toRadians(enemy.getRotation());
        enemy.setX(enemy.getX() + Math.cos(radians));
        enemy.setY(enemy.getY() + Math.sin(radians));
    }

    private void handleBounds(Entity enemy, GameData gameData) {
        boolean bounced = false;

        if (enemy.getX() < 0 || enemy.getX() > gameData.getDisplayWidth()) {
            bounced = true;
            enemy.setX(Math.max(0, Math.min(enemy.getX(), gameData.getDisplayWidth())));
        }

        if (enemy.getY() < 0 || enemy.getY() > gameData.getDisplayHeight()) {
            bounced = true;
            enemy.setY(Math.max(0, Math.min(enemy.getY(), gameData.getDisplayHeight())));
        }

        if (bounced) {
            enemy.setRotation(enemy.getRotation() + 180);
        }
    }

    private void maybeShoot(Entity enemy, GameData gameData, World world) {
        float shootTimer = shootCooldownTimers.getOrDefault(enemy, 0f);
        shootTimer += gameData.getDelta();

        if (shootTimer >= 2.5f) {
            if (random.nextDouble() < 0.2) { // 20% chance to shoot
                getBulletSPIs().stream().findFirst().ifPresent(spi ->
                        world.addEntity(spi.createBullet(enemy, gameData))
                );
            }
            shootTimer = 0f;
        }

        shootCooldownTimers.put(enemy, shootTimer);
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class)
                .stream()
                .map(ServiceLoader.Provider::get)
                .collect(toList());
    }
}
