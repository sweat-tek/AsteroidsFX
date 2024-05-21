package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface indicates a post-entity processing service in the game.
 */
public interface IPostEntityProcessingService {

    /**
     * Processes entities in the game world after the main entity processing.
     *
     * @param gameData the current game data
     * @param world the game world containing entities
     * @pre gameData and world must not be null
     * @post entities in the world may be modified based on the post-processing logic
     */
    void process(GameData gameData, World world);

    /**
     * Executes post-processing operations on entities in the game world.
     *
     * @param gameData the current game data
     * @param world the game world containing entities
     * @pre gameData and world must not be null
     * @post additional post-processing operations may be applied to entities or game state
     */
    void postProcess(GameData gameData, World world);
}