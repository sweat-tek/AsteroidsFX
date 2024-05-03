import dk.sdu.mmmi.cbse.asteroid.AsterioidControlSystem;
import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Asteroid {
    exports dk.sdu.mmmi.cbse.asteroid;
    requires Common;
    requires CommonAsteroids;
    provides IGamePluginService with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
    provides AsteroidSPI with AsterioidControlSystem;
    provides IEntityProcessingService with AsterioidControlSystem;
}