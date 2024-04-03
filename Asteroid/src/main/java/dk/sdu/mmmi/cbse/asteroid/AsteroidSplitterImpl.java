package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public class AsteroidSplitterImpl implements IAsteroidSplitter {

     @Override
    public void createSplitAsteroid(GameData gameData, World world, Entity entity) {
            AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
            asteroidPlugin.createAsteroid(entity, gameData);
    }

}