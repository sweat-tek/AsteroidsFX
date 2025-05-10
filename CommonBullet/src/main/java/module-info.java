module CommonBullet {
    requires Common;
    exports dk.sdu.mmmi.cbse.common.bullet;
    
    provides dk.sdu.mmmi.cbse.common.bullet.BulletSPI 
        with dk.sdu.mmmi.cbse.common.bullet.BulletPlugin;
    
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService 
        with dk.sdu.mmmi.cbse.common.bullet.BulletControlSystem;

    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService
        with dk.sdu.mmmi.cbse.common.bullet.BulletGamePlugin;
}
