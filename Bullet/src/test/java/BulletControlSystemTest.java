package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BulletControlSystemTest {

    private final BulletControlSystem system = new BulletControlSystem();

    @Test
    void createsBulletCorrectly() {
        Entity shooter = new Entity();
        shooter.setX(100);
        shooter.setY(200);
        shooter.setRotation(90);

        Entity bullet = system.createBullet(shooter, new GameData());

        assertNotNull(bullet);
        assertEquals(90, bullet.getRotation(), 0.1);
        assertEquals(1, bullet.getRadius(), 0.1);
        assertTrue(bullet.getY() > 200); // moving down if rotation is 90 degrees
    }
}
