module Core {
    requires Common;
    requires CommonBullet;    
    requires javafx.graphics;   
    requires spring.context;
    requires spring.core;
    requires spring.beans;
    requires spring.web;
    
    exports dk.sdu.mmmi.cbse.main;
    exports dk.sdu.mmmi.cbse.core.plugin;
    exports dk.sdu.mmmi.cbse.core.plugin.impl;
    
    opens dk.sdu.mmmi.cbse.main to javafx.graphics,spring.core;
    opens dk.sdu.mmmi.cbse.core.plugin to spring.core, spring.beans, spring.context;
    opens dk.sdu.mmmi.cbse.core.plugin.impl to spring.core, spring.beans, spring.context;
    
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
    uses dk.sdu.mmmi.cbse.core.plugin.PluginService; 
    uses dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
    
    provides dk.sdu.mmmi.cbse.core.plugin.PluginService 
        with dk.sdu.mmmi.cbse.core.plugin.impl.CorePluginServiceImpl;
}


