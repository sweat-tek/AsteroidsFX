package dk.sdu.mmmi.cbse.core.plugin.impl;

import dk.sdu.mmmi.cbse.core.plugin.PluginService;
import org.springframework.stereotype.Service;

/**
 * Core module implementation of the PluginService interface
 */
@Service
public class CorePluginServiceImpl implements PluginService {
    
    @Override
    public String getName() {
        return "Core Plugin Service";
    }
    
    @Override
    public String getDescription() {
        return "This is the implementation from the Core module";
    }
    
    @Override
    public String performAction() {
        return "Core module performed the action!";
    }
}

