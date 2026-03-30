package dk.sdu.mmmi.cbse.enemies;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    Entity createEnemy(GameData gameData) {
        Entity enemyShip = new Enemy();

        Random random = new Random();
        float x, y;
        float rotation;

        int side = random.nextInt(4); // 0 = left, 1 = right, 2 = top, 3 = bottom

        switch (side) {
            case 0: // Left edge
                x = 0;
                y = random.nextInt(gameData.getDisplayHeight());
                rotation = random.nextInt(120) - 60; // Aim rightward
                break;
            case 1: // Right edge
                x = gameData.getDisplayWidth();
                y = random.nextInt(gameData.getDisplayHeight());
                rotation = 180 + random.nextInt(120) - 60; // Aim leftward
                break;
            case 2: // Top edge
                x = random.nextInt(gameData.getDisplayWidth());
                y = 0;
                rotation = 90 + random.nextInt(120) - 60; // Aim downward
                break;
            case 3: // Bottom edge
                x = random.nextInt(gameData.getDisplayWidth());
                y = gameData.getDisplayHeight();
                rotation = 270 + random.nextInt(120) - 60; // Aim upward
                break;
            default:
                x = gameData.getDisplayWidth() / 2f;
                y = gameData.getDisplayHeight() / 2f;
                rotation = random.nextInt(360);
        }

        enemyShip.setX(x);
        enemyShip.setY(y);
        enemyShip.setRotation(rotation);
        enemyShip.setRadius(10);

        // enemy shape
        enemyShip.setPolygonCoordinates(-5, -5, 10, 0, -5, 5);

        // enemy color
        enemyShip.setColor(Color.RED);

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
