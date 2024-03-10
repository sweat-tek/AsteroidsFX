package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.Enemy;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService, IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {

            double changeX = Math.cos(Math.toRadians(enemy.getRotation())) + 1 * Math.random();
            double changeY = Math.sin(Math.toRadians(enemy.getRotation())) + 1 * Math.random();
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);
            enemy.setRotation(enemy.getRotation() + 1 * Math.random());

            if (100 * Math.random() > 98) {
                for (BulletSPI bullet : getBulletSPIs()) {
                    world.addEntity(bullet.createBullet(enemy,gameData));
                }
            }

            if (enemy.getX() < -2) {
                enemy.setX(gameData.getDisplayWidth() + 1);
            }

            if (enemy.getX() > gameData.getDisplayWidth() + 2) {
                enemy.setX(-1);
            }

            if (enemy.getY() < -2) {
                enemy.setY(gameData.getDisplayHeight() + 1);
            }

            if (enemy.getY() > gameData.getDisplayHeight() + 2) {
                enemy.setY(-1);
            }

        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}