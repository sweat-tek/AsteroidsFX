package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class AsteroidNewProcessor implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        if(world.getEntities(Asteroid.class).size() < 1) {
            AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
            asteroidPlugin.start(gameData, world);
        }
    }
}
