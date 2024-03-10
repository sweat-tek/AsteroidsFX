package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    Random random = new Random();
    public EnemyPlugin() {

    }
    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {

        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemyShip.setX(gameData.getDisplayHeight()/4);
        enemyShip.setY(gameData.getDisplayWidth()/4);
        return enemyShip;
    }


    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
//        world.removeEntity(enemy);
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Enemy.class) {
                world.removeEntity(e);
            }
        }
    }

}