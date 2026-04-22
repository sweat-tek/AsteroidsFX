package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add enemy entity to the world
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Entity enemyShip = new Enemy();
        // Create a hexagon shape for the enemy
        enemyShip.setPolygonCoordinates(12,0, 6,10, -6,10, -12,0, -6,-10, 6,-10);
        // Position enemy randomly on screen
        enemyShip.setX(Math.random() * gameData.getDisplayWidth());
        enemyShip.setY(Math.random() * gameData.getDisplayHeight());
        enemyShip.setRadius(10);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove enemy entity
        world.removeEntity(enemy);
    }
}
