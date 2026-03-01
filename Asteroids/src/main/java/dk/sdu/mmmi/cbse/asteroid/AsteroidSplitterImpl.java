package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

import java.util.Random;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity e, World world) {
        if (e.getRadius()<=5) {
            System.out.println("Too Small!!");
            return;
        }
        //int splitAmount = new Random().nextInt(2) + 1;
        int splitAmount = 2;
        for (int i = 0; i < splitAmount; i++) {
            //System.out.println(splitAmount);
            int rndSpawn = new Random().nextInt(10)+10;
            Entity asteroid = new Asteroid();
            Random randomRotation = new Random();
            double[] polygonCoords = new double[e.getPolygonCoordinates().length];
            for (int j = 0; j < polygonCoords.length; j++) {
                polygonCoords[j] = e.getPolygonCoordinates()[j]/2;
            }
            asteroid.setPolygonCoordinates(polygonCoords);
            asteroid.setX(e.getX()+rndSpawn);
            asteroid.setY(e.getY()+rndSpawn);
            asteroid.setRadius(e.getRadius()/2);
            asteroid.setRotation(e.getRotation() - 60 + randomRotation.nextInt(120));
            asteroid.setHealth(e.getHealth()/2);
            world.addEntity(asteroid);
        }

    }

}
