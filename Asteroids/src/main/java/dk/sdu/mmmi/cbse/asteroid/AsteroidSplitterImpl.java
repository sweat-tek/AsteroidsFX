package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if((e.getRadius()/2) > 5) world.addEntity(createAsteroid(e));
    }

    private Entity createAsteroid(Entity entity) {
        Entity asteroid = new Asteroid();
        Random rnd = new Random();
        double size = rnd.nextDouble(5, (entity.getRadius()/2));
        asteroid.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
        asteroid.setX(entity.getX());
        asteroid.setY(entity.getY());
        asteroid.setRadius((float) size);
        asteroid.setRotation(rnd.nextInt(90));
        asteroid.setType("asteroid");
        return asteroid;
    }
}
