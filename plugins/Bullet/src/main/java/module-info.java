import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.core.plugin.PluginService;

module Bullet {
    requires Common;
    requires CommonBullet;
    requires Core;
    requires spring.context;
    requires spring.beans;
    requires spring.core;
    
    // Exports for plugin features
    exports dk.sdu.mmmi.cbse.bullet.plugin.impl;
    exports dk.sdu.mmmi.cbse.bulletsystem;
    
    // Open packages for Spring reflection
    opens dk.sdu.mmmi.cbse.bulletsystem to spring.core, spring.beans, spring.context;
    opens dk.sdu.mmmi.cbse.bullet.plugin.impl to spring.core, spring.beans, spring.context;
    
    // Service provider declarations
    provides PluginService 
        with dk.sdu.mmmi.cbse.bullet.plugin.impl.BulletPluginServiceImpl;
    
    provides IGamePluginService 
        with dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
    provides IEntityProcessingService 
        with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
    provides BulletSPI 
        with dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
}
