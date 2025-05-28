package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                LifePart entityLife = entity.getPart(LifePart.class);
                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;
                }
                if (this.collides(entity,collisionDetection)) {
                    if (entityLife.getLife() > 0 && entityLife.getIFrames() <= 0){
                        entityLife.setIsHit(true);
                        ((LifePart)(collisionDetection.getPart(LifePart.class))).setIsHit(true);
                    }
                }
            }
        }
    }
    public Boolean collides(Entity entity1, Entity entity2) {

        PositionPart entMove = entity1.getPart(PositionPart.class);
        PositionPart entMove2 = entity2.getPart(PositionPart.class);

        double dx = entMove.getX() - entMove2.getX();
        double dy = entMove.getY() - entMove2.getY();
        double dist = Math.sqrt(dx * dx + dy * dy);
        return dist < (entity1.getRadius() + entity2.getRadius());
    }

}
