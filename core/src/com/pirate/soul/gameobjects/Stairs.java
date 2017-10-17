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
 * Created by Alexander on 22.12.2016.
 */

public class Stairs {
    float STAIRS_RESIZE_FACTOR = 600f;
    Animation stairsAnimation;
    public Sprite stairsSprite;
    TextureRegion stairsSheet;
    TextureRegion currentStairsFrame;
    float stateTime;
    public static Rectangle stairsRectangle;
    public static Array<Rectangle> rects;
    private static int STAIRS_FRAME_SIZE = 1;
    private static float STAIRS_TIME_PERIOD = 0.3f;

    public Stairs (float width, float height, TextureRegion stairsSheet, float x, float y){
        stairsSprite = new Sprite();
        stairsRectangle = new Rectangle();
        rects = new Array<Rectangle>();
        stairsSprite.setPosition(x, y);
        this.stairsSheet = stairsSheet;
        stairsSheet = MainManager.texturePack.findRegion(Сonstants.platformSheet);
        stairsAnimation = animationCreater(width, height, stairsSheet, STAIRS_FRAME_SIZE, STAIRS_TIME_PERIOD, stairsSprite);
        currentStairsFrame = (TextureRegion) stairsAnimation.getKeyFrame(stateTime, true);
        stairsSprite.setRegion(currentStairsFrame);
    }

    public Animation animationCreater(float width, float height, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / STAIRS_RESIZE_FACTOR), spriteSheet.getRegionHeight() * (width / STAIRS_RESIZE_FACTOR));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }
}
