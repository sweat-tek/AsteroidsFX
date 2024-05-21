package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface represents a game plugin service for starting and stopping game functionalities.
 */
public interface IGamePluginService {

    /**
     * Launches the game plugin application
     *
     * @param gameData the current game data
     * @param world the game world

     * @pre gameData and world must not be null
     * @post the game plugin service is started and prepared to carry out functionalities
     */
    void start(GameData gameData, World world);

    /**
     * halts and clears the resources that the game plugin is using.
     *
     * @param gameData the current game data containing information about the game
     * @param world the game world that includes numerous game components and entities.
     * @pre Plugin is running
     * @post the game plugin service is stopped
     */
    void stop(GameData gameData, World world);
}