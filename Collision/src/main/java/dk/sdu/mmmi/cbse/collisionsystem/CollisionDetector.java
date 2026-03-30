package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private final ServiceLoader<IAsteroidSplitter> asteroidSplitterLoader = ServiceLoader.load(IAsteroidSplitter.class);

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    handleCollision(entity1, entity2, world);
                }
            }
        }
    }

    private boolean collides(Entity e1, Entity e2) {
        float dx = (float) (e1.getX() - e2.getX());
        float dy = (float) (e1.getY() - e2.getY());
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (e1.getRadius() + e2.getRadius());
    }

    private void handleCollision(Entity e1, Entity e2, World world) {
        String c1 = e1.getClass().getSimpleName();
        String c2 = e2.getClass().getSimpleName();

        boolean isBullet = e1 instanceof Bullet || e2 instanceof Bullet;
        boolean isAsteroid = e1 instanceof Asteroid || e2 instanceof Asteroid;
        boolean isPlayer = c1.equals("Player") || c2.equals("Player");
        boolean isEnemy = c1.equals("Enemy") || c2.equals("Enemy");

        // Bullet hits asteroid
        if ((e1 instanceof Bullet && e2 instanceof Asteroid) || (e2 instanceof Bullet && e1 instanceof Asteroid)) {
            Entity asteroid = e1 instanceof Asteroid ? e1 : e2;
            createAsteroidSplit(asteroid, world);
            world.removeEntity(asteroid);
            world.removeEntity(e1);
            world.removeEntity(e2);
        }

        // Bullet hits enemy or player
        else if (isBullet && (isEnemy || isPlayer)) {
            world.removeEntity(e1);
            world.removeEntity(e2);
        }

        // Asteroid hits player or enemy
        else if (isAsteroid && (isPlayer || isEnemy)) {
            world.removeEntity(e1);
            world.removeEntity(e2);
        }

        // Enemy hits player
        else if (isEnemy && isPlayer) {
            world.removeEntity(e1);
            world.removeEntity(e2);
        }
    }

    private void createAsteroidSplit(Entity asteroid, World world) {
        for (IAsteroidSplitter splitter : asteroidSplitterLoader) {
            splitter.createSplitAsteroid(asteroid, world);
        }
    }
}
