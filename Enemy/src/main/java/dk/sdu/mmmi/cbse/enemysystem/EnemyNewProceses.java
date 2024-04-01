package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class EnemyNewProceses implements IPostEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {
        if (world.getEntities(Enemy.class).size() == 0) {
            EnemyPlugin enemyPlugin = new EnemyPlugin();

            enemyPlugin.start(gameData, world);

        }
    }
}
