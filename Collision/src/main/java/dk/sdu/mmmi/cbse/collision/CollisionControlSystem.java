package dk.sdu.mmmi.cbse.collision;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionControlSystem implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (entity1 == entity2) continue;
                double distance = Math.sqrt(
                        Math.pow(entity1.getX() - entity2.getX(), 2)
                        + Math.pow((entity1.getY() - entity2.getY()), 2));

                if (distance < entity1.getSize() || distance < entity2.getSize()) {
                    entity1.setDead(true);
                    entity2.setDead(true);
                    entity1.setHealth(entity1.getHealth()-entity2.getSize());
                    entity2.setHealth(entity2.getHealth()-entity1.getSize());
                }
            }
        }
    }
}
