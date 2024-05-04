import dk.sdu.mmmi.cbse.common.asteroids.AsteroidSPI;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collisions {
    requires Common;
    requires Asteroids;
    requires CommonAsteroids;
    provides IPostEntityProcessingService with dk.sdu.mmmi.cbse.collision.CollisionDetectionSystem;

}