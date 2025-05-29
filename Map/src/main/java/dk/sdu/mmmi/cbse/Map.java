package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.services.MapService;

public class Map  implements MapService {
    @Override
    public String getMap(){
        return "assets/AsteroidsBackground.jpg";
    }

    public String getSoundTrack(){
        return null;
    }
}
