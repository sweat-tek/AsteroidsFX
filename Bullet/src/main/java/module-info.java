import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;
    provides IGamePluginService with dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
    provides BulletSPI with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
}