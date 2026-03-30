package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import javafx.scene.paint.Color;

import java.util.*;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private static final float MAX_LIFETIME = 1.5f; // Bullets disappear after 1.5 seconds
    private static final float BULLET_SPEED = 10f;  // How fast bullets move per frame

    // Keeps track of how long each bullet has existed
    private final Map<Entity, Float> bulletTimers = new HashMap<>();

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> expiredBullets = new ArrayList<>();

        for (Entity bullet : world.getEntities(Bullet.class)) {
            // Move the bullet forward based on its direction and speed
            double radians = Math.toRadians(bullet.getRotation());
            bullet.setX(bullet.getX() + Math.cos(radians) * BULLET_SPEED);
            bullet.setY(bullet.getY() + Math.sin(radians) * BULLET_SPEED);

            // Update how long this bullet has been alive
            float newLifetime = bulletTimers.getOrDefault(bullet, 0f) + gameData.getDelta();
            bulletTimers.put(bullet, newLifetime);

            // Mark bullets that lived too long for removal
            if (newLifetime >= MAX_LIFETIME) {
                expiredBullets.add(bullet);
            }
        }

        // remove expired bullets
        for (Entity expired : expiredBullets) {
            bulletTimers.remove(expired);
            world.removeEntity(expired);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity newBullet = new Bullet();

        // bullet shape
        newBullet.setPolygonCoordinates(0, -2, 1, -1, 1, 1, 0, 2, -1, 1, -1, -1);


        double angle = Math.toRadians(shooter.getRotation());
        newBullet.setX(shooter.getX() + Math.cos(angle) * 3);
        newBullet.setY(shooter.getY() + Math.sin(angle) * 3);

        newBullet.setRotation(shooter.getRotation());
        newBullet.setRadius(1); // For collision detection

        newBullet.setColor(Color.YELLOW); // Bullet color

        // Start the bullet's lifetime timer at 0
        bulletTimers.put(newBullet, 0f);

        return newBullet;
    }
}
