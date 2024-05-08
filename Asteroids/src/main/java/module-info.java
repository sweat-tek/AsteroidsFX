import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.asteroid.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;

module Asteroid {
    exports dk.sdu.mmmi.cbse.asteroid;
    requires Common;
    requires CommonBullet;
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    provides IGamePluginService with AsteroidPlugin;
    provides IEntityProcessingService with AsteroidControlSystem;

}