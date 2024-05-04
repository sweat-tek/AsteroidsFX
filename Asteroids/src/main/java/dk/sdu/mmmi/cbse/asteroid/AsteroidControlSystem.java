package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Arrays;
import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService, AsteroidSPI {

    private final Random random = new Random();
    @Override
    public void process(GameData gameData, World world) {

        int amountOfAsteroids = world.getEntities(Asteroid.class).size();
        // Spawn asteroids if there are less than 10 randomly
        if(Math.random()*100 > 99 && amountOfAsteroids < 10) {
            spawnAsteroid(gameData, world);
        }

        // Move asteroids randomly, but in semi-continuous directions
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            moveAsteroidsSemiRandomly(asteroid, gameData);

        }
    }

    private void spawnAsteroid(GameData gameData, World world) {
        Entity asteroid = new Asteroid();
        asteroid.setPolygonCoordinates(
                0*random.nextDouble(0.8,1.2),
                0*random.nextDouble(0.8,1.2),
                20*random.nextDouble(0.8,1.2),
                24*random.nextDouble(0.8,1.2),
                32*random.nextDouble(0.8,1.2),
                28*random.nextDouble(0.8,1.2),
                40*random.nextDouble(0.8,1.2),
                16*random.nextDouble(0.8,1.2),
                28*random.nextDouble(0.8,1.2),
                -4*random.nextDouble(0.8,1.2));
        // Random position
        asteroid.setX(gameData.getDisplayWidth()*Math.random());
        asteroid.setY(gameData.getDisplayHeight()*Math.random());
        asteroid.setRotation((float) (Math.random()*360));
        world.addEntity(asteroid);
    }

    private void moveAsteroidsSemiRandomly(Entity asteroid, GameData gameData) {
        double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
        double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));
        asteroid.setX(asteroid.getX() + changeX/2);
        asteroid.setY(asteroid.getY() + changeY/2);
        changeDirectionIfCloseToEdge(asteroid, gameData);
    }

    private void changeDirectionIfCloseToEdge(Entity asteroid, GameData gameData) {
        double width = gameData.getDisplayWidth();
        double height = gameData.getDisplayHeight();
        if(asteroid.getX() < 0) {
            asteroid.setRotation(asteroid.getRotation() + 5);
        }
        if(asteroid.getX() > width) {
            asteroid.setRotation(asteroid.getRotation() - 5);
        }
        if(asteroid.getY() < 0) {
            asteroid.setRotation(asteroid.getRotation() + 5);
        }
        if(asteroid.getY() > height) {
            asteroid.setRotation(asteroid.getRotation() - 5);
        }
    }

    @Override
    public void splitAsteroid(Entity asteroid, World world) {
        double minimumHeight = 20;
        double minimumWidth = 20;

        // when the bullet hits its target it  would then split into 2

        if (asteroid.calculateWidth() > minimumWidth && asteroid.calculateHeight() > minimumHeight) {
            Entity asteroid1 = new Asteroid();
            Entity asteroid2 = new Asteroid();
            double[] polygonCoordinates = Arrays.stream(asteroid.getPolygonCoordinates()).map(point -> point * 0.5).toArray();
            asteroid1.setPolygonCoordinates(polygonCoordinates);
            asteroid2.setPolygonCoordinates(polygonCoordinates);
            asteroid1.setX(asteroid.getX());
            asteroid1.setY(asteroid.getY());
            asteroid2.setX(asteroid.getX());
            asteroid2.setY(asteroid.getY());
            asteroid1.setRotation(asteroid.getRotation() + 45);
            asteroid2.setRotation(asteroid.getRotation() - 45);
            world.addEntity(asteroid1);
            world.addEntity(asteroid2);
            world.removeEntity(asteroid);
        } else {
            world.removeEntity(asteroid);
        }
    }
}
