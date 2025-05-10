package dk.sdu.mmmi.cbse.main;

import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import dk.sdu.mmmi.cbse.core.plugin.PluginService;
import java.util.List;

public class Main extends Application {
    
    private AnnotationConfigApplicationContext ctx;
    
    @Override
    public void start(Stage window) throws Exception {
        System.out.println("=====================================");
        System.out.println("Starting AsteroidsFX game with dynamic plugin loading...");
        System.out.println("=====================================");
        
        // Create plugin-modules directory if it doesn't exist
        Path pluginDir = Paths.get("plugin-modules");
        if (!Files.exists(pluginDir)) {
            Files.createDirectories(pluginDir);
        }
        
        try {
            System.out.println("\nInitializing Spring context...");
            ctx = new AnnotationConfigApplicationContext(ModuleConfig.class);

            // Demonstrate the split package scenario with plugin services
            demonstratePluginServices();
            
            System.out.println("\nRegistered Spring beans:");
            for (String beanName : ctx.getBeanDefinitionNames()) {
                System.out.println(" - " + beanName);
            }
            
            Game game = ctx.getBean(Game.class);
            game.start(window);
            game.render();
            
        } catch (Exception e) {
            System.err.println("Error initializing application: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
    
    /**
     * Demonstrates the PluginService implementations from both Core and dynamically loaded plugins.
     * This method shows how the ModuleLayer API handles the split package scenario.
     */
    private void demonstratePluginServices() {
        System.out.println("\n=====================================");
        System.out.println("DEMONSTRATING PLUGIN SYSTEM AND SPLIT PACKAGE HANDLING");
        System.out.println("=====================================");
        
        try {
            // Get the plugin services from Spring context (includes both Core and Bullet implementations)
            List<PluginService> pluginServices = 
                    ctx.getBean("pluginServices", List.class);
            
            if (pluginServices.isEmpty()) {
                System.out.println("No plugin services found!");
                return;
            }
            
            System.out.println("\nFound " + pluginServices.size() + " Plugin Service implementations:");
            
            // Call methods on each plugin service to demonstrate they work
            for (PluginService service : pluginServices) {
                System.out.println("\n* Plugin Service: " + service.getClass().getName());
                System.out.println("  - Name: " + service.getName());
                System.out.println("  - Description: " + service.getDescription());
                System.out.println("  - Action result: " + service.performAction());
                System.out.println("  - Module: " + service.getClass().getModule().getName());
            }
            
            System.out.println("\nDemonstration of split package handling complete!");
            System.out.println("=====================================\n");
            
        } catch (Exception e) {
            System.err.println("Error during plugin service demonstration: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
