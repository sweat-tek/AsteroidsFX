package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.GameKeys;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) { // The behaviour of the bullet
        for (Entity bullet : world.getEntities(Bullet.class)) { // Movement of the bullet, stolen from PlayerControlSystem's reaction to a press of the "up" key.
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));
            bullet.setX(bullet.getX() + changeX);
            bullet.setY(bullet.getY() + changeY);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) { // Takes the entity which fires the bullet, and gets the gameData injected.
        // Get the coordinates of the shooter.
        double shooterX = shooter.getX();
        double shooterY = shooter.getY();
        double shooterRotation = shooter.getRotation();

        Entity bullet = new Bullet(); // Create a new bullet entity.
        bullet.setPolygonCoordinates(-5, -5, 10, 0, -5, 5); // TODO: Make the bullet's shape more bullet-like.
        bullet.setX(shooterX); // Set the bullet's x-coordinate to the shooter's x-coordinate.
        bullet.setY(shooterY); // Set the bullet's y-coordinate to the shooter's y-coordinate.
        bullet.setRotation(shooterRotation); // Set the bullet's rotation to the shooter's rotation.
        return bullet; // Return the bullet entity.
    }

    private void setShape(Entity entity) {
    }

}
