package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * An interface for plugin-services to the game, such as Player and Bullet. Defines the methods start and stop for the plugin-services' start-up and shut-down logic.
 */
public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
