package dk.sdu.mmmi.cbse.enemy;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

import java.util.Random;

public class EnemySpaceshipPlugin implements IGamePluginService {

    private Entity enemySpaceship;

    public EnemySpaceshipPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entities to the world
        enemySpaceship = createEnemySpaceship(gameData);
        world.addEntity(enemySpaceship);
    }

    private Entity createEnemySpaceship(GameData gameData) {

        Entity enemyShip = new EnemySpaceship();
        enemyShip.setPolygonCoordinates(-10,0,-5,3,-3,6,3,6,5,3,10,0,3,-3,-3,-3);
        enemyShip.setWidth(20);
        enemyShip.setHeight(9);
        setStartPoint(gameData, enemyShip); // Set starting point for enemy spaceship

        return enemyShip;
    }

    private void setStartPoint(GameData gameData, Entity enemySpaceship) {
        Random random = new Random();
        int randomizer = random.nextInt(4) + 1;

        switch (randomizer) {
            case 1:
                // Set enemy at the top
                enemySpaceship.setX(Math.random() * gameData.getDisplayWidth());
                enemySpaceship.setY(0);
                enemySpaceship.setRotation(Math.random() * 180 + 0.1);
                break;
            case 2:
                // Set enemy at the bottom
                enemySpaceship.setX(Math.random() * gameData.getDisplayWidth());
                enemySpaceship.setY(gameData.getDisplayHeight());
                enemySpaceship.setRotation(Math.random() * -180 - 0.1);
                break;
            case 3:
                // Set enemy to the left
                enemySpaceship.setX(0);
                enemySpaceship.setY(Math.random() * gameData.getDisplayHeight());
                enemySpaceship.setRotation(random.nextDouble() * 180 - 90);
                break;
            case 4:
                // Set enemy to the right
                enemySpaceship.setX(gameData.getDisplayWidth());
                enemySpaceship.setY(Math.random() * gameData.getDisplayHeight());
                enemySpaceship.setRotation(random.nextDouble() * -180 + 90);
                break;
        }
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(enemySpaceship);
    }
}