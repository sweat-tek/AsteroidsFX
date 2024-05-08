package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.playersystem.Player;
import dk.sdu.mmmi.cbse.enemy.EnemySpaceship;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionSystem implements IPostEntityProcessingService {

    private Boolean skipCollision = false;

    @Override
    public void process(GameData gameData, World world) {

        // Iterate through all entities in the world
        for (Entity entity : world.getEntities()) {
            for (Entity collideEntity : world.getEntities()) {
                skipCollision = false;

                // Skip if entity is colliding with itself
                if (entity == collideEntity) {
                    continue;
                }

                // Skip collision if both entities are asteroids
                if (entity.getClass() == Asteroid.class && collideEntity.getClass() == Asteroid.class) {
                    continue;
                }

                // Check if entity is a player and collideEntity is a bullet made by that player
                if (entity.getClass() == Player.class && collideEntity.getClass() == Bullet.class) {
                    for (Entity bullet : ((Player) entity).getBullets()) {
                        if (bullet == collideEntity) {
                            skipCollision = true;
                        }
                    }
                }

                // Check if entity is an enemy spaceship and collideEntity is a bullet made by that enemy spaceship
                if (entity.getClass() == EnemySpaceship.class && collideEntity.getClass() == Bullet.class) {
                    for (Entity bullet : ((EnemySpaceship) entity).getBullets()) {
                        if (bullet == collideEntity) {
                            skipCollision = true;
                        }
                    }
                }

                // Skip collision if requested
                if (skipCollision == true) {
                    continue;
                }

                // Check for overlap
                double startEntity = entity.getX() + entity.getWidth() / 2;
                double startCollideEntity = collideEntity.getX() + collideEntity.getWidth() / 2;
                double endEntity = entity.getY() + entity.getHeight() / 2;
                double endCollideEntity = collideEntity.getY() + collideEntity.getHeight() / 2;

                // Calculate the distance between the centers of the entities' hit-boxes
                double distanceX = Math.abs(startEntity - startCollideEntity);
                double distanceY = Math.abs(endEntity - endCollideEntity);

                // Calculate the minimum distance between the centers at which a collision can occur
                double minDistanceX = entity.getWidth() / 2 + collideEntity.getWidth() / 2;
                double minDistanceY = entity.getHeight() / 2 + collideEntity.getHeight() / 2;

                // If there is a collision, handle it for different entity types
                if (distanceX < minDistanceX && distanceY < minDistanceY) {
                    if (entity.getClass() == Asteroid.class || entity.getClass() == Player.class || entity.getClass() == EnemySpaceship.class) {
                        entity.handleCollision(gameData, world, collideEntity);
                    }
                }
            }
        }
    }

    @Override
    public void postProcess(GameData gameData, World world) {
        // Additional post-processing logic can be added here if needed
    }
}