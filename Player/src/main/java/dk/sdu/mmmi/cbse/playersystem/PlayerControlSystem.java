package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.bullet.RunTimeInstantiateService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;


public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
            
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);

            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));
            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));

            if (gameData.getKeys().isDown(GameKeys.SPACE)){
                for (RunTimeInstantiateService bullet : getBulletSPIs()){
                    world.addEntity(bullet.spawn(player.getPart(PositionPart.class), gameData));
                }
            }

            movingPart.process(gameData, player);
            lifePart.process(gameData, player);
            if (lifePart.isHit()){
                System.exit(1);
            }
            updateShape(player);

            positionPart.process(gameData, player);

        }
    }

    private void updateShape(Entity entity) {
        double[] shapex = new double[4];
        double[] shapey = new double[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        double x = positionPart.getX();
        double y = positionPart.getY();
        double radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    private Collection<? extends RunTimeInstantiateService> getBulletSPIs() {
        return ServiceLoader.load(RunTimeInstantiateService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}
