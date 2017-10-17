package com.pirate.soul;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.collision.Segment;

/**
 * Created by Alexander on 16.11.2016.
 */

public class Сonstants {
    //pack
    public static final String texturePack = "data/pirate_pack.pack";
    //pirate
    public static final String pirateRun = "pirateRunSheet";
    public static final String pirateStand = "pirateStandSheet";
    public static final String pirateWalk = "pirateWalk";
    public static final String pirateJump = "pirateJumpSheet";
    public static final String pirateJumpDown = "pirateJumpDown";
    public static final String pirateRunningJump = "runningJumpSheet";
    public static final String pirateRunningJumpDown = "runningJumpDown";
    public static final String pirateShoot = "pirateShootSheet";
    public static final String pirateRunningShoot = "pirateRunningShootSheet";
    public static final String pirateJumpShoot = "pirateJumpShootSheet";
    public static final String pirateHit = "pirateHitSheet";
    public static final String pirateRunningHit = "pirateRunningHitSheet";
    public static final String pirateJumpHit = "pirateJumpHitSheet";
    public static final String pirateClimb = "pirateClimbSheet";
    //zombie with sword
    public static final String zombieWalk = "ZombieWalkSheet";
    public static final String zombieAttack = "zombieAttackSheet";
    public static final String zombieDamaging = "zombieDamagingSmall";
    //zombie with shotgun
    public static final String zombieSgWalk = "ZombieSGWalkSheet";
    //bullet
    public static final String bullet = "bullet";
    //platform
    public static final String platformSheet = "Ground";
    //paddles
    public static final String leftPaddleImage = "left";
    public static final String rightPaddleImage = "right";
    public static final String jumpImagePaddle = "jump";
    public static final String shootPaddleImage = "shoot";
    public static final String attackPaddleImage = "attack";
    //levels
    public static final String[] levels = {"data/pixel_maps/level1/level1.tmx"};
    public static final String[] foreGround = {"data/pixel_maps/level1/level1_1.tmx"};
    //font
    public static final String fontPath = "data/fonts/dos.fnt";
    //other
    public static final Vector2 spawnPoint = new Vector2(20, 4);
    public static final float unitScale = 1 / 32f;


}
