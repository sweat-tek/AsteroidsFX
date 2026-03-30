module Asteroid {
    requires Common;
    requires CommonAsteroids;
    requires javafx.graphics;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
            with dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;

    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService
            with dk.sdu.mmmi.cbse.asteroid.AsteroidProcessor,
                    dk.sdu.mmmi.cbse.asteroid.AsteroidControlSystem;

    provides dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter
            with dk.sdu.mmmi.cbse.asteroid.AsteroidSplitterImpl;
}
