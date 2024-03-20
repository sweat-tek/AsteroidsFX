package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class AsteroidCreate implements IPostEntityProcessingService {

    /**
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {
        //System.out.println(world.getEntities(Asteroid.class).size());
        if (world.getEntities(Asteroid.class).size() < 1) {
            AsteroidPlugin asteroidPlugin = new AsteroidPlugin();
            asteroidPlugin.start(gameData, world);
        }
    }
}
