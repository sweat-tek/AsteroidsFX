import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Enemy {
    requires Common;
    requires javafx.graphics;

    provides IGamePluginService with dk.sdu.mmmi.enemy.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.mmmi.enemy.EnemyControlSystem;

}