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
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            bullet.setX(bullet.getX() + Math.cos(Math.toRadians(bullet.getRotation())) * 5);
            bullet.setY(bullet.getY() + Math.sin(Math.toRadians(bullet.getRotation())) * 5);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        System.out.println("creating bullet");
        // New instance of the bullet
        Bullet bullet = new Bullet();

        bullet.setX(shooter.getX());
        bullet.setY(shooter.getY());

        bullet.setPolygonCoordinates(0,0,0,5,5,1,0,0);
        bullet.setRotation(shooter.getRotation());

        return bullet;
    }

    private void setShape(Entity entity) {
    }

}
