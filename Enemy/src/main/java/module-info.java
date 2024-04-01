import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Enemy {
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyControlle;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.enemysystem.EnemyNewProceses;
}