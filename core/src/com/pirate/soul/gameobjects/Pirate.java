package com.pirate.soul.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.pirate.soul.Сonstants;
import com.pirate.soul.Datas;
import com.pirate.soul.managers.MainManager;
import com.pirate.soul.utils.MapUtils;

/**
 * Created by Alexander on 15.11.2016.
 */

public class Pirate {
    public static Pirate pirate;
    public Rectangle attackRangeRectangle;
    boolean isLeftPressed;
    boolean isRightPressed;
    boolean isLeftPaddleTouched;
    boolean isRightPaddleTouched;
    boolean isJumpPressed;
    boolean isJumpPaddleTouched;
    //Running
    float stateTime;
    public Sprite runSprite;
    Animation runAnimation;
    TextureRegion runSheet;
    TextureRegion currentRunPoseFrame;
    boolean runAnimationStateTime = false;
    private static int RUN_ANIMATION_FRAME_SIZE = 8;
    private static float RUN_ANIMATION_TIME_PERIOD = 0.12f;
    float runX, runY;
    //Standing
    public Sprite standSprite;
    float standStateTime;
    Animation standAnimation;
    TextureRegion standSheet;
    TextureRegion currentStandPoseFrame;
    boolean standAnimationStateTime = false;
    private static int STAND_ANIMATION_FRAME_SIZE = 4;
    private static float STAND_ANIMATION_TIME_PERIOD = 0.3f;
    //Walking
    public Sprite walkSprite;
    Animation walkAnimation;
    TextureRegion walkSheet;
    TextureRegion currentWalkPoseFrame;
    boolean walkAnimationStateTime = false;
    private static int WALK_ANIMATION_FRAME_SIZE = 10;
    private static float WALK_ANIMATION_TIME_PERIOD = 0.8f;
    //Jump
    public Sprite jumpSprite;
    float jumpStateTime;
    Animation jumpAnimation;
    TextureRegion jumpSheet;
    TextureRegion currentJumpPoseFrame;
    boolean jumpAnimationStateTime = false;
    private static int JUMP_ANIMATION_FRAME_SIZE = 3;
    private static float JUMP_ANIMATION_TIME_PERIOD = 0.2f;
    //jump down
    public Sprite jumpDownSprite;
    float jumpDownStateTime;
    Animation jumpDownAnimation;
    TextureRegion jumpDownSheet;
    TextureRegion currentJumpDownPoseFrame;
    boolean jumpDownAnimationStateTime = false;
    private static int JUMP_DOWN_ANIMATION_FRAME_SIZE = 1;
    private static float JUMP_DOWN_ANIMATION_TIME_PERIOD = 0.2f;
    float oldY = 0;
    float newY = 0;
    //Running jump
    public Sprite runningJumpSprite;
    float runningJumpStateTime;
    Animation runningJumpAnimation;
    TextureRegion runningJumpSheet;
    TextureRegion currentRunningJumpPoseFrame;
    boolean runningJumpAnimationStateTime = false;
    private static int RUNNING_JUMP_ANIMATION_FRAME_SIZE = 2;
    private static float RUNNING_JUMP_ANIMATION_TIME_PERIOD = 0.3f;
    //Running jump down
    public Sprite runningJumpDownSprite;
    float runningJumpDownStateTime;
    Animation runningJumpDownAnimation;
    TextureRegion runningJumpDownSheet;
    TextureRegion currentRunningJumpDownPoseFrame;
    boolean runningJumpDownAnimationStateTime = false;
    private static int RUNNING_JUMP_DOWN_ANIMATION_FRAME_SIZE = 1;
    private static float RUNNING_JUMP_DOWN_ANIMATION_TIME_PERIOD = 0.2f;
    //Shoot
    public Sprite shootSprite;
    float shootStateTime;
    Animation shootAnimation;
    TextureRegion shootSheet;
    TextureRegion currentShootPoseFrame;
    public boolean shootAnimationStateTime = false;
    private static int SHOOT_ANIMATION_FRAME_SIZE = 4;
    private static float SHOOT_ANIMATION_TIME_PERIOD = 0.1f;
    float shootFramesLenght = 0;
    float shootFramesDuration = 0;
    long startShoot;
    long endShoot;
    //Running shoot
    public Sprite runningShootSprite;
    float runningShootStateTime;
    Animation runningShootAnimation;
    TextureRegion runningShootSheet;
    TextureRegion currentRunningShootPoseFrame;
    public boolean runningShootAnimationStateTime = false;
    private static int RUNNING_SHOOT_ANIMATION_FRAME_SIZE = 4;
    private static float RUNNING_SHOOT_ANIMATION_TIME_PERIOD = 0.1f;
    //Jump shoot
    public Sprite jumpShootSprite;
    float jumpShootStateTime;
    Animation jumpShootAnimation;
    TextureRegion jumpShootSheet;
    TextureRegion currentJumpShootPoseFrame;
    public boolean jumpShootAnimationStateTime = false;
    private static int JUMP_SHOOT_ANIMATION_FRAME_SIZE = 4;
    private static float JUMP_SHOOT_ANIMATION_TIME_PERIOD = 0.1f;
    //Hit (attack)
    public Sprite hitSprite;
    float hitStateTime;
    Animation hitAnimation;
    TextureRegion hitSheet;
    TextureRegion currentHitPoseFrame;
    public boolean hitAnimationStateTime = false;
    private static int HIT_ANIMATION_FRAME_SIZE = 5;
    private static float HIT_ANIMATION_TIME_PERIOD = 0.06f;
    long startAttack = 0;
    long endAttack = 0;
    //Running hit
    public Sprite runningHitSprite;
    float runningHitStateTime;
    Animation runningHitAnimation;
    TextureRegion runningHitSheet;
    TextureRegion currentRunningHitPoseFrame;
    public boolean runningHitAnimationStateTime = false;
    private static int RUNNING_HIT_ANIMATION_FRAME_SIZE = 5;
    private static float RUNNING_HIT_ANIMATION_TIME_PERIOD = 0.06f;
    //Jump hit
    public Sprite jumpHitSprite;
    float jumpHitStateTime;
    Animation jumpHitAnimation;
    TextureRegion jumpHitSheet;
    TextureRegion currentJumpHitPoseFrame;
    public boolean jumpHitAnimationStateTime = false;
    private static int JUMP_HIT_ANIMATION_FRAME_SIZE = 5;
    private static float JUMP_HIT_ANIMATION_TIME_PERIOD = 0.06f;
    //climb
    public Sprite climbSprite;
    float climbStateTime;
    Animation climbAnimation;
    TextureRegion climbSheet;
    TextureRegion climbPoseFrame;
    public boolean climbAnimationStateTime = false;
    public boolean climbStopAnimationStateTime = false;
    public boolean backClimbAnimationStateTime = false;
    private static int CLIMB_ANIMATION_FRAME_SIZE = 4;
    private static float CLIMB_ANIMATION_TIME_PERIOD = 0.2f;
    //Texture atlas
    static TextureAtlas texturePack; //packed textures
    static AssetManager assetManager;
    public static final float PIRATE_RESIZE_FACTOR = 1.7f;
    //physics
    Vector2 velocity;
    private static final float maxVelocity = 0.15f;
    private static final float damping = 0.03f;
    private static final float gravitySpeedFaster = -0.02f;
    private static final float gravitySpeedSlower = -0.01f;
    private static final Vector2 gravity = new Vector2(0, gravitySpeedFaster);
    //platforms
    public Array<Rectangle> platformRects;
    public Array<Rectangle> nearbyPlatformRects;
    //stairs
    public Array<Rectangle> stairsRects;
    //traps
    public Array<Rectangle> trapsRects;
    //ropes
    public Array<Rectangle> ropesRects;
    float oldPirateX;
    float oldPirateY;
    boolean rightPoseRope = false;
    boolean leftPoseRope = false;
    boolean onGround = false;
    //damage
    boolean damageTimeOut = false;
    boolean damageTrigger = false;
    long startDamageTime = 0;
    long endDamaageTime = 0;
    //Pirate direction and move
    enum Direction {
        LEFT, RIGHT
    };
    Direction direction = Direction.RIGHT;

    public Rectangle pirateRectangle;
    //jump
    private static final float jumpVelocity = 0.4f;
    boolean isGrounded = false;
    //pirate's status
    public enum Lifestate {
        ALIVE, DEAD
    };
    public enum PirateAttackStatus {
        YES, NO
    };
    public PirateAttackStatus attackStatus = PirateAttackStatus.NO;
    public Lifestate lifestate = Lifestate.ALIVE;
    static float respawnDelay = 1;
    public float respawnCounter = 0;

    public void initialize(float width, float height) {
        velocity = new Vector2(0, 0);
        pirate = new Pirate();
        assetManager = new AssetManager();
        loadAssets();
        texturePack = assetManager.get(Сonstants.texturePack);
        pirateRectangle = new Rectangle();
        attackRangeRectangle = new Rectangle();
        platformRects = new Array<Rectangle>();
        //run
        runSheet = texturePack.findRegion(Сonstants.pirateRun);
        runSprite = new Sprite();
        runAnimation = animationCreater(width, runSheet, RUN_ANIMATION_FRAME_SIZE, RUN_ANIMATION_TIME_PERIOD, runSprite);
        currentRunPoseFrame = (TextureRegion) runAnimation.getKeyFrame(stateTime, true);
        //stand 
        standSheet = texturePack.findRegion(Сonstants.pirateStand);
        standSprite = new Sprite();
        standAnimation = animationCreater(width, standSheet, STAND_ANIMATION_FRAME_SIZE, STAND_ANIMATION_TIME_PERIOD, standSprite);
        currentStandPoseFrame = (TextureRegion) standAnimation.getKeyFrame(standStateTime, true);
        //walk
        walkSprite = new Sprite();
        walkSheet = texturePack.findRegion(Сonstants.pirateWalk);
        walkAnimation = animationCreater(width, walkSheet, WALK_ANIMATION_FRAME_SIZE, WALK_ANIMATION_TIME_PERIOD, walkSprite);
        walkAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);
        currentWalkPoseFrame = (TextureRegion) walkAnimation.getKeyFrame(0, true);
        //jump
        jumpSprite = new Sprite();
        jumpSheet = texturePack.findRegion(Сonstants.pirateJump);
        jumpAnimation = animationCreater(width, jumpSheet, JUMP_ANIMATION_FRAME_SIZE, JUMP_ANIMATION_TIME_PERIOD, jumpSprite);
        currentJumpPoseFrame = (TextureRegion) jumpAnimation.getKeyFrame(jumpStateTime, true);
        //running jump
        runningJumpSprite = new Sprite();
        runningJumpSheet = texturePack.findRegion(Сonstants.pirateRunningJump);
        runningJumpAnimation = animationCreater(width, runningJumpSheet, RUNNING_JUMP_ANIMATION_FRAME_SIZE, RUNNING_JUMP_ANIMATION_TIME_PERIOD, runningJumpSprite);
        currentRunningJumpPoseFrame = (TextureRegion) runningJumpAnimation.getKeyFrame(runningJumpStateTime, true);
        //running jump down
        runningJumpDownSprite = new Sprite();
        runningJumpDownSheet = texturePack.findRegion(Сonstants.pirateRunningJumpDown);
        runningJumpDownAnimation = animationCreater(width, runningJumpDownSheet, RUNNING_JUMP_DOWN_ANIMATION_FRAME_SIZE, RUNNING_JUMP_DOWN_ANIMATION_TIME_PERIOD, runningJumpDownSprite);
        currentRunningJumpDownPoseFrame = (TextureRegion) runningJumpDownAnimation.getKeyFrame(runningJumpDownStateTime, true);
        //jump down
        jumpDownSprite = new Sprite();
        jumpDownSheet = texturePack.findRegion(Сonstants.pirateJumpDown);
        jumpDownAnimation = animationCreater(width, jumpDownSheet, JUMP_DOWN_ANIMATION_FRAME_SIZE, JUMP_DOWN_ANIMATION_TIME_PERIOD, jumpDownSprite);
        currentJumpDownPoseFrame = (TextureRegion) jumpDownAnimation.getKeyFrame(jumpDownStateTime, true);
        //shoot
        startShoot = endShoot = 0;
        shootSprite = new Sprite();
        shootSheet = texturePack.findRegion(Сonstants.pirateShoot);
        shootAnimation = animationCreater(width, shootSheet, SHOOT_ANIMATION_FRAME_SIZE, SHOOT_ANIMATION_TIME_PERIOD, shootSprite);
        currentShootPoseFrame = (TextureRegion) shootAnimation.getKeyFrame(shootStateTime, true);
        shootFramesLenght = shootAnimation.getKeyFrames().length;
        shootFramesDuration = shootAnimation.getAnimationDuration();
        //running shoot
        runningShootSprite = new Sprite();
        runningShootSheet = texturePack.findRegion(Сonstants.pirateRunningShoot);
        runningShootAnimation = animationCreater(width, runningShootSheet, RUNNING_SHOOT_ANIMATION_FRAME_SIZE, RUNNING_SHOOT_ANIMATION_TIME_PERIOD, runningShootSprite);
        currentRunningShootPoseFrame = (TextureRegion) runningShootAnimation.getKeyFrame(runningShootStateTime, true);
        //jump shoot
        jumpShootSprite = new Sprite();
        jumpShootSheet = texturePack.findRegion(Сonstants.pirateJumpShoot);
        jumpShootAnimation = animationCreater(width, jumpShootSheet, JUMP_SHOOT_ANIMATION_FRAME_SIZE, JUMP_SHOOT_ANIMATION_TIME_PERIOD, jumpShootSprite);
        currentJumpShootPoseFrame = (TextureRegion) jumpShootAnimation.getKeyFrame(jumpShootStateTime, true);
        //hit
        hitSprite = new Sprite();
        hitSheet = texturePack.findRegion(Сonstants.pirateHit);
        hitAnimation = animationCreater(width, hitSheet, HIT_ANIMATION_FRAME_SIZE, HIT_ANIMATION_TIME_PERIOD, hitSprite);
        hitAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        currentHitPoseFrame = (TextureRegion) hitAnimation.getKeyFrame(hitStateTime, true);
        //Running hit
        runningHitSprite = new Sprite();
        runningHitSheet = texturePack.findRegion(Сonstants.pirateRunningHit);
        runningHitAnimation = animationCreater(width, runningHitSheet, RUNNING_HIT_ANIMATION_FRAME_SIZE, RUNNING_HIT_ANIMATION_TIME_PERIOD, runningHitSprite);
        runningHitAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        currentRunningHitPoseFrame = (TextureRegion) runningHitAnimation.getKeyFrame(runningHitStateTime, true);
        //Jump hit
        jumpHitSprite = new Sprite();
        jumpHitSheet = texturePack.findRegion(Сonstants.pirateJumpHit);
        jumpHitAnimation = animationCreater(width, jumpHitSheet, JUMP_HIT_ANIMATION_FRAME_SIZE, JUMP_HIT_ANIMATION_TIME_PERIOD, jumpHitSprite);
        jumpHitAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        currentJumpHitPoseFrame = (TextureRegion) jumpHitAnimation.getKeyFrame(jumpHitStateTime, true);
        //Climb
        climbSprite = new Sprite();
        climbSheet = texturePack.findRegion(Сonstants.pirateClimb);
        climbAnimation = animationCreater(width, climbSheet, CLIMB_ANIMATION_FRAME_SIZE, CLIMB_ANIMATION_TIME_PERIOD, climbSprite);
        climbAnimation.setPlayMode(Animation.PlayMode.NORMAL);
        climbPoseFrame = (TextureRegion) climbAnimation.getKeyFrame(climbStateTime, true);
        //set the pirate position
        setPosition(Сonstants.spawnPoint.x, Сonstants.spawnPoint.y);
    }

    public static void loadAssets() {
        assetManager.load(Сonstants.texturePack, TextureAtlas.class);
        assetManager.finishLoading();
    }

    public Animation animationCreater(float width, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / (width * PIRATE_RESIZE_FACTOR)), spriteSheet.getRegionHeight() * (width / (width * PIRATE_RESIZE_FACTOR)));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }

    public void setDirection(Direction direction, Sprite sprite) {
        if (direction == Direction.LEFT) {
            sprite.setFlip(true, false);
        } else {
            sprite.setFlip(false, false);
        }
    }

    public void render(SpriteBatch batch) {
        if (climbAnimationStateTime) {
            climbStateTime += Gdx.graphics.getDeltaTime();
            climbPoseFrame = (TextureRegion) climbAnimation.getKeyFrame(climbStateTime, true);
            climbSprite.setRegion(climbPoseFrame);
            setDirection(direction, climbSprite);
            climbSprite.draw(batch);
        } else if (backClimbAnimationStateTime) {
            climbStateTime += Gdx.graphics.getDeltaTime();
            climbAnimation.setPlayMode(Animation.PlayMode.REVERSED);
            climbPoseFrame = (TextureRegion) climbAnimation.getKeyFrame(climbStateTime, true);
            climbSprite.setRegion(climbPoseFrame);
            setDirection(direction, climbSprite);
            climbSprite.draw(batch);
        } else if (climbStopAnimationStateTime) {
            climbSprite.setRegion(climbPoseFrame);
            setDirection(direction, climbSprite);
            climbSprite.draw(batch);
        } else if (shootAnimationStateTime && standAnimationStateTime && isGrounded) {
            shootStateTime += Gdx.graphics.getDeltaTime();
            currentShootPoseFrame = (TextureRegion) shootAnimation.getKeyFrame(shootStateTime, true);
            shootSprite.setRegion(currentShootPoseFrame);
            setDirection(direction, shootSprite);
            shootSprite.draw(batch);
        } else if (shootAnimationStateTime && runAnimationStateTime && isGrounded) {
            shootStateTime += Gdx.graphics.getDeltaTime();
            currentRunningShootPoseFrame = (TextureRegion) runningShootAnimation.getKeyFrame(shootStateTime, true);
            runningShootSprite.setRegion(currentRunningShootPoseFrame);
            setDirection(direction, runningShootSprite);
            runningShootSprite.draw(batch);
        } else if (shootAnimationStateTime && !isGrounded) {
            shootStateTime += Gdx.graphics.getDeltaTime();
            currentJumpShootPoseFrame = (TextureRegion) jumpShootAnimation.getKeyFrame(shootStateTime, true);
            jumpShootSprite.setRegion(currentJumpShootPoseFrame);
            setDirection(direction, jumpShootSprite);
            jumpShootSprite.draw(batch);
        } else if (hitAnimationStateTime && !runAnimationStateTime && isGrounded) {
            hitStateTime += Gdx.graphics.getDeltaTime();
            currentHitPoseFrame = (TextureRegion) hitAnimation.getKeyFrame(hitStateTime, false);
            hitSprite.setRegion(currentHitPoseFrame);
            setDirection(direction, hitSprite);
            hitSprite.draw(batch);
        } else if (runningHitAnimationStateTime && isGrounded) {
            runningHitStateTime += Gdx.graphics.getDeltaTime();
            currentRunningHitPoseFrame = (TextureRegion) runningHitAnimation.getKeyFrame(runningHitStateTime, false);
            runningHitSprite.setRegion(currentRunningHitPoseFrame);
            setDirection(direction, runningHitSprite);
            runningHitSprite.draw(batch);
        } else if (jumpHitAnimationStateTime && !isGrounded) {
            jumpHitStateTime += Gdx.graphics.getDeltaTime();
            currentJumpHitPoseFrame = (TextureRegion) jumpHitAnimation.getKeyFrame(jumpHitStateTime, false);
            jumpHitSprite.setRegion(currentJumpHitPoseFrame);
            setDirection(direction, jumpHitSprite);
            jumpHitSprite.draw(batch);
        } else if (runAnimationStateTime && isGrounded && !runningHitAnimationStateTime) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentRunPoseFrame = (TextureRegion) runAnimation.getKeyFrame(stateTime, true);
            runSprite.setRegion(currentRunPoseFrame);
            setDirection(direction, runSprite);
            runSprite.draw(batch);
            runningHitAnimationStateTime = false;
            hitAnimationStateTime = false;
        } else if (standAnimationStateTime && isGrounded) {
            standStateTime += Gdx.graphics.getDeltaTime();
            currentStandPoseFrame = (TextureRegion) standAnimation.getKeyFrame(standStateTime, true);
            standSprite.setRegion(currentStandPoseFrame);
            setDirection(direction, standSprite);
            standSprite.draw(batch);
        } else if (jumpDownAnimationStateTime && !runningJumpAnimationStateTime && !isGrounded && !jumpHitAnimationStateTime) {
            jumpDownStateTime += Gdx.graphics.getDeltaTime();
            currentJumpDownPoseFrame = (TextureRegion) jumpDownAnimation.getKeyFrame(jumpDownStateTime, true);
            jumpDownSprite.setRegion(currentJumpDownPoseFrame);
            setDirection(direction, jumpDownSprite);
            jumpDownSprite.draw(batch);
        } else if (!jumpDownAnimationStateTime && !isGrounded && !runAnimationStateTime && !jumpHitAnimationStateTime) {
            jumpStateTime += Gdx.graphics.getDeltaTime();
            currentJumpPoseFrame = (TextureRegion) jumpAnimation.getKeyFrame(jumpStateTime, true);
            jumpSprite.setRegion(currentJumpPoseFrame);
            setDirection(direction, jumpSprite);
            jumpSprite.draw(batch);
        } else if (runAnimationStateTime && !isGrounded && !runningJumpDownAnimationStateTime && !jumpHitAnimationStateTime) {
            runningJumpStateTime += Gdx.graphics.getDeltaTime();
            currentRunningJumpPoseFrame = (TextureRegion) runningJumpAnimation.getKeyFrame(runningJumpStateTime, true);
            runningJumpSprite.setRegion(currentRunningJumpPoseFrame);
            setDirection(direction, runningJumpSprite);
            runningJumpSprite.draw(batch);
        } else if (runningJumpDownAnimationStateTime && !jumpHitAnimationStateTime) {
            runningJumpDownStateTime += Gdx.graphics.getDeltaTime();
            currentRunningJumpDownPoseFrame = (TextureRegion) runningJumpDownAnimation.getKeyFrame(runningJumpDownStateTime, true);
            runningJumpDownSprite.setRegion(currentRunningJumpDownPoseFrame);
            setDirection(direction, runningJumpDownSprite);
            runningJumpDownSprite.draw(batch);
        }
    }

    public void setPosition(float x, float y) {
        runSprite.setPosition(x, y);
    }

    public void move(float x, float y) {
        setPosition(runSprite.getX() + x, runSprite.getY() + y);
    }

    public void setLeftPressed(boolean isPressed) {
        if (isRightPressed && isPressed) {
            isRightPressed = false;
        }
        isLeftPressed = isPressed;
    }

    public void setRightPressed(boolean isPressed) {
        if (isLeftPressed && isPressed) {
            isLeftPressed = false;
        }
        isRightPressed = isPressed;
    }

    public void setLeftPaddleTouched(boolean isTouched) {
        if (isRightPaddleTouched && isTouched) {
            isRightPaddleTouched = false;
        }
        isLeftPaddleTouched = isTouched;
    }

    public void setRightPaddleTouched(boolean isTouched) {
        if (isLeftPaddleTouched && isTouched) {
            isLeftPaddleTouched = false;
        }
        isRightPaddleTouched = isTouched;
    }

    public void setJumpPressed(boolean isPressed) {
        if (isJumpPressed && isPressed) {
            isJumpPressed = false;
        }
        isJumpPressed = isPressed;
    }

    public void setJumpPaddleTouched(boolean isTouched) {
        if (isJumpPaddleTouched && isTouched) {

            isJumpPaddleTouched = false;
        }
        isJumpPaddleTouched = isTouched;
    }

    public void checkJumpDown() {
        oldY = newY;
        newY = pirateRectangle.getY();
        if (oldY - newY > 0.3f) {
            isGrounded = false;
            if (!runAnimationStateTime) {
                jumpDownAnimationStateTime = true;
            } else if (runAnimationStateTime) {
                runningJumpDownAnimationStateTime = true;
            }
        }
    }

    void correctPiratePosition(){
        if (direction == Direction.RIGHT) {
            hitSprite.setX(runX - 0.5f);
            runningHitSprite.setX(runX - 0.4f);
            jumpHitSprite.setX(runX - 0.4f);
        } else {
            hitSprite.setX(runX - 0.95f);
            runningHitSprite.setX(runX - 1.0f);
            jumpHitSprite.setX(runX - 1.0f);
        }
    }

    void setCurrentPositionForAllSprites(){
        runX = runSprite.getX();
        runY = runSprite.getY();
        standSprite.setX(runX);
        standSprite.setY(runY);
        jumpSprite.setX(runX);
        jumpSprite.setY(runY);
        jumpDownSprite.setX(runX);
        jumpDownSprite.setY(runY);
        runningJumpSprite.setX(runX);
        runningJumpSprite.setY(runY);
        runningJumpDownSprite.setX(runX);
        runningJumpDownSprite.setY(runY);
        shootSprite.setX(runX);
        shootSprite.setY(runY);
        runningShootSprite.setX(runX);
        runningShootSprite.setY(runY);
        jumpShootSprite.setX(runX);
        jumpShootSprite.setY(runY);
        climbSprite.setX(runX);
        climbSprite.setY(runY);
        attackRangeRectangle.setX(runX);
        attackRangeRectangle.setY(runY);
        jumpDownSprite.setX(runX + 0.2f);
        hitSprite.setY(runY);
        runningHitSprite.setY(runY);
        jumpHitSprite.setY(runY);
    }

    void disableAnimation(){
        runAnimationStateTime = false;
        standAnimationStateTime = false;
        jumpAnimationStateTime = false;
        jumpDownAnimationStateTime = false;
        runningJumpAnimationStateTime = false;
        runningJumpDownAnimationStateTime = false;
        climbAnimationStateTime = false;
        climbStopAnimationStateTime = false;
        backClimbAnimationStateTime = false;
        onGround = false;
        if (hitStateTime >= (HIT_ANIMATION_TIME_PERIOD * HIT_ANIMATION_FRAME_SIZE)) {
            hitAnimationStateTime = false;
        }
        if (runningHitStateTime >= (RUNNING_HIT_ANIMATION_TIME_PERIOD * RUNNING_HIT_ANIMATION_FRAME_SIZE)) {
            runningHitAnimationStateTime = false;
        }
        if (jumpHitStateTime >= (JUMP_HIT_ANIMATION_TIME_PERIOD * JUMP_HIT_ANIMATION_FRAME_SIZE)) {
            jumpHitAnimationStateTime = false;
        }
    }

    void attackAnimationTimeOut(){
        endAttack = TimeUtils.timeSinceMillis(startAttack);
        if (endAttack >= 300f) {
            attackStatus = PirateAttackStatus.NO;
            endAttack = startAttack = 0;
        }
        if (shootAnimationStateTime) {
            endShoot = TimeUtils.timeSinceMillis(startShoot);
            if (endShoot >= 300) {
                if (direction == Direction.RIGHT) {
                    MainManager.bullet.reset(runSprite.getX() + runSprite.getWidth() - 1, runSprite.getY() + (shootSprite.getHeight() / 2f), false);
                } else {
                    MainManager.bullet.reset(runSprite.getX() + runSprite.getWidth() - 1, runSprite.getY() + (shootSprite.getHeight() / 2f), true);
                }
                shootAnimationStateTime = false;
            }
        }
    }

    void damageAnimationTimeOut(){
        if (damageTrigger) {
            endDamaageTime = TimeUtils.timeSinceMillis(startDamageTime);
            if (endDamaageTime >= 1000) {
                damageTimeOut = false;
                damageTrigger = false;
                endDamaageTime = 0;
                startDamageTime = 0;
            } else {
                damageTimeOut = true;
            }
        }
    }

    void checkButtonPressed(){
        if (isLeftPressed) {
            runAnimationStateTime = true;
            direction = Direction.LEFT;
            velocity.x = -maxVelocity;
        }
        else if (isRightPressed) {
            runAnimationStateTime = true;
            direction = Direction.RIGHT;
            velocity.x = maxVelocity;
        }
        if (isLeftPaddleTouched) {
            runAnimationStateTime = true;
            direction = Direction.LEFT;
            velocity.x = -maxVelocity;
        }
        else if (isRightPaddleTouched) {
            runAnimationStateTime = true;
            direction = Direction.RIGHT;
            velocity.x = maxVelocity;
        }
    }

    void moveDamping(){
        if (velocity.x < 0) {
            velocity.x += damping;
        } else if (velocity.x > 0) {
            velocity.x -= damping;
        }
    }

    void checkPirateStands(){
        if (direction == Direction.RIGHT && velocity.x <= 0.02f) {
            velocity.x = 0.0f;
            standAnimationStateTime = true;
        } else if (direction == Direction.LEFT && velocity.x >= -0.02f) {
            velocity.x = 0.0f;
            standAnimationStateTime = true;
        }
    }

    void checkPirateRuns(){
        if (velocity.x != 0) {
            runAnimationStateTime = true;
        }
    }
    public void update() {
        setCurrentPositionForAllSprites();
        correctPiratePosition();
        disableAnimation();
        attackAnimationTimeOut();
        damageAnimationTimeOut();
        checkButtonPressed();
        moveDamping();
        checkPirateStands();
        checkPirateRuns();

        velocity.add(gravity);
        checkWallTouch();
        //checkHazardsTouch();
        checkCollectibleTouch();
        checkDoorTouch();
        checkEnemiesTouch();
        checkPlatformTouch();
        checkTrampolineTouch();
        checkRopesTouch();
        checkStairsTouch();
        checkTrapsTouch();
        checkJumpDown();
        move(velocity.x, velocity.y);
    }

    public void checkWallTouch() {
        float positionX = 0;
        if (direction == Direction.LEFT) {
            positionX = runSprite.getX() - 1;
        } else {
            positionX = runSprite.getX() + 1;
        }
        pirateRectangle.set(positionX, runSprite.getY(), standSprite.getWidth() / 1.5f, 0.5f);
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, runSprite, "Wall");
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                velocity.x = 0.0f;
                break;
            }
        }
        pirateRectangle.x = runSprite.getX() + 0.5f;
        tiles = MapUtils.getVertNeighbourTiles(velocity, runSprite, "Wall");
        pirateRectangle.y += velocity.y;
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                if (velocity.y > 0) {
                    runSprite.setY(tile.y - runSprite.getHeight());

                } else if (velocity.y < 0.08) {
                    onGround = true;
                    runSprite.setY(tile.y + tile.height);
                    isGrounded = true;
                }
                velocity.y = 0;
                break;
            }
        }
    }

    public void checkPlatformTouch() {
        pirateRectangle.set(jumpDownSprite.getX() + 0.4f, runSprite.getY(), jumpDownSprite.getWidth(), 1f);
        int startX, startY, endX, endY;
        if (velocity.y > 0) {
            startY = endY = (int) (runSprite.getY() + jumpDownSprite.getHeight());
        } else {
            startY = endY = (int) (runSprite.getY() + velocity.y);
        }
        startX = (int) (runSprite.getX());
        endX = (int) (runSprite.getX() + runSprite.getWidth());
        platformRects = MainManager.platformsRects;
        for (Rectangle tile : platformRects) {
            for (int y = startY; y <= endY; y++) {
                for (int x = startX; x <= endX; x++) {
                    if (tile.x == x && tile.y == y) {
                        if (pirateRectangle.overlaps(tile)) {
                            if (velocity.y > 0) {
                                runSprite.setY(tile.y - jumpDownSprite.getHeight());

                            } else if (velocity.y < 0) {
                                runSprite.setY(tile.y + tile.height);
                                isGrounded = true;
                            }
                            velocity.y = 0;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkStairsTouch() {
        pirateRectangle.set(jumpDownSprite.getX() + 0.4f, runSprite.getY(), jumpDownSprite.getWidth() - 0.4f, 1f);
        stairsRects = MainManager.stairsRectangles;
        for (Rectangle tile : stairsRects) {
            if (pirateRectangle.overlaps(tile)) {
                if (velocity.y < 0) {
                    velocity.y = 0;
                    isGrounded = true;
                } else if (velocity.x != 0) {
                    velocity.y = 0.12f;
                    isGrounded = true;
                }
            }
        }
    }

    public void checkTrapsTouch() {
        pirateRectangle.set(runSprite.getX(), runSprite.getY(), runSprite.getWidth(), runSprite.getHeight());
        trapsRects = MainManager.trapsRectangles;
        for (Rectangle tile : trapsRects) {
            if (pirateRectangle.overlaps(tile)) {
                if (!(Datas.lives <= 0 && Datas.health <= 1)) {
                    if (damageTimeOut == false) {
                        damagePirate(1);
                    }
                    break;
                } else {
                    Gdx.app.exit();
                }
            }
        }
        pirateRectangle.x = runSprite.getX();
        pirateRectangle.y += velocity.y;
        for (Rectangle tile : trapsRects) {
            if (pirateRectangle.overlaps(tile)) {
                if (!(Datas.lives <= 0 && Datas.health <= 1)) {
                    if (damageTimeOut == false) {
                        damagePirate(1);
                    }
                    break;
                } else {
                    Gdx.app.exit();
                }
            }
        }
    }

    public void checkRopesTouch() {
        pirateRectangle.set(climbSprite.getX(), climbSprite.getY(), runSprite.getWidth(), 0.1f);
        ropesRects = MainManager.ropesRectangles;
        for (Rectangle tile : ropesRects) {
            if (pirateRectangle.overlaps(tile)) {
                if (!(onGround && (isLeftPressed || isLeftPaddleTouched))) {
                    oldPirateY = 0;
                    if (isRightPaddleTouched || isRightPressed) {
                        climbAnimationStateTime = true;
                        velocity.x = 0;
                        velocity.y = 0.15f;
                        oldPirateY = runSprite.getY();
                        isGrounded = true;
                    } else if ((isLeftPaddleTouched || isLeftPressed) && runSprite.getY() > 0) {
                        backClimbAnimationStateTime = true;
                        velocity.x = 0;
                        velocity.y = -0.15f;
                        oldPirateY = runSprite.getY();
                        isGrounded = true;
                    } else if (isJumpPressed || isJumpPaddleTouched) {
                        velocity.x = -1.5f;
                    } else if (!jumpAnimationStateTime) {
                        climbStopAnimationStateTime = true;
                        velocity.y = 0;
                        isGrounded = true;
                    }
                    direction = Direction.RIGHT;
                }
            }
        }
    }

    public void checkTrampolineTouch() {
        pirateRectangle.set(runSprite.getX(), runSprite.getY(), standSprite.getWidth() / 1.5f, 0.5f);
        for (Rectangle tile : MainManager.trampRectangles) {
            if (pirateRectangle.overlaps(tile)) {
                velocity.y = 0.5f;
            }
        }
    }

    public void checkHazardsTouch() {
        pirateRectangle.set(runSprite.getX(), runSprite.getY(), runSprite.getWidth(), runSprite.getHeight());
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, runSprite, "Hazards");
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                if (!(Datas.lives <= 0 && Datas.health <= 0)) {
                    if (damageTimeOut == false) {
                        damagePirate(1);
                    }
                    break;
                } else {
                    Gdx.app.exit();
                }
            }
        }
        pirateRectangle.x = runSprite.getX();
        tiles = MapUtils.getVertNeighbourTiles(velocity, runSprite, "Hazards");
        pirateRectangle.y += velocity.y;
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                if (!(Datas.lives <= 0 && Datas.health <= 1)) {
                    if (damageTimeOut == false) {
                        damagePirate(1);
                    }
                    break;
                } else {
                    Gdx.app.exit();
                }
            }
        }
    }

    public void checkCollectibleTouch() {
        pirateRectangle.set(runSprite.getX(), runSprite.getY(), runSprite.getWidth(), runSprite.getHeight());
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, runSprite, "Coins");
        TiledMapTileLayer coinsLayer = (TiledMapTileLayer) MainManager.map.getLayers().get("Coins");
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                MapProperties coinsTileProperties = coinsLayer.getCell((int) tile.x, (int) tile.y).getTile().getProperties();
                int coinsPoints = Integer.parseInt(coinsTileProperties.get("Points").toString());
                Datas.score += coinsPoints;
                coinsLayer.setCell((int) tile.x, (int) tile.y, null);
                break;
            }
        }
        pirateRectangle.x = runSprite.getX();
        tiles = MapUtils.getVertNeighbourTiles(velocity, runSprite, "Coins");
        pirateRectangle.y += velocity.y;
        for (Rectangle tile : tiles) {
            if (pirateRectangle.overlaps(tile)) {
                MapProperties coinsTileProperties = coinsLayer.getCell((int) tile.x, (int) tile.y).getTile().getProperties();
                int coinsPoints = Integer.parseInt(coinsTileProperties.get("Points").toString());
                Datas.score += coinsPoints;
                coinsLayer.setCell((int) tile.x, (int) tile.y, null);
                break;
            }
        }
    }

    public void jump() {
        if (isGrounded) {
            runningHitAnimationStateTime = false;
            hitAnimationStateTime = false;
            jumpStateTime = 0;
            runningJumpStateTime = 0;
            jumpAnimationStateTime = true;
            velocity.y = jumpVelocity;
            isGrounded = false;
        }
    }

    public void shoot() {
        shootStateTime = 0;
        shootAnimationStateTime = true;
        startShoot = TimeUtils.millis();
    }

    public void damagePirate(int damage) {
        if (damageTimeOut == false) {
            damageTrigger = true;
            startDamageTime = TimeUtils.millis();
            if (Datas.health > damage) {
                Datas.health -= damage;
            } else {
                killPirate();
            }
        }
    }

    public void killPirate() {
        lifestate = Lifestate.DEAD;
        respawnCounter = respawnDelay;
    }

    public void respawnPirate() {
        setPosition(Сonstants.spawnPoint.x, Сonstants.spawnPoint.y);
        Datas.health = 10;
        Datas.lives--;
    }

    public void checkDoorTouch() {
        pirateRectangle.set(runSprite.getX(), runSprite.getY(), runSprite.getWidth(), runSprite.getHeight());
        if (MainManager.door != null && pirateRectangle.overlaps(MainManager.door)) {
            MainManager.loadLevel();
        }
    }

    public void checkEnemiesTouch() {
        for (Enemy enemy : MainManager.enemies) {
            pirateRectangle.set(runSprite.getX(), runSprite.getY() + 1f, runSprite.getWidth(), runSprite.getHeight() - 1f);
            if (enemy.state == Enemy.State.ALIVE && enemy.rectangle.overlaps(pirateRectangle) && enemy.damageTrigger == false) {
                if (Datas.lives > 0 || Datas.health > 1) {
                    if (damageTimeOut == false) {
                        damagePirate(1);
                    }
                    break;
                } else {
                    Gdx.app.exit();
                }
            }
        }
    }

    public void attack() {
        runningHitAnimationStateTime = false;
        hitAnimationStateTime = false;
        jumpHitAnimationStateTime = false;
        attackStatus = PirateAttackStatus.YES;
        startAttack = TimeUtils.millis();
        if (direction == Direction.LEFT) {
            attackRangeRectangle.set(runSprite.getX() - 1, runSprite.getY(), runSprite.getWidth(), runSprite.getHeight() + 1);
        } else {
            attackRangeRectangle.set(runSprite.getX(), runSprite.getY(), runSprite.getWidth() + 1, runSprite.getHeight() + 1);
        }
        if (runAnimationStateTime && isGrounded) {
            runningHitAnimationStateTime = true;
            runningHitStateTime = 0;
        } else if (!runAnimationStateTime && isGrounded) {
            hitAnimationStateTime = true;
            hitStateTime = 0;
        } else if (!isGrounded) {
            jumpHitAnimationStateTime = true;
            jumpHitStateTime = 0;
        }
    }

    public static void dispose() {
        assetManager.clear();
    }
}
