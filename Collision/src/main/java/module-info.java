import dk.sdu.mmmi.cbse.common.asteroid.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collisions {
    requires Common;
    requires Asteroids;
    requires CommonAsteroids;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collisions.CollisionDetectionSystem;

}