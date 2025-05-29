package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.enemy.Enemy;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;

public class EnemyControlSystem implements IEntityProcessingService {
    private Entity enemy;
    public EnemyControlSystem(){}

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            Random random = new Random();
            float RNG = random.nextFloat();

            if (RNG > 0.1f && RNG < .9f){
                movingPart.setUp(true);
            }

            if (RNG < 0.2f){
                movingPart.setLeft(true);
            }
            if (RNG > 0.8f){
                movingPart.setRight(true);
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);
            if (lifePart.isHit()){
                world.removeEntity(enemy);
            }
            movingPart.setLeft(false);
            movingPart.setRight(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity entity) {
        double[] shapex = new double[4];
        double[] shapey = new double[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        double x = positionPart.getX();
        double y = positionPart.getY();
        double radians = positionPart.getRadians();

        shapex[0] = (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }


}