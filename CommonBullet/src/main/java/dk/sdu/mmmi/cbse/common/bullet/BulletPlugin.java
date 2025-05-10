package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Implementation of BulletSPI that creates bullet entities
 */
public class BulletPlugin implements BulletSPI {
    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Bullet bullet = new Bullet();
        
        // Convert rotation to radians
        double radians = Math.toRadians(shooter.getRotation());
        
        // Calculate the offset from the shooter's position (using shooter's radius + bullet's radius)
        double offsetDistance = shooter.getRadius() + bullet.getRadius() + 5;
        
        // Set bullet's initial position at the tip of the shooter
        bullet.setX(shooter.getX() + Math.cos(radians) * offsetDistance);
        bullet.setY(shooter.getY() + Math.sin(radians) * offsetDistance);
        
        // Set the bullet's rotation to match the shooter's direction
        bullet.setRotation(shooter.getRotation());
        
        return bullet;
    }
}

