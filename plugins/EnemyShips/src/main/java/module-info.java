module Enemy {
    exports dk.sdu.mmmi.cbse.enemies;

    requires Common;
    requires CommonBullet;
    requires Bullet;
    requires javafx.graphics;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
            with dk.sdu.mmmi.cbse.enemies.EnemyPlugin;

    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService
            with dk.sdu.mmmi.cbse.enemies.EnemyControlSystem;

    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
}
