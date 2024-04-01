package dk.sdu.mmmi.cbse.enemysystem;


import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;





public class EnemyControlle implements IEntityProcessingService {
    Random random=new Random();





        @Override
        public void process(GameData gameData, World world) {
            for (Entity enemy : world.getEntities(Enemy.class)) {
                int randomRotationChange = random.nextInt(11) - 5; // Random number between -5 and 5
                enemy.setRotation(enemy.getRotation() + randomRotationChange);

                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );

                double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
                double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
                enemy.setX(enemy.getX() + changeX);
                enemy.setY(enemy.getY() + changeY);

                if (enemy.getX() < 0) {
                    enemy.setX(gameData.getDisplayWidth());
                }

                if (enemy.getX() > gameData.getDisplayWidth()) {
                    enemy.setX(0);
                }

                if (enemy.getY() < 0) {
                    enemy.setY(gameData.getDisplayHeight());
                }

                if (enemy.getY() > gameData.getDisplayHeight()) {
                    enemy.setY(0);
                }

            }
        }
    private List<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

}





