import dk.sdu.mmmi.cbse.collision.CollisionSystem;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collisions {
    requires Common;
    requires Asteroid;
    requires CommonBullet;
    requires Player;
    requires EnemySpaceship;

    provides IPostEntityProcessingService with CollisionSystem;

}