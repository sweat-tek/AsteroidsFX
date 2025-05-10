module Player {
    requires Common;
    requires CommonBullet;
    
    exports dk.sdu.mmmi.cbse.playersystem;
    
    provides dk.sdu.mmmi.cbse.common.services.IGamePluginService 
        with dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
        
    provides dk.sdu.mmmi.cbse.common.services.IEntityProcessingService 
        with dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
    
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
}
