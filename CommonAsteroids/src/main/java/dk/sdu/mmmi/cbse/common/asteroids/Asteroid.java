package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 * Asteroid entity with size information
 * @author corfixen
 */
public class Asteroid extends Entity {
    public enum Size {
        LARGE(30, 3), 
        MEDIUM(20, 2), 
        SMALL(10, 1);
        
        public final int size;
        public final int points;
        
        Size(int size, int points) {
            this.size = size;
            this.points = points;
        }
    }
    
    private Size size;
    
    public Size getSize() {
        return size;
    }
    
    public void setSize(Size size) {
        this.size = size;
    }
}
