package dk.sdu.mmmi.cbse.asteroid;


import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


public class Asteroid extends Entity {

    private int asteroidSize;

    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();

    @Override
    public void handleCollision(GameData gameData, World world, Entity collidingEntity) {

        if(getAsteroidSize() == 1) {
            world.removeEntity(collidingEntity);
            world.removeEntity(this);
        } else if (getAsteroidSize() == 2) {
            Entity asteroidChild1 = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setNewPolygonCoordinates(asteroidChild1, 1);
            asteroidChild1.setX(this.getX());
            asteroidChild1.setY(this.getY());
            asteroidChild1.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild1);

            Entity asteroidChild2 = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setNewPolygonCoordinates(asteroidChild2, 1);
            asteroidChild2.setX(this.getX());
            asteroidChild2.setY(this.getY());
            asteroidChild2.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild2);

            world.removeEntity(collidingEntity);
            world.removeEntity(this);
        } else {
            Entity asteroidChild1 = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setNewPolygonCoordinates(asteroidChild1, 2);
            asteroidChild1.setX(this.getX());
            asteroidChild1.setY(this.getY());
            asteroidChild1.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild1);

            Entity asteroidChild2 = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setNewPolygonCoordinates(asteroidChild2, 2);
            asteroidChild2.setX(this.getX());
            asteroidChild2.setY(this.getY());
            asteroidChild2.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild2);

            world.removeEntity(collidingEntity);
            world.removeEntity(this);
        }
    }

    public int getAsteroidSize() {
        return asteroidSize;
    }

    public void setAsteroidSize(int asteroidSize) {
        this.asteroidSize = asteroidSize;
    }
}