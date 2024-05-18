package dk.sdu.mmmi.cbse.asteroid;


import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;


public class Asteroid extends Entity {

    private int asteroidSize;

    private AsteroidPlugin asteroidPlugin = new AsteroidPlugin();

    @Override
    public void handleCollision(GameData gameData, World world, Entity collidingEntity) {
        switch (getAsteroidSize()) {
            case 1:
                handleAsteroidSizeOne(world, collidingEntity);
                break;
            case 2:
                handleAsteroidSizeTwo(gameData, world, collidingEntity);
                break;
            default:
                handleDefaultAsteroidSize(gameData, world, collidingEntity);
                break;
        }
    }

    private void handleAsteroidSizeOne(World world, Entity collidingEntity) {
        world.removeEntity(collidingEntity);
        world.removeEntity(this);
    }

    private void handleAsteroidSizeTwo(GameData gameData, World world, Entity collidingEntity) {
        for (int i = 0; i < 2; i++) {
            Entity asteroidChild = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setAsteroidCoordinates(asteroidChild, 1);
            asteroidChild.setX(this.getX());
            asteroidChild.setY(this.getY());
            asteroidChild.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild);
        }
        world.removeEntity(collidingEntity);
        world.removeEntity(this);
    }

    private void handleDefaultAsteroidSize(GameData gameData, World world, Entity collidingEntity) {
        for (int i = 0; i < 2; i++) {
            Entity asteroidChild = asteroidPlugin.createAsteroid(gameData);
            asteroidPlugin.setAsteroidCoordinates(asteroidChild, 2);
            asteroidChild.setX(this.getX());
            asteroidChild.setY(this.getY());
            asteroidChild.setRotation(Math.random() * 360);
            world.addEntity(asteroidChild);
        }
        world.removeEntity(collidingEntity);
        world.removeEntity(this);
    }

    public int getAsteroidSize() {
        return asteroidSize;
    }

    public void setAsteroidSize(int asteroidSize) {
        this.asteroidSize = asteroidSize;
    }
}