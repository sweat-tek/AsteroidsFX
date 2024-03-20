package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidProcessor implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            double changeX = Math.cos(Math.toRadians(asteroid.getRotation()));
            double changeY = Math.sin(Math.toRadians(asteroid.getRotation()));

            asteroid.setX(asteroid.getX() + changeX * 0.5);
            asteroid.setY(asteroid.getY() + changeY * 0.5);

            if (asteroid.getX() < 0)
            { asteroid.setX(gameData.getDisplayWidth()); }

            if (asteroid.getX() > gameData.getDisplayWidth())
            { asteroid.setX(0); }

            if (asteroid.getY() < 0)
            { asteroid.setY(gameData.getDisplayHeight()); }

            if (asteroid.getY() > gameData.getDisplayHeight())
            { asteroid.setY(0); }

            if (asteroid.getDead()) {
                System.out.println("Splitting");
                AsteroidPlugin asteroidPlugin = new AsteroidPlugin();

                Entity nAst = asteroidPlugin.createAsteroid(asteroid, gameData);

                world.addEntity(nAst);
            }
        }
    }
}