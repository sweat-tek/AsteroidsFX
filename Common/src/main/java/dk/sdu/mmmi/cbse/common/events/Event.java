package dk.sdu.mmmi.cbse.common.events;

import dk.sdu.mmmi.cbse.common.data.Entity;
import java.io.Serializable;

public class Event implements Serializable {
    private final Entity src;

    public Event(Entity src) {
        this.src = src;
    }

    public Entity getSrc() {
        return src;
    }
}
