package dk.sdu.mmmi.cbse.main;

import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles dynamic loading of plugin modules using the ModuleLayer API.
 * This class demonstrates how to handle split packages and dynamic service loading
 * in a modular Java application.
 */
public class PluginLoader {
    // Directory where plugin modules are located
    private static final String PLUGIN_DIR = "plugin-modules";
    private ModuleLayer pluginLayer;
    
    /**
     * Initializes the plugin layer by finding and loading all modules from 
     * the plugin-modules directory
     */
    public void initializePluginLayer() {
        try {
            // Create the plugins directory if it doesn't exist
            Path pluginsDir = Paths.get(PLUGIN_DIR);
            if (!Files.exists(pluginsDir)) {
                Files.createDirectories(pluginsDir);
                System.out.println("Created plugin directory: " + pluginsDir.toAbsolutePath());
            }
            
            // Find all modules in the plugins directory
            ModuleFinder pluginFinder = ModuleFinder.of(pluginsDir);
            
            // Get the set of plugin module names
            Set<String> pluginModules = pluginFinder.findAll().stream()
                    .map(ModuleReference::descriptor)
                    .map(descriptor -> descriptor.name())
                    .collect(Collectors.toSet());
            
            if (pluginModules.isEmpty()) {
                System.out.println("No plugin modules found in " + pluginsDir.toAbsolutePath());
                return;
            }
            
            System.out.println("Found plugin modules: " + pluginModules);
            
            // Get the current module layer (boot layer)
            ModuleLayer bootLayer = ModuleLayer.boot();
            Configuration bootConfig = bootLayer.configuration();
            
            // Create a new configuration that adds the plugin modules to the boot configuration
            Configuration pluginConfig = bootConfig.resolve(
                    pluginFinder,           // Finder for the modules we want to add
                    ModuleFinder.of(),     // Finder for modules to resolve any dependencies (empty as we resolve against boot)
                    pluginModules           // Names of modules we want to add
            );
            
            // Create a new layer with the plugin modules
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            pluginLayer = bootLayer.defineModulesWithOneLoader(pluginConfig, systemClassLoader);
            
            System.out.println("Plugin layer created successfully");
            System.out.println("Plugin layer contains modules: " + 
                    pluginLayer.modules().stream()
                            .map(m -> m.getName())
                            .collect(Collectors.joining(", ")));
            
            // Debug information about modules and their exported packages
            pluginLayer.modules().forEach(module -> {
                System.out.println("Module: " + module.getName() + " provides:");
                module.getDescriptor().provides().forEach(provides -> {
                    System.out.println("  - Service: " + provides.service());
                    provides.providers().forEach(provider -> {
                        System.out.println("    with: " + provider);
                    });
                });
            });
            
        } catch (IOException e) {
            System.err.println("Error initializing plugin layer: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error during plugin layer initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Loads services of the specified type from both the boot layer and the plugin layer.
     * This method demonstrates how to handle services across module layers, including
     * from different modules that might contain split packages.
     * 
     * @param <T> The service type
     * @param serviceType The class object representing the service type
     * @return A list of service instances from both core and plugin modules
     */
    public <T> List<T> loadServices(Class<T> serviceType) {
        List<T> services = new ArrayList<>();
        Set<String> loadedServiceClasses = new HashSet<>(); // Track loaded service class names
        
        try {
            // Load services from the boot layer (core module)
            System.out.println("Loading " + serviceType.getSimpleName() + " services from boot layer...");
            ServiceLoader<T> bootLayerServices = ServiceLoader.load(serviceType);
            for (T service : bootLayerServices) {
                String serviceClass = service.getClass().getName();
                if (loadedServiceClasses.add(serviceClass)) { // Will only add if not present
                    System.out.println("Found service in boot layer: " + serviceClass + 
                                  " from module: " + service.getClass().getModule().getName());
                    services.add(service);
                }
            }
            
            // Load services from the plugin layer if it exists
            if (pluginLayer != null) {
                System.out.println("Loading " + serviceType.getSimpleName() + " services from plugin layer...");
                ServiceLoader<T> pluginLayerServices = ServiceLoader.load(pluginLayer, serviceType);
                for (T service : pluginLayerServices) {
                    String serviceClass = service.getClass().getName();
                    if (loadedServiceClasses.add(serviceClass)) { // Will only add if not present
                        System.out.println("Found service in plugin layer: " + serviceClass + 
                                      " from module: " + service.getClass().getModule().getName());
                        services.add(service);
                    }
                }
            }
            
            if (services.isEmpty()) {
                System.out.println("WARNING: No " + serviceType.getSimpleName() + " services were found!");
                System.out.println("Class being loaded: " + serviceType.getName());
                System.out.println("Module of class: " + serviceType.getModule().getName());
            }
            
            System.out.println("Total " + serviceType.getSimpleName() + " services loaded: " + services.size());
        } catch (Exception e) {
            System.err.println("Error loading services for " + serviceType.getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        
        return services;
    }
    
    /**
     * Gets the plugin ModuleLayer if it has been initialized
     * 
     * @return The plugin ModuleLayer, or null if not initialized
     */
    public ModuleLayer getPluginLayer() {
        return pluginLayer;
    }
    
    /**
     * Checks if the plugin layer has been successfully initialized
     * 
     * @return true if the plugin layer exists, false otherwise
     */
    public boolean isPluginLayerInitialized() {
        return pluginLayer != null;
    }
}
