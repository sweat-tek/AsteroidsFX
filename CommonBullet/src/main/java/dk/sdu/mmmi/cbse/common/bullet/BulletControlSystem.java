package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        // Debug: Print number of bullets in the world
        int bulletCount = world.getEntities(Bullet.class).size();
        if (bulletCount > 0) {
            System.out.println("Processing " + bulletCount + " bullets");
        }

        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Convert rotation to radians
            double radians = Math.toRadians(bullet.getRotation());
            
            // Higher speed for better gameplay
            double bulletSpeed = 15;
            
            // Update bullet position based on its rotation
            double newX = bullet.getX() + Math.cos(radians) * bulletSpeed;
            double newY = bullet.getY() + Math.sin(radians) * bulletSpeed;
            bullet.setX(newX);
            bullet.setY(newY);
            
            // Debug: Print bullet position
            System.out.println("Bullet at: (" + newX + ", " + newY + ") rotation: " + bullet.getRotation());
            
            // Remove bullets that are out of bounds
            if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth() ||
                bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
                System.out.println("Bullet removed - out of bounds");
            }
        }
    }
}

