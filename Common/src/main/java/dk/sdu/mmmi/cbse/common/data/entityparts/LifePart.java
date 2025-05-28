package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class LifePart implements EntityPart {

    private boolean dead = false;
    private int life;
    private boolean isHit = false;

    private double iFrameConstant = 2;

    private double iFrames;

    public LifePart(int life) {
        this.life = life;
    }
    public LifePart(int life, double iFrames) {
        this(life);
        this.iFrames = iFrames;
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }
    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        return dead;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (iFrames > 0){
            iFrames -= gameData.getDelta();
            isHit = false;
            entity.currentColor = entity.iFrameColor;
            return;
        }
        if (isHit) {
            this.iFrames = iFrameConstant;
            life -= 1;
            return;
        }
        if (life <= 0) {
            dead = true;
        }
        entity.currentColor = entity.baseColor;
    }

    public void setIFrameConstant(double iFrameConstant) {
        this.iFrameConstant = iFrameConstant;
    }

    public double getIFrames() {
        return iFrames;
    }
}