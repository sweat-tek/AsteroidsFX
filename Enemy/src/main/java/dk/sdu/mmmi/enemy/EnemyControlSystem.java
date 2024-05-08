package dk.sdu.mmmi.enemy;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {

    /**
     * @param gameData
     * @param world
     */
    @Override
    public void process(GameData gameData, World world) {

        if (world.getEntities(Enemy.class).size() < 1) {
            EnemyPlugin enemyPlugin = new EnemyPlugin();
            enemyPlugin.start(gameData, world);
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            if(enemy.getHealth() <= 0) {
                enemy.setDead(true);
            }


            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

            enemy.setX(enemy.getX() + changeX * 0.5);
            enemy.setY(enemy.getY() + changeY * 0.5);

            if (enemy.getX() < 0) { enemy.setX(gameData.getDisplayWidth()); }

            if (enemy.getX() > gameData.getDisplayWidth()) { enemy.setX(0); }

            if (enemy.getY() < 0) { enemy.setY(gameData.getDisplayHeight()); }

            if (enemy.getY() > gameData.getDisplayHeight()) { enemy.setY(0); }

            if (enemy.getDead() && enemy.getSize() > 10) {
            }
        }
    }
}
