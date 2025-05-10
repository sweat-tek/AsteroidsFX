package dk.sdu.mmmi.cbse.main;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author jcs
 */
@Configuration
class ModuleConfig {
    
    private final PluginLoader pluginLoader;
    
    public ModuleConfig() {
        // Initialize the plugin loader
        this.pluginLoader = new PluginLoader();
        this.pluginLoader.initializePluginLayer();
    }

    @Bean
    public PluginLoader pluginLoader() {
        return pluginLoader;
    }
    
    @Bean
    public Game game(){
        return new Game(gamePluginServices(), entityProcessingServiceList(), postEntityProcessingServices());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServiceList(){
        return pluginLoader.loadServices(IEntityProcessingService.class);
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return pluginLoader.loadServices(IGamePluginService.class);
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return pluginLoader.loadServices(IPostEntityProcessingService.class);
    }
    
    /**
     * This bean demonstrates the split package scenario using the ModuleLayer system.
     * It will load both the local PluginService implementation from the Core module
     * and the dynamically loaded implementation from the Bullet plugin module.
     * 
     * @return List of all PluginService implementations across module layers
     */
    @Bean
    public List<dk.sdu.mmmi.cbse.core.plugin.PluginService> pluginServices() {
        // This loads both Core and plugin module implementations
        return pluginLoader.loadServices(dk.sdu.mmmi.cbse.core.plugin.PluginService.class);
    }
}
