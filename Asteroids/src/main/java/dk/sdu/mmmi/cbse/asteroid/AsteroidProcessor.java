package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.Random;

public class AsteroidProcessor implements IEntityProcessingService, AsteroidSPI {

    private IAsteroidSplitter asteroidSplitter = new AsteroidSplitterImpl();

    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            if (asteroid.getX() < 0) {
                asteroid.setX(asteroid.getX() - gameData.getDisplayWidth());
            }

            if (asteroid.getX() > gameData.getDisplayWidth()) {
                asteroid.setX(asteroid.getX() % gameData.getDisplayWidth());
            }

            if (asteroid.getY() < 0) {
                asteroid.setY(asteroid.getY() - gameData.getDisplayHeight());
            }

            if (asteroid.getY() > gameData.getDisplayHeight()) {
                asteroid.setY(asteroid.getY() % gameData.getDisplayHeight());
            }

        }

        long currentTime = System.currentTimeMillis();
        if(currentTime - gameData.getLastSpawnedAstroid() >= gameData.getAstroidCooldown()) {
            for (int i = 0; i < 3; i++) {
                Entity asteroid = createAsteroid(gameData);
                asteroid.setHealth(400);
                if(asteroid.getX() - world.getEntities(Player.class).get(0).getX() < 10 || asteroid.getY()- world.getEntities(Player.class).get(0).getY() < 10) {
                    world.addEntity(asteroid);
                }

            }
            gameData.setLastSpawnedAstroid(currentTime);
        }

    }

    /**
     * Dependency Injection using OSGi Declarative Services
     */
    public void setAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = asteroidSplitter;
    }

    public void removeAsteroidSplitter(IAsteroidSplitter asteroidSplitter) {
        this.asteroidSplitter = null;
    }

    public IAsteroidSplitter getAsteroidSplitter() {
        return asteroidSplitter;
    }

    public Entity createAsteroid(GameData gameData) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        asteroid.setPolygonCoordinates(-10, -15, -5, -25, 5, -20, 15, -10, 10, 5, 0, 10, -10, 5, -15, -5);
        asteroid.setX(rnd.nextInt(700));
        asteroid.setY(rnd.nextInt(700));
        asteroid.setRadius(20);
        asteroid.setRotation(rnd.nextInt(360));
        asteroid.setHealth(400);
        return asteroid;
    }

}
