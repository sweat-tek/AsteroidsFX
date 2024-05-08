package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;
import javafx.scene.paint.Paint;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private double size;
    private double health;
    private Paint paint;
    private Boolean dead = false;

    public Paint getPaint() { return paint; }

    public void setPaint(Paint paint) { this.paint = paint; }

    public Boolean getDead() { return dead; }

    public void setDead(Boolean dead) { this.dead = dead; }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getID() {
        return ID.toString();
    }

    public void setPolygonCoordinates(double... coordinates ) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(double x) {
        this.x =x;
    }

    public double getX() {
        return x;
    }
    
    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setRotation(double rotation) {
        this.rotation = rotation;
    }

    public double getRotation() {
        return rotation;
    }

    public double getHealth() { return health; }

    public void setHealth(double health) { this.health = health; }
}
