
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.enemy.EnemySpaceshipControlSystem;
import dk.sdu.mmmi.cbse.enemy.EnemySpaceshipPlugin;

module EnemySpaceship {
    exports dk.sdu.mmmi.cbse.enemy;
    requires Common;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with EnemySpaceshipPlugin;
    provides IEntityProcessingService with EnemySpaceshipControlSystem;
}
    