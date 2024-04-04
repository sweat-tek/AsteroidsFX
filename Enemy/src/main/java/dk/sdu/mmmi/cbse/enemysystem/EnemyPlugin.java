package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        Entity enemy=createEnemy(gameData);
        world.addEntity(enemy);

    }

    public Entity createEnemy(GameData gameData){
        Entity enemy = new Enemy();

        enemy.setPolygonCoordinates(-5,-5,10,0,-5,5);
        enemy.setX(100);
        enemy.setY(100);
        enemy.setType("enemy");
       // enemy.setType("asteroids");//enemy
        enemy.setTimeshoter(0);
        enemy.setRadius(8);
        return enemy;

    }

    @Override
    public void stop(GameData gameData, World world) {

    }
}
