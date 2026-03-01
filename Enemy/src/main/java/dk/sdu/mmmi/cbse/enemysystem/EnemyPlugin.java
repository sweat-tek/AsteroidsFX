package dk.sdu.mmmi.cbse.enemysystem;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    public EnemyPlugin() {
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/4);
        enemyShip.setY(gameData.getDisplayWidth()/3);
        enemyShip.setRadius(8);
        enemyShip.setHealth(150);
        return enemyShip;
    }

    @Override
    public void start(GameData gameData, World world) {
        // Add Enemy to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove enemy
        world.removeEntity(enemy);
    }
}
