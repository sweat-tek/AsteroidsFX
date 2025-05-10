package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidPlugin implements IGamePluginService {
    private static final Random rnd = new Random();
    private static final int INITIAL_ASTEROIDS = 4;

    @Override
    public void start(GameData gameData, World world) {
        // Spawn initial asteroids
        for (int i = 0; i < INITIAL_ASTEROIDS; i++) {
            Entity asteroid = createRandomAsteroid(gameData, Asteroid.Size.LARGE);
            world.addEntity(asteroid);
            System.out.println("Created initial asteroid: " + asteroid.getID());
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    public Entity createRandomAsteroid(GameData gameData, Asteroid.Size size) {
        Asteroid asteroid = new Asteroid();
        asteroid.setEntityType("ASTEROID");  // Set entity type
        asteroid.setSize(size);
        
        // Position the asteroid at a random edge of the screen
        positionAtEdge(asteroid, gameData);
        
        // Set random rotation (direction)
        asteroid.setRotation(rnd.nextDouble() * 360);
        
        // Generate shape based on size
        generateShape(asteroid, size);
        
        return asteroid;
    }
    
    private void positionAtEdge(Asteroid asteroid, GameData gameData) {
        // Randomly choose which edge to spawn on
        int edge = rnd.nextInt(4); // 0: top, 1: right, 2: bottom, 3: left
        float radius = asteroid.getRadius();
        
        switch (edge) {
            case 0: // top
                asteroid.setX(rnd.nextDouble() * gameData.getDisplayWidth());
                asteroid.setY(-radius);
                break;
            case 1: // right
                asteroid.setX(gameData.getDisplayWidth() + radius);
                asteroid.setY(rnd.nextDouble() * gameData.getDisplayHeight());
                break;
            case 2: // bottom
                asteroid.setX(rnd.nextDouble() * gameData.getDisplayWidth());
                asteroid.setY(gameData.getDisplayHeight() + radius);
                break;
            case 3: // left
                asteroid.setX(-radius);
                asteroid.setY(rnd.nextDouble() * gameData.getDisplayHeight());
                break;
        }
    }
    
    private void generateShape(Asteroid asteroid, Asteroid.Size size) {
        // More points for larger asteroids
        int points;
        double variation;
        
        switch (size) {
            case LARGE:
                points = 10 + rnd.nextInt(3);  // 10-12 points
                variation = 0.4;  // 40% variation for large asteroids
                break;
            case MEDIUM:
                points = 8 + rnd.nextInt(3);   // 8-10 points
                variation = 0.3;  // 30% variation for medium asteroids
                break;
            case SMALL:
                points = 6 + rnd.nextInt(3);   // 6-8 points
                variation = 0.2;  // 20% variation for small asteroids
                break;
            default:
                points = 8 + rnd.nextInt(3);
                variation = 0.3;
        }
        
        double[] coordinates = new double[points * 2];
        double baseRadius = size.size;
        
        for (int i = 0; i < points; i++) {
            double angle = (2 * Math.PI * i) / points;
            // Add randomness to the radius based on size
            double radius = baseRadius * (1.0 - variation + (rnd.nextDouble() * variation * 2));
            coordinates[i * 2] = Math.cos(angle) * radius;
            coordinates[i * 2 + 1] = Math.sin(angle) * radius;
        }
        
        asteroid.setPolygonCoordinates(coordinates);
        asteroid.setRadius((float)baseRadius); // Cast to float for the collision radius
        
        System.out.println("Generated " + size + " asteroid with " + points + " points");
    }
}
