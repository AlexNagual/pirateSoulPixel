package com.pirate.soul.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pirate.soul.Сonstants;
import com.pirate.soul.managers.MainManager;

/**
 * Created by Alexander on 11.01.2017.
 */

public class Rope {
    float ROPE_RESIZE_FACTOR = 600f;
    Animation ropeAnimation;
    public Sprite ropeSprite;
    TextureRegion ropeSheet;
    TextureRegion currentRopeFrame;
    float stateTime;
    public static Rectangle ropeRectangle;
    public static Array<Rectangle> rects;
    private static int ROPE_FRAME_SIZE = 1;
    private static float ROPE_TIME_PERIOD = 0.3f;

    public Rope (float width, float height, TextureRegion stairsSheet, float x, float y){
        ropeSprite = new Sprite();
        ropeRectangle = new Rectangle();
        rects = new Array<Rectangle>();
        ropeSprite.setPosition(x, y);
        this.ropeSheet = stairsSheet;
        stairsSheet = MainManager.texturePack.findRegion(Сonstants.platformSheet);
        ropeAnimation = animationCreater(width, stairsSheet, ROPE_FRAME_SIZE, ROPE_TIME_PERIOD, ropeSprite);
        currentRopeFrame = (TextureRegion) ropeAnimation.getKeyFrame(stateTime, true);
        ropeSprite.setRegion(currentRopeFrame);
    }

    public Animation animationCreater(float width, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / ROPE_RESIZE_FACTOR), spriteSheet.getRegionHeight() * (width / ROPE_RESIZE_FACTOR));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }

}
