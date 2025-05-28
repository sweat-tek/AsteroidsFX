package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class PositionPart implements EntityPart{

    private double x;
    private double y;
    private double radians;

    public PositionPart(double x, double y, double radians) {
        this.x = x;
        this.y = y;
        this.radians = radians;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public double getRadians(){
        return radians;
    }
    public void setX(double newX){
        this.x = newX;
    }
    public void setY(double newY){
        this.y = newY;
    }

    public void setPosition(double newX, double newY){
        this.x = newX;
        this.y = newY;
    }

    public void setRadians(double radians){
        this.radians = radians;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
