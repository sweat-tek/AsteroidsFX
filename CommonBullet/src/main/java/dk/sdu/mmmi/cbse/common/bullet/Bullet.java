package dk.sdu.mmmi.cbse.common.bullet;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Bullet extends Entity {
    public Bullet() {
        // Create a larger, more visible bullet shape (elongated triangle)
        setPolygonCoordinates(
            -4, 4,   // left point
             0, -8,  // front point (twice as long)
             4, 4    // right point
        );
        
        // Set a larger collision radius
        setRadius(4);
    }
}
