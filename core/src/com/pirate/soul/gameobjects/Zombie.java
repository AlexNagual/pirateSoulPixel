package com.pirate.soul.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.pirate.soul.Сonstants;
import com.pirate.soul.managers.MainManager;

/**
 * Created by Alexander on 24.11.2016.
 */

public class Zombie extends Enemy {
    private static final float ZOMBIE_RESIZE_FACTOR = 1.25f;
    private static final float ZOMBIE_VELOCITY = 0.06f;
    Animation zombieWalkAnimation;
    TextureRegion zombieWalkSheet;
    TextureRegion currentZombieWalkFrame;
    float stateTime;
    private static int ZOMBIE_WALK_ANIMATION_FRAME_SIZE = 8;
    private static float ZOMBIE_WALK_ANIMATION_TIME_PERIOD = 0.2f;
    Animation zombieDamagedAnimation;
    TextureRegion zombieDamagedSheet;
    TextureRegion currentZombieDamagedFrame;
    private static int ZOMBIE_DAMAGED_ANIMATION_FRAME_SIZE = 1;
    private static float ZOMBIE_DAMAGED_ANIMATION_TIME_PERIOD = 0.25f;
    Animation zombieAttackAnimation;
    TextureRegion zombieAttackSheet;
    TextureRegion currentZombieAttackFrame;
    private static int ZOMBIE_ATTACK_ANIMATION_FRAME_SIZE = 4;
    private static float ZOMBIE_ATTACK_ANIMATION_TIME_PERIOD = 0.1f;
    private static final float HORIZ_ATTACK_DISTANCE = 2f;
    boolean attackTimeOut = false;
    boolean attackTrigger = false;
    long startAttack = 0;
    long endAttack = 0;
    boolean rightPosition = false;
    float difference, yDifference = 0;

    @Override
    public void render(SpriteBatch batch) {
        if (!attackTrigger) {
            currentZombieAttackFrame = (TextureRegion) zombieAttackAnimation.getKeyFrame(stateTime, true);
            sprite.draw(batch);
        } else {
            if (direction == Direction.RIGHT) {
                direction = Direction.LEFT;
            }
            currentZombieAttackFrame = (TextureRegion) zombieAttackAnimation.getKeyFrame(stateTime, true);
            zombieAttackSprite.setRegion(currentZombieAttackFrame);
            if (rightPosition)
                zombieAttackSprite.setFlip(true, false);
            else
                zombieAttackSprite.setFlip(false, false);
            zombieAttackSprite.draw(batch);
        }
    }

    public Zombie(float width, float height, TextureRegion zombieSheet, float x, float y, float health) {
        sprite = new Sprite();
        sprite.setPosition(x, y);
        velocity = new Vector2(ZOMBIE_VELOCITY, 0);
        rectangle = new Rectangle();
        this.zombieWalkSheet = zombieSheet;
        this.healths = health;
        zombieWalkAnimation = animationCreater(width, zombieWalkSheet, ZOMBIE_WALK_ANIMATION_FRAME_SIZE, ZOMBIE_WALK_ANIMATION_TIME_PERIOD, sprite);
        //get damage
        zombieDamagedSprite = new Sprite();
        zombieDamagedSheet = MainManager.texturePack.findRegion(Сonstants.zombieDamaging);
        zombieDamagedAnimation = animationCreater(width, zombieDamagedSheet, ZOMBIE_DAMAGED_ANIMATION_FRAME_SIZE, ZOMBIE_DAMAGED_ANIMATION_TIME_PERIOD, zombieDamagedSprite);
        //attack
        zombieAttackSprite = new Sprite();
        zombieAttackSprite.setFlip(true, false);
        zombieAttackSheet = MainManager.texturePack.findRegion(Сonstants.zombieAttack);
        zombieAttackAnimation = animationCreater(width, zombieAttackSheet, ZOMBIE_ATTACK_ANIMATION_FRAME_SIZE, ZOMBIE_ATTACK_ANIMATION_TIME_PERIOD, zombieAttackSprite);
        // get initial frame
        currentZombieWalkFrame = (TextureRegion) zombieWalkAnimation.getKeyFrame(stateTime, true);
        currentZombieDamagedFrame = (TextureRegion) zombieDamagedAnimation.getKeyFrame(stateTime, true);
        currentZombieAttackFrame = (TextureRegion) zombieAttackAnimation.getKeyFrame(stateTime, true);

    }

    public Animation animationCreater(float width, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / (width * ZOMBIE_RESIZE_FACTOR)), spriteSheet.getRegionHeight() * (width / (width * ZOMBIE_RESIZE_FACTOR)));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }

    public void attack(){
        difference = MainManager.pirate.runSprite.getX()- (sprite.getX()+sprite.getWidth()/2);
        yDifference = MainManager.pirate.runSprite.getY()+1f - sprite.getY();
        if(Math.abs(difference)<=HORIZ_ATTACK_DISTANCE && yDifference < sprite.getHeight() && yDifference>0 && !damageTrigger){
            if (difference > 0) {
                if (!attackTimeOut){
                    attackTrigger = true;
                }
                attackStateTime = 0;
                startAttack = TimeUtils.millis();
                rightPosition = true;
            }
            else {
                if (!attackTimeOut) {
                    attackTrigger = true;
                }
                attackStateTime = 0;
                startAttack = TimeUtils.millis();
                rightPosition = false;
            }
        }
    }

    void checkAttackTrigger(){
        if (attackTrigger){
            endAttack = TimeUtils.timeSinceMillis(startAttack);
            if (endAttack >= 300){
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
            }
        }
    }

    void checkSenseAndFollowToPirateBoy(){
        if (MainManager.pirate.lifestate == Pirate.Lifestate.ALIVE) {
            senseAndFollow();
        }
        if (velocity.x < 0) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
    }

    void setCurrentZombiePosition(){
        rectangle.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        sprite.setX(sprite.getX() + velocity.x);
        zombieAttackSprite.setX(sprite.getX());
        zombieAttackSprite.setY(sprite.getY());
        zombieDamagedSprite.setX(sprite.getX());
        zombieDamagedSprite.setY(sprite.getY());
    }

    void checkZombieWalk(){
        stateTime += Gdx.graphics.getDeltaTime();
        if (!attackTrigger)
        {
            currentZombieWalkFrame = (TextureRegion) zombieWalkAnimation.getKeyFrame(stateTime, true);
        }
        if (direction == Direction.RIGHT) {
            sprite.setFlip(true, false);
        } else {
            sprite.setFlip(false, false);
        }
    }

    @Override
    public void update() {
        attack();
        checkDamaged(currentZombieWalkFrame, currentZombieDamagedFrame);
        super.update();
        checkWallHit();
        checkAttackTrigger();
        checkSenseAndFollowToPirateBoy();
        setCurrentZombiePosition();
        checkZombieWalk();
    }
}
