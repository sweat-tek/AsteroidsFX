package dk.sdu.mmmi.cbse.enemy;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        int amountOfEnemies = world.getEntities(Enemy.class).size();
        // Spawn enemy , if there are less than 4 enemies with about 1% chance
        if(Math.random()*100 > 99 && amountOfEnemies < 4) {
            spawnEnemy(gameData, world);
        }

        // Process movement for all enemies
        for (Entity enemy : world.getEntities(Enemy.class)) {
            moveRandomly(enemy, gameData);

            // Fire bullet randomly
            if(Math.random()*100 > 99) {
                fireBullet(enemy, gameData, world);
            }
        }
    }

    private void moveRandomly(Entity enemy, GameData gameData) {
        double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
        enemy.setX(enemy.getX() + changeX/2);
        enemy.setY(enemy.getY() + changeY/2);

        // Change rotation a little bit randomly, but if it's close to the edges, they adjust
        // Get edges
        double width = gameData.getDisplayWidth();
        double height = gameData.getDisplayHeight();

        if(enemy.getX() < 0) {
            enemy.setRotation(enemy.getRotation() + 5);
        }
        if(enemy.getX() > width) {
            enemy.setRotation(enemy.getRotation() - 5);
        }
        if(enemy.getY() < 0) {
            enemy.setRotation(enemy.getRotation() + 5);
        }
        if(enemy.getY() > height) {
            enemy.setRotation(enemy.getRotation() - 5);
        }
        // if not close to edge, adjust direction randomly
        if(enemy.getX() > 0 && enemy.getX() < width && enemy.getY() > 0 && enemy.getY() < height) {
            if(Math.random()*10 > 9) {
                enemy.setRotation(enemy.getRotation() + (Math.random()*1000 > 500 ? 5 : -5));
            }
        }
    }

    private void spawnEnemy(GameData gameData, World world) {
        Entity enemy = new Enemy();
        enemy.setPolygonCoordinates(-10,-10,10,0,-10,10);
        enemy.setX(gameData.getDisplayWidth()*Math.random());
        enemy.setY(gameData.getDisplayHeight()*Math.random());
        enemy.setRotation((float) (Math.random()*360));
        world.addEntity(enemy);
    }

    private void fireBullet(Entity entity, GameData gameData, World world) {
        getBulletSPIs().forEach(bulletSPI -> {
            Entity bullet = bulletSPI.createBullet(entity, gameData);
            world.addEntity(bullet);
        });
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}