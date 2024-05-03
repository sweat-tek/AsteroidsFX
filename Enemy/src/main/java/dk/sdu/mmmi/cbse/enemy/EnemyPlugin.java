package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {


    @Override
    public void start(GameData gameData, World world) {

    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove all of type enemy
        for (dk.sdu.mmmi.cbse.common.data.Entity entity : world.getEntities()) {
            if (entity.getEntityType() == Entity.entityType.ENEMY) {
                world.removeEntity(entity);
            }
        }
    }
}