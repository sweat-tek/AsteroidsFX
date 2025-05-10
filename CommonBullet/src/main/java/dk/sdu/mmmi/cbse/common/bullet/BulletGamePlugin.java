package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletGamePlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        // Nothing to initialize at start
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all bullets from world when stopping
        for (Entity entity : world.getEntities(Bullet.class)) {
            world.removeEntity(entity);
        }
    }
}

