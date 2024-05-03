package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author corfixen
 */
public interface BulletSPI {

    /**
     * Creates a bullet
     * @param gameData e
     * @param e gameData
     * @precondition entity is not null and gameData is not null
     * @postcondition  bullet is added to the world, and fired in the direction of the entity that fires it.
     */
    Entity createBullet(Entity e, GameData gameData);
}