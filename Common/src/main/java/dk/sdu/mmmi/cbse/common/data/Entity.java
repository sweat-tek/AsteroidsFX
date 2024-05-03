package dk.sdu.mmmi.cbse.common.data;

import java.io.Serializable;
import java.util.UUID;


public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    public enum entityType {
        PLAYER, ENEMY, BULLET, ASTEROID
    }

    private entityType entityType;

    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;

    private int health;

    public Entity(int health, entityType entityType) {
        this.health = health;
        this.entityType = entityType;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public entityType getEntityType() {
        return entityType;
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


    public double calculateWidth() {
        if (polygonCoordinates == null || polygonCoordinates.length < 4) {
            return 0; // Not enough points to form a polygon
        }
        double minX = polygonCoordinates[0];
        double maxX = polygonCoordinates[0];
        for (int i = 0; i < polygonCoordinates.length; i += 2) {
            double x = polygonCoordinates[i];
            if (x < minX) minX = x;
            if (x > maxX) maxX = x;
        }
        return maxX - minX;
    }

    public double calculateHeight() {
        if (polygonCoordinates == null || polygonCoordinates.length < 4) {
            return 0; // Not enough points to form a polygon
        }
        double minY = polygonCoordinates[1];
        double maxY = polygonCoordinates[1];
        for (int i = 1; i < polygonCoordinates.length; i += 2) {
            double y = polygonCoordinates[i];
            if (y < minY) minY = y;
            if (y > maxY) maxY = y;
        }
        return maxY - minY;
    }

    public boolean intersects(Entity other) {
        // Figure out if they intersect based on height, width and position
        double thisWidth = this.calculateWidth();
        double thisHeight = this.calculateHeight();
        double otherWidth = other.calculateWidth();
        double otherHeight = other.calculateHeight();
        double thisX = this.getX();
        double thisY = this.getY();
        double otherX = other.getX();
        double otherY = other.getY();
        return thisX < otherX + otherWidth &&
                thisX + thisWidth > otherX &&
                thisY < otherY + otherHeight &&
                thisY + thisHeight > otherY;
    }



}
