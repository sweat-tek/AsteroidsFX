package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Convert rotation to radians
            double radians = Math.toRadians(bullet.getRotation());
            
            // Higher speed for better gameplay
            double bulletSpeed = 15;
            
            // Update bullet position based on its rotation
            bullet.setX(bullet.getX() + Math.cos(radians) * bulletSpeed);
            bullet.setY(bullet.getY() + Math.sin(radians) * bulletSpeed);
            
            // Remove bullets that are out of bounds
            if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() ||
                bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }
}

