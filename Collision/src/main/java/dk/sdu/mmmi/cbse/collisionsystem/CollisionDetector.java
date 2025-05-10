package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {
    private IAsteroidSplitter asteroidSplitter;

    public CollisionDetector() {
        // Load the asteroid splitter service
        ServiceLoader<IAsteroidSplitter> loader = ServiceLoader.load(IAsteroidSplitter.class);
        if (loader.findFirst().isPresent()) {
            asteroidSplitter = loader.findFirst().get();
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        // Store entities to be removed to avoid concurrent modification
        List<Entity> entitiesToRemove = new ArrayList<>();
        List<Asteroid> asteroidsToSplit = new ArrayList<>();

        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                if (collides(entity1, entity2)) {
                    handleCollision(entity1, entity2, world, entitiesToRemove, asteroidsToSplit);
                }
            }
        }

        // Remove entities after processing all collisions
        for (Entity entity : entitiesToRemove) {
            world.removeEntity(entity);
            System.out.println("Removed entity: " + entity.getID());
        }

        // Split asteroids after removing entities
        if (asteroidSplitter != null) {
            for (Asteroid asteroid : asteroidsToSplit) {
                System.out.println("Splitting asteroid of size: " + asteroid.getSize());
                asteroidSplitter.createSplitAsteroid(asteroid, world);
            }
        }
    }

    private void handleCollision(Entity entity1, Entity entity2, World world,
                               List<Entity> entitiesToRemove, List<Asteroid> asteroidsToSplit) {
        // Handle Player-Asteroid collision
        if ((entity1 instanceof Player && entity2 instanceof Asteroid) ||
            (entity1 instanceof Asteroid && entity2 instanceof Player)) {
            entitiesToRemove.add(entity1);
            entitiesToRemove.add(entity2);
            System.out.println("GAME OVER - Player hit by asteroid!");
            // Game over logic would go here
        }
        // Handle Bullet-Asteroid collision
        else if ((entity1 instanceof Bullet && entity2 instanceof Asteroid) ||
                 (entity1 instanceof Asteroid && entity2 instanceof Bullet)) {
            Asteroid asteroid = (entity1 instanceof Asteroid) ? (Asteroid) entity1 : (Asteroid) entity2;
            Entity bullet = (entity1 instanceof Bullet) ? entity1 : entity2;
            
            asteroidsToSplit.add(asteroid);
            entitiesToRemove.add(bullet);
            entitiesToRemove.add(asteroid);
            System.out.println("Bullet hit asteroid: " + asteroid.getID() + " of size " + asteroid.getSize());
        }
    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }
}
