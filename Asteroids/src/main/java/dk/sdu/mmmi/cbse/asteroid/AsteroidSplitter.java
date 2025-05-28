package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidEffect;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AsteroidSplitter implements IAsteroidEffect {

    @Override
    public boolean createSplitAsteroid(Entity e, World world) {
        PositionPart otherPos = e.getPart(PositionPart.class);
        LifePart otherLife = e.getPart(LifePart.class);
        MovingPart otherMoving = e.getPart(MovingPart.class);

        double radians = otherPos.getRadians();
        if (otherLife.getLife() <= 0){
            world.removeEntity(e);
            return false;
        }
        Entity asteroidOne = new Asteroid();

        double newRadius = e.getRadius() / 2;
        PositionPart positionPart = newAsteroidPositions(otherPos, newRadius);

        e.setRadius(newRadius);
        asteroidOne.setRadius(newRadius);
        double speed = otherMoving.getMaxSpeed() / 2;
        asteroidOne.setRadius(newRadius);
        otherMoving.setMaxSpeed(speed);

        asteroidOne.add(new MovingPart(0, 6000, speed, 0));
        asteroidOne.add(positionPart);
        asteroidOne.add(new LifePart(otherLife.getLife(), 5));

        world.addEntity(asteroidOne);
        return true;
    }
    private PositionPart newAsteroidPositions(PositionPart oldPosition, double newRadius) {
        double newRadians = oldPosition.getRadians() - 1.0;
        oldPosition.setRadians(oldPosition.getRadians() + 1.0);
        double by1 = sin(newRadians) * newRadians;
        double bx1 = cos(newRadians) * newRadians;

        return new PositionPart(oldPosition.getX() + bx1,oldPosition.getY() + by1, newRadians);
    }

}
