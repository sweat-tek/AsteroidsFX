package dk.sdu.mmmi.cbse.bullet.plugin.impl;

import dk.sdu.mmmi.cbse.core.plugin.PluginService;
import org.springframework.stereotype.Service;

/**
 * Bullet module implementation of the PluginService interface
 * This demonstrates the dynamic loading of services from plugin modules
 */
@Service
public class BulletPluginServiceImpl implements PluginService {
    
    @Override
    public String getName() {
        return "Bullet Plugin Service";
    }
    
    @Override
    public String getDescription() {
        return "This is the implementation from the Bullet plugin module";
    }
    
    @Override
    public String performAction() {
        return "Bullet plugin module performed the action! (Dynamically loaded)";
    }
}

