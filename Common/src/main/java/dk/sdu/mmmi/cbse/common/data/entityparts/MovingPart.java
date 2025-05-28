package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

import static java.lang.Math.*;

public class MovingPart implements EntityPart {

    private double dx, dy;
    private double deceleration, acceleration;
    private double maxSpeed, rotationSpeed;
    private boolean left, right, up;

    public MovingPart(double deceleration, double acceleration, double maxSpeed, double rotationSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
        this.rotationSpeed = rotationSpeed;
    }
    public double getDx(){
        return dx;
    }
    public double getDy(){
        return dy;
    }

    public void setDeceleration(double deceleration){
        this.deceleration = deceleration;
    }
    public void setAcceleration(double acceleration){
        this.acceleration = acceleration;
    }
    public void setMaxSpeed(double maxSpeed){
        this.maxSpeed = maxSpeed;
    }
    public void setRotationSpeed(double rotationSpeed){
        this.rotationSpeed = rotationSpeed;
    }
    public void setSpeed(double speed){
        this.maxSpeed = speed;
        this.acceleration = speed;
    }
    public void setLeft(boolean left){
        this.left = left;
    }
    public void setRight(boolean right){
        this.right = right;
    }
    public void setUp(boolean up){
        this.up = up;
    }
    public double getMaxSpeed(){
        return maxSpeed;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        posPostionPart postionPart = entity.getPart(PositionPart.class);
        double x = postionPart.getX();
        double y = postionPart.getY();
        double radians = postionPart.getRadians();
        float dt = gameData.getDelta();

        // controls turning
        if (left){
            radians += rotationSpeed * dt;
        }

        if (right){
            radians -= rotationSpeed * dt;
        }

        // controls acc
        if (up){
            dx += cos(radians) * acceleration * dt;
            dy += sin(radians) * acceleration * dt;
        }

        // controls dec
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0){
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed){
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }
        x += dx * dt;
        if (x > gameData.getDisplayWidth()){
            x = 0;
        } else if (x < 0){
            x = gameData.getDisplayWidth();
        }
        y += dy * dt;
        if (y > gameData.getDisplayHeight()){
            y = 0;
        } else if (y < 0){
            y = gameData.getDisplayHeight();
        }

        postionPart.setX(x);
        postionPart.setY(y);

        postionPart.setRadians(radians);
    }
}
