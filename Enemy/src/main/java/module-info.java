import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.EnemyControlSystem;
import dk.sdu.mmmi.cbse.EnemyPlugin;

module Enemy {
    requires Enemy;
    requires CommonEnemy;
    requires Common;
    provides IEntityProcessingService with EnemyControlSystem;
    provides IGamePluginService with EnemyPlugin;
}