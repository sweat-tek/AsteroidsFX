module Bullet {
    requires Common;
    requires CommonBullet;
    requires javafx.graphics;

    exports dk.sdu.mmmi.cbse.bulletsystem;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
            with dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;

    provides dk.sdu.mmmi.cbse.common.bullet.BulletSPI
            with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;

    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService
            with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
}
