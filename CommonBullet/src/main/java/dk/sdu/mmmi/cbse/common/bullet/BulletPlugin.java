package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Implementation of BulletSPI that creates bullet entities
 */
public class BulletPlugin implements BulletSPI {
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        // Debug print
        System.out.println("Creating bullet from shooter at position: (" + shooter.getX() + ", " + shooter.getY() + ")");
        
        Bullet bullet = new Bullet();
        bullet.setEntityType("BULLET");  // Set entity type
        
        // Calculate starting position at the tip of the shooter
        double radians = Math.toRadians(shooter.getRotation());
        
        // Position bullet at the edge of the shooter (using shooter's radius)
        double distance = shooter.getRadius() + bullet.getRadius();
        double startX = shooter.getX() + Math.cos(radians) * distance;
        double startY = shooter.getY() + Math.sin(radians) * distance;
        
        bullet.setX(startX);
        bullet.setY(startY);
        bullet.setRotation(shooter.getRotation());
        
        // Debug print
        System.out.println("Bullet created at: (" + startX + ", " + startY + ") with rotation: " + bullet.getRotation());
        
        return bullet;
    }
}

