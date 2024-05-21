package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface represents a service for processing entities in the game world.
 */
public interface IEntityProcessingService {

    /**
     * Processes entities in the game world
     *
     * @param gameData the current game data
     * @param world the game world containing entities
     * @pre All processes are completed and the application is running.
     * @post The processing logic has the ability to change entities in the world.
     */
    void process(GameData gameData, World world);

    /**
     * Executes post-processing operations on entities in the game world.
     *
     * @param gameData the current game data
     * @param world the game world containing entities
     * @pre All processes are completed, and the application is running.
     * @post The post-processing logic may further alter entities or game state as required.
     */
    void postProcess(GameData gameData, World world);
}