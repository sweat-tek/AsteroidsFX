import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;
    requires Player;
    
    provides IPostEntityProcessingService 
        with dk.sdu.mmmi.cbse.collisionsystem.CollisionDetector;
        
    uses dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
}
