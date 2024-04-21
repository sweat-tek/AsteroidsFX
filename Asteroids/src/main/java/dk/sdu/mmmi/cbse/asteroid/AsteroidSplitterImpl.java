package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import java.util.Random;

class AsteroidSplitImpl implements IAsteroidSplitter {

    @Override
    public void createSplitAsteroid(Entity entity, World world, GameData gameData) {
        Random random = new Random();
        for(int i = 0; i < random.nextInt(5) + 1; i++){
            AsteroidPlugin splittedasteroid = new AsteroidPlugin();
            world.addEntity(splittedasteroid.createAsteroid(gameData, entity));
        }
    }
}