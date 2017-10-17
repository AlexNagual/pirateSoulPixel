package com.pirate.soul.gameobjects;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.pirate.soul.managers.MainManager;
import com.pirate.soul.utils.MapUtils;

/**
 * Created by Alexander on 24.11.2016.
 */

public abstract class Enemy {
    Sprite sprite;
    Vector2 velocity;
    Rectangle rectangle;
    public abstract void render(SpriteBatch batch);
    private static final float HORIZ_SENSE_DISTANCE = 7;
    enum Direction{LEFT,RIGHT};
    Direction direction = Direction.LEFT;
    public enum State{ALIVE,DEAD};
    public State state = State.ALIVE;
    public float healths;
    Bullet bullet = MainManager.bullet;
    long startTime;
    long timeElapsed;
    long startDamage = 0;
    long endDamage = 0;
    boolean damageTimeOut = false;
    public boolean damageTrigger = false;
    boolean attackTimeOut = false;
    boolean attackTrigger = false;
    long startAttack = 0;
    long endAttack = 0;
    public Sprite zombieDamagedSprite;
    public Sprite zombieAttackSprite;
    float attackStateTime;

    public void update(){
        if (damageTrigger){
            endDamage = TimeUtils.timeSinceMillis(startDamage);
            if (endDamage >= 700){
                if (direction == Direction.LEFT){
                    velocity.x = -0.06f;
                }
                else
                    velocity.x = 0.06f;
                damageTimeOut = false;
                damageTrigger = false;
            }
            else {
                if (direction == Direction.LEFT)
                    velocity.x = -0.000001f;
                else
                    velocity.x = 0.000001f;
                damageTimeOut = true;
            }
        }
        if (attackTrigger){
            endAttack = TimeUtils.timeSinceMillis(startAttack);
            if (endAttack >= 100){
                if (direction == Direction.LEFT){
                    velocity.x = -0.06f;
                }
                else
                    velocity.x = 0.06f;
                attackTimeOut = false;
                attackTrigger = false;
            }
            else {
                if (direction == Direction.LEFT)
                    velocity.x = -0.000001f;
                else
                    velocity.x = 0.000001f;
                damageTimeOut = true;
            }
        }
    }

    public void checkWallHit(){
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, sprite, "Stopwall");
        for (Rectangle tile : tiles) {
            if (rectangle.overlaps(tile)) {
                velocity.x *=-1;
                break;
            }
        }
    }

    public void senseAndFollow(){
        float difference = MainManager.pirate.runSprite.getX()- (sprite.getX()+sprite.getWidth()/2);
        float yDifference = MainManager.pirate.runSprite.getY()+1f - sprite.getY();
        if(Math.abs(difference)<=HORIZ_SENSE_DISTANCE && yDifference < sprite.getHeight() && yDifference>0){
            int startX, startY, endX, endY;
            if (difference > 0) {
                endX = (int) MainManager.pirate.runSprite.getX();
                startX = (int) sprite.getX();
            }
            else {
                startX = (int) MainManager.pirate.runSprite.getX();
                endX = (int) sprite.getX();
            }
            startY = (int) (sprite.getY());
            endY = (int) (sprite.getY() + sprite.getHeight());
            Array<Rectangle> tiles = MapUtils.getTiles(startX, startY, endX,endY, "Wall");
            if (tiles.size == 0) {
                if((direction == Direction.LEFT) && difference>0){
                    velocity.x *=-1;
                }
                if((direction == Direction.RIGHT )&& difference<0){
                    direction = Direction.LEFT;
                    velocity.x *=-1;
                }
            }
        }
    }

    public void checkDamaged(TextureRegion normal, TextureRegion damaged){
        if(rectangle.overlaps(bullet.rectangle) && bullet.state==Bullet.State.ALIVE ){
            startTime = TimeUtils.millis();
            sprite.setRegion(damaged);
            bullet.state=Bullet.State.DEAD;
            if (damageTimeOut == false) {
                damaged(40);
            }
        } else if (rectangle.overlaps(MainManager.pirate.attackRangeRectangle) && MainManager.pirate.attackStatus == Pirate.PirateAttackStatus.YES){
            startTime = TimeUtils.millis();
            sprite.setRegion(damaged);
            if (damageTimeOut == false) {
                damaged(40);
            }
        }
        timeElapsed = TimeUtils.timeSinceMillis(startTime);
        if (timeElapsed >= 700){
            sprite.setRegion(normal);
        }
    }
    public void damaged(int damage){
        if (damageTimeOut == false){
            damageTrigger = true;
            startDamage = TimeUtils.millis();
            if (healths >= damage) {
                healths -= damage;
            } else {
                die();
            }
        }
    }

    public void die() {
        state = State.DEAD;
    }


}
