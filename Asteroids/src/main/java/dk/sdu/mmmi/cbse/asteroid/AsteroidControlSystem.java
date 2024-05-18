package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;



public class AsteroidControlSystem implements IEntityProcessingService{

    @Override
    public void process(GameData gameData, World world) {
        // Ensure minimum number of asteroids in the game world
        spawnAsteroids(gameData, world);

        // Update position of each asteroid
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            // Calculate change in position based on current rotation
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            // Update asteroid's position
            asteroid.setX(asteroid.getX() + changeX);
            asteroid.setY(asteroid.getY() + changeY);

            // Wrap around screen boundaries if asteroid moves beyond them
            if (asteroid.getX() < 0) {
                asteroid.setX(gameData.getDisplayWidth());
            }
            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(0);
            }
            if (asteroid.getY() < 0) {
                asteroid.setY(gameData.getDisplayHeight());
            }
            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(0);
            }
        }
    }

    private void spawnAsteroids(GameData gameData, World world) {
        // Get the current count of asteroids in the game world
        int asteroidCount = world.getEntities(Asteroid.class).size();

        // Check if the current count is less than the minimum required
        if (asteroidCount < MIN_ASTEROIDS) {
            // Calculate the number of asteroids needed to reach the minimum
            int asteroidsToSpawn = MIN_ASTEROIDS - asteroidCount;

            // Spawn the required number of asteroids
            for (int i = 0; i < asteroidsToSpawn; i++) {
                // Create a new asteroid entity using the plugin
                Entity asteroid = asteroidPlugin.createAsteroid(gameData);

                // Add the asteroid to the game world
                world.addEntity(asteroid);
            }
        }
    }
    @Override
    public void postProcess(GameData gameData, World world) {

        for (Entity asteroid : new ArrayList<>(world.getEntities(Asteroid.class))) {
            // Check if asteroid is out of bounds
            if (asteroid.getX() < 0 || asteroid.getX() > gameData.getDisplayWidth() ||
                    asteroid.getY() < 0 || asteroid.getY() > gameData.getDisplayHeight()) {
                // Remove asteroid from the world
                world.removeEntity(asteroid);
            }
        }
    }


    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }


    private static final int MIN_ASTEROIDS = 5;
    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
}