package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.RunTimeInstantiateService;
import dk.sdu.mmmi.cbse.common.data.Color;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimerPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, RunTimeInstantiateService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);

            setShape(bullet);
        }
    }


    @Override
    public Entity spawn(PositionPart shooterPart, GameData gameData){
        double startx = shooterPart.getX();
        double starty = shooterPart.getY();
        double direction = shooterPart.getRadians();

        float dt = gameData.getDelta();
        float speed = 300;

        Entity bullet = new Bullet();
        bullet.currentColor = Color.VIOLET;
        bullet.setRadius(15);

        double bx = cos(direction) * 8 * bullet.getRadius();
        double by = sin(direction) * 8 * bullet.getRadius();

        // bullets stats
        bullet.add(new PositionPart(bx + startx, by + starty, direction));
        bullet.add(new LifePart(1));
        bullet.add(new MovingPart(0,10000000, speed, 0));
        bullet.add(new TimerPart(1));

        bullet.setShapeX(new double[3]);
        bullet.setShapeY(new double[3]);

        return bullet;
    }

    private void setShape(Entity entity) {
        double[] shapex = entity.getShapeX();
        double[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        double x = positionPart.getX();
        double y = positionPart.getY();
        double radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[2] = (float) (x + Math.cos(radians - 4 * 3.14f / 5));
        shapey[2] = (float) (y + Math.sin(radians - 4 * 3.14f / 5));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}