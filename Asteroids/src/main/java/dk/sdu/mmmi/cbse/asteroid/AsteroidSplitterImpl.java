package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity asteroid, World world) {
        float currentRadius = asteroid.getRadius();

        // Prevent splitting if already small
        if (currentRadius <= 5) {
            return;
        }


        float splitRadius = 4f;

        for (int i = 0; i < 2; i++) {
            Entity smallerAsteroid = new Asteroid();

            smallerAsteroid.setX(asteroid.getX());
            smallerAsteroid.setY(asteroid.getY());
            smallerAsteroid.setRotation((float) (asteroid.getRotation() + (i == 0 ? 20 : -20)));

            smallerAsteroid.setRadius(splitRadius);
            smallerAsteroid.setPolygonCoordinates(
                    splitRadius, -splitRadius,
                    -splitRadius, -splitRadius,
                    -splitRadius, splitRadius,
                    splitRadius, splitRadius
            );

            smallerAsteroid.setColor(asteroid.getColor());

            world.addEntity(smallerAsteroid);
        }
    }
}
