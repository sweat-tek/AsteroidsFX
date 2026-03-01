package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.bullet.Bullet;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.enemysystem.Enemy;
import dk.sdu.mmmi.cbse.playersystem.Player;

import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private static final IAsteroidSplitter splitter = ServiceLoader.load(IAsteroidSplitter.class).iterator().next();

    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {

                // if the two entities are identical, skip the iteration
                if (entity1.getID().equals(entity2.getID())) {
                    continue;                    
                }

                // CollisionDetection
                if (this.collides(entity1, entity2)) {
                    int tempHealth = entity1.getHealth();
                    entity1.setHealth(entity1.getHealth() - entity2.getHealth());
                    entity2.setHealth(entity2.getHealth() - tempHealth);
                    if (entity1 instanceof Asteroid) {
                        if(entity1.getHealth()<=0) {
                            splitter.createSplitAsteroid(entity1, world);
                            System.out.println("split e1 as: " + entity1.getID());
                            world.removeEntity(entity1);
                        } else {
                            entity1.setRotation(entity1.getRotation() + entity2.getRotation());
                        }
                    } else if (entity1 instanceof Player && entity1.getHealth()<=0) {
                        System.out.println("You Died!");
                        System.exit(400);
                    } else if (entity1 instanceof Enemy) {
                        if(entity1.getHealth()<=0) {
                            world.removeEntity(entity1);
                        }
                    } else if (entity1 instanceof Bullet) {
                        if(entity1.getHealth()<=0) {
                            world.removeEntity(entity1);
                        }
                    }
                    if (entity2 instanceof Asteroid) {
                        if(entity2.getHealth()<=0) {
                            splitter.createSplitAsteroid(entity2, world);
                            System.out.println("split e2 as: " + entity2.getID());
                            world.removeEntity(entity2);
                        } else {
                            entity2.setRotation(entity1.getRotation() + entity1.getRotation());
                        }
                    }  else if (entity2 instanceof Player && entity2.getHealth()<=0) {
                        System.out.println("You Died!");
                        System.exit(400);
                    } else if (entity2 instanceof Enemy) {
                        if(entity2.getHealth()<=0) {
                            world.removeEntity(entity2);
                        }
                    } else if (entity2 instanceof Bullet) {
                        if(entity2.getHealth()<=0) {
                            world.removeEntity(entity2);
                        }
                    }
                }
            }
        }

    }

    public Boolean collides(Entity entity1, Entity entity2) {
        float dx = (float) entity1.getX() - (float) entity2.getX();
        float dy = (float) entity1.getY() - (float) entity2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

}
