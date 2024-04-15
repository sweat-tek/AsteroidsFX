package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {
    /**
     * @param gameData
     * @param world
     */

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemy = new Enemy();
        Random rnd = new Random();

        enemy.setPolygonCoordinates(-10,-10,20,0,-10,10);
        enemy = spawnEnemies(enemy, rnd.nextInt(0, 4), gameData);

        return enemy;
    }

    private Entity spawnEnemies(Entity enemy, int cornerIndex, GameData gameData) {
        Random rnd = new Random();
        double x;
        double y;
        switch (cornerIndex) {
            case 1:
                y = rnd.nextDouble(0, gameData.getDisplayHeight());
                x = 0;
                enemy.setRotation(rnd.nextDouble(0,180));
                break;
            case 2:
                y = 0;
                x = rnd.nextDouble(0, gameData.getDisplayWidth());
                enemy.setRotation(rnd.nextDouble(180,360)); // Upside of the screen
                break;
            case 3:
                y = 0;
                x = rnd.nextDouble(0, gameData.getDisplayWidth());
                enemy.setRotation(rnd.nextDouble(0,180)); // Downside of the screen
                break;
            default:
                y = rnd.nextDouble(0, gameData.getDisplayHeight());
                x = 0;
                enemy.setRotation(rnd.nextDouble(180,360));
                break;
        }
        enemy.setX(x);
        enemy.setY(y);
        return enemy;
    }


    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
