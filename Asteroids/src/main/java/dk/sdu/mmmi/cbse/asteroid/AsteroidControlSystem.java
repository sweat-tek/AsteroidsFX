package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private final int maxAsteroids = 10; // max asteroids
    private final float spawnInterval = 1.5f; // Seconds between spawn of asteroid.
    private float spawnTimer = 0;

    @Override
    public void process(GameData gameData, World world) {
        spawnTimer += gameData.getDelta();

        int currentCount = world.getEntities(Asteroid.class).size();

        while (spawnTimer >= spawnInterval && currentCount < maxAsteroids) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
            spawnTimer -= spawnInterval;
            currentCount++;
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();

        int size = random.nextInt(20) + 10; // size range
        asteroid.setPolygonCoordinates(
                size, -size,
                -size, -size,
                -size, size,
                size, size
        );

        // spawn point to avoid asteroids spawning anywhere
        int side = random.nextInt(4);
        float x, y, rotation;
        switch (side) {
            case 0: // Left
                x = 0;
                y = random.nextInt(gameData.getDisplayHeight());
                rotation = random.nextInt(120) - 60;
                break;
            case 1: // Right
                x = gameData.getDisplayWidth();
                y = random.nextInt(gameData.getDisplayHeight());
                rotation = 180 + random.nextInt(120) - 60;
                break;
            case 2: // Top
                x = random.nextInt(gameData.getDisplayWidth());
                y = 0;
                rotation = 90 + random.nextInt(120) - 60;
                break;
            case 3: // Bottom
                x = random.nextInt(gameData.getDisplayWidth());
                y = gameData.getDisplayHeight();
                rotation = 270 + random.nextInt(120) - 60;
                break;
            default:
                x = gameData.getDisplayWidth() / 2f;
                y = gameData.getDisplayHeight() / 2f;
                rotation = random.nextInt(360);
        }

        asteroid.setX(x);
        asteroid.setY(y);
        asteroid.setRotation(rotation);
        asteroid.setRadius(size / 2f);
        asteroid.setColor(javafx.scene.paint.Color.BLACK);

        return asteroid;
    }
}
