package com.pirate.soul.gameobjects;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.pirate.soul.Сonstants;
import com.pirate.soul.managers.MainManager;

/**
 * Created by Alexander on 04.06.2017.
 */

public class Traps {
    float TRAPS_RESIZE_FACTOR = 600f;
    Animation trapsAnimation;
    public Sprite trapsSprite;
    TextureRegion trapsSheet;
    TextureRegion currentTrapsFrame;
    float stateTime;
    public static Rectangle trapsRectangle;
    public static Array<Rectangle> rects;
    private static int TRAPS_FRAME_SIZE = 1;
    private static float TRAPS_TIME_PERIOD = 0.3f;

    public Traps(float width, float height, TextureRegion trapsSheet, float x, float y){
        trapsSprite = new Sprite();
        trapsRectangle = new Rectangle();
        rects = new Array<Rectangle>();
        trapsSprite.setPosition(x, y);
        this.trapsSheet = trapsSheet;
        trapsSheet = MainManager.texturePack.findRegion(Сonstants.platformSheet);
        trapsAnimation = animationCreater(width, trapsSheet, TRAPS_FRAME_SIZE, TRAPS_TIME_PERIOD, trapsSprite);
        currentTrapsFrame = (TextureRegion) trapsAnimation.getKeyFrame(stateTime, true);
        trapsSprite.setRegion(currentTrapsFrame);
    }

    public Animation animationCreater(float width, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / TRAPS_RESIZE_FACTOR), spriteSheet.getRegionHeight() * (width / TRAPS_RESIZE_FACTOR));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }

}
