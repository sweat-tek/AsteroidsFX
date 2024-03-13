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
        double size = rnd.nextDouble(7,20);
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(0);
        asteroid.setY(0);
        asteroid.setRotation(rnd.nextInt(0,360));

        asteroid.setX(rnd.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(rnd.nextInt(gameData.getDisplayHeight()));
        asteroid = spawnAsteroids(asteroid, rnd.nextInt(0,4), gameData);

        asteroid.setSize(size);

        return asteroid;
    }

    private Entity spawnAsteroids(Entity asteroid, int cornerIndex, GameData gameData) {
        Random rnd = new Random();
        double x;
        double y;
        switch (cornerIndex) {
            case 1:
                y = rnd.nextDouble(0, gameData.getDisplayHeight());
                x = 0;
                asteroid.setRotation(rnd.nextDouble(0,180));
                break;
            case 2:
                y = 0;
                x = rnd.nextDouble(0, gameData.getDisplayWidth());
                asteroid.setRotation(rnd.nextDouble(180,360)); // Upside of the screen
                break;
            case 3:
                y = 0;
                x = rnd.nextDouble(0, gameData.getDisplayWidth());
                asteroid.setRotation(rnd.nextDouble(0,180)); // Downside of the screen
                break;
            default:
                y = rnd.nextDouble(0, gameData.getDisplayHeight());
                x = 0;
                asteroid.setRotation(rnd.nextDouble(180,360));
                break;
        }
        asteroid.setX(x);
        asteroid.setY(y);
        return asteroid;
    }

}