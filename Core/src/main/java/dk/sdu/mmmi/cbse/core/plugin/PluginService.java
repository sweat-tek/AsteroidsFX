package dk.sdu.mmmi.cbse.core.plugin;

/**
 * Interface for demonstrating plugin functionality between modules
 */
public interface PluginService {
    
    /**
     * Get the name of the implementation
     * @return The implementation name
     */
    String getName();
    
    /**
     * Get a description of the implementation
     * @return The implementation description
     */
    String getDescription();
    
    /**
     * Perform some action specific to the implementation
     * @return Result of the action
     */
    String performAction();
}

