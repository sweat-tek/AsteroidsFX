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
    private static final Random rnd = new Random();

    @Override
    public void createSplitAsteroid(Entity e, World w) {
        if (!(e instanceof Asteroid)) {
            return;
        }
        
        Asteroid asteroid = (Asteroid) e;
        if (asteroid.getSize() != Asteroid.Size.SMALL) {
            Asteroid.Size newSize = asteroid.getSize() == Asteroid.Size.LARGE ? 
                                  Asteroid.Size.MEDIUM : Asteroid.Size.SMALL;
            
            // Create two smaller asteroids
            for (int i = 0; i < 2; i++) {
                Asteroid newAsteroid = new Asteroid();
                newAsteroid.setSize(newSize);
                
                // Position at the split asteroid's location
                newAsteroid.setX(asteroid.getX());
                newAsteroid.setY(asteroid.getY());
                
                // Set random directions moving away from each other
                double newRotation = asteroid.getRotation() + (i == 0 ? 45 : -45) + (rnd.nextDouble() * 30);
                newAsteroid.setRotation(newRotation);
                
                w.addEntity(newAsteroid);
            }
        }
        // Remove the original asteroid
        w.removeEntity(asteroid);
    }
}
