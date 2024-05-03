package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface AsteroidSPI {
    /**
     * Split the asteroid into two smaller asteroids and remove the original asteroid
     * @param asteroid
     * @param world
     * @precondition asteroid is not null and world is not null
     * @postcondition  asteroid is removed from the world and two new asteroids are added to the world
     */
    public void splitAsteroid(Entity asteroid, World world);
}