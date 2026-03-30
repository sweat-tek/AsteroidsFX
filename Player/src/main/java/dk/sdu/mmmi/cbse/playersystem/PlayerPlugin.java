package dk.sdu.mmmi.cbse.playersystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import javafx.scene.paint.Color;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData data, World world) {
        player = initPlayer(data);
        player.setColor(Color.DARKGRAY);
        world.addEntity(player);
    }

    @Override
    public void stop(GameData data, World world) {
        world.removeEntity(player);
    }

    private Entity initPlayer(GameData data) {
        Entity p = new Player();
        p.setPolygonCoordinates(-6, -5, 10, 0, -6, 5);
        p.setX(data.getDisplayWidth() * 0.5f);
        p.setY(data.getDisplayHeight() * 0.5f);
        p.setRadius(8);
        return p;
    }
}
