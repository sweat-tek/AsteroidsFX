package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world) {
        Random rnd = new Random(); // If wanted to generate minimum and maximum spawns of asteroids.
        int asteroidCount = 7;

        for (int i = 0; i < asteroidCount; i++) {
            Entity asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        int size = rnd.nextInt(12) + 7;
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRadius(size);
        asteroid.setRotation(rnd.nextInt(90));

        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
        spawnAsteroids(asteroid, rnd.nextInt(4), gameData);

        double acceleration = 0.2 + rnd.nextDouble() * 0.4;
        double direction = Math.toRadians(asteroid.getRotation());
        double directionX = acceleration * Math.cos(direction);
        double directionY = acceleration * Math.sin(direction);

        asteroid.setDirectionX(directionX);
        asteroid.setDirectionY(directionY);
        asteroid.setSize(size);
        asteroid.setRadius(size * 2);
        return asteroid;
    }

    private void spawnAsteroids(Entity asteroid, int cornerIndex, GameData gameData) {
        Random rnd = new Random();
        int rotation = rnd.nextInt(70);
        int x = 1;
        int y = 1;
        switch (cornerIndex) {
            case 1:
                y = gameData.getDisplayHeight() - 1;
                rotation += 280;
                break;
            case 2:
                x = gameData.getDisplayWidth() - 1;
                rotation += 100;
                break;
            case 3:
                x = gameData.getDisplayWidth() - 1;
                y = gameData.getDisplayHeight() - 1;
                rotation += 190;
                break;
            default:
                rotation += 10;
                break;
        }
        asteroid.setRotation(rotation);
        asteroid.setX(x);
        asteroid.setY(y);
    }

}