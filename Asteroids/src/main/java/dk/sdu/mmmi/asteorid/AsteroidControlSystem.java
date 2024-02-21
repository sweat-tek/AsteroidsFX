package dk.sdu.mmmi.asteorid;

import dk.sdu.mmmi.asteorids.Asteorid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

    }

    public Entity createAsteorid (Entity entity, GameData gameData) {
        Asteorid asteorid = new Asteorid();

        return asteorid;
    }
}
