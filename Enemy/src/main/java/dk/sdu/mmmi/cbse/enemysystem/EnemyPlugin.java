package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        world.addEntity(createEnemy());
    }

    Entity createEnemy() {
        Enemy enemy = new Enemy();
        enemy.setX(100);
        enemy.setY(100);
        enemy.setRotation(180f);
        enemy.setPolygonCoordinates(-5,-3,0,10,5,-3);
        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
