package dk.sdu.mmmi.cbse.enemysystem;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.enemy.Enemy;

import java.util.Collection;
import java.util.Random;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class EnemyControlSystem implements IEntityProcessingService{
    private Random r = new Random();
    private int speed = 4;
    int probabilityOfSuccess = 1;
    Random random = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            int sway = r.nextInt(11) - 5;
            enemy.setRotation(enemy.getRotation() + sway);
            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);
            if (enemy.getX() < 0) {
                world.removeEntity(enemy);
            }

            if (enemy.getX() > gameData.getDisplayWidth()) {
                world.removeEntity(enemy);
            }

            if (enemy.getY() < 0) {
                world.removeEntity(enemy);
            }

            if (enemy.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(enemy);
            }
            int randomNumber = random.nextInt(100);
            if (randomNumber == probabilityOfSuccess) {
                getBulletSPIs().stream().findFirst().ifPresent(
                        spi -> {world.addEntity(spi.createBullet(enemy, gameData));}
                );
            }
        }

    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}