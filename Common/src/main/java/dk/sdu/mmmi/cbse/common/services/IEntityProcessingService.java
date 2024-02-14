package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IEntityProcessingService {

    /**
     * "Process" the game data and world. Is used to update the state (execute the behaviour) of plug-in entities, like Player and Bullet.
     *
     * @param gameData
     * @param world
     */
    void process(GameData gameData, World world);
}
