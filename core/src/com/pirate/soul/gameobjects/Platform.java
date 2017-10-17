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
 * Created by Alexander on 19.12.2016.
 */

public class Platform {
    float PLATFORM_RESIZE_FACTOR = 600f;
    Animation platformAnimation;
    public Sprite platformSprite;
    TextureRegion platformSheet;
    TextureRegion currentPlatformFrame;
    float stateTime;
    public static Rectangle platformRectangle;
    public static Array<Rectangle> rects;
    private static int PLATFORM_FRAME_SIZE = 1;
    private static float PLATFORM_TIME_PERIOD = 0.3f;

    public Platform (float width, float height, TextureRegion platformSheet, float x, float y){
        platformSprite = new Sprite();
        platformRectangle = new Rectangle();
        rects = new Array<Rectangle>();
        platformSprite.setPosition(x, y);
        this.platformSheet = platformSheet;
        platformSheet = MainManager.texturePack.findRegion(Сonstants.platformSheet);
        platformAnimation = animationCreater(width, height, platformSheet, PLATFORM_FRAME_SIZE, PLATFORM_TIME_PERIOD, platformSprite);
        currentPlatformFrame = (TextureRegion) platformAnimation.getKeyFrame(stateTime, true);
        platformSprite.setRegion(currentPlatformFrame);
    }

    public Animation animationCreater(float width, float height, TextureRegion spriteSheet, int animation_frame_size, float animation_time_period, Sprite sprite) {
        TextureRegion[][] tmp = spriteSheet.split(spriteSheet.getRegionWidth() / animation_frame_size, spriteSheet.getRegionHeight());
        TextureRegion[] spritesFrames = tmp[0];
        Animation spriteAnimation = new Animation(animation_time_period, spritesFrames);
        sprite.setSize((spriteSheet.getRegionWidth() / animation_frame_size) * (width / PLATFORM_RESIZE_FACTOR), spriteSheet.getRegionHeight() * (width / PLATFORM_RESIZE_FACTOR));
        sprite.setSize(sprite.getWidth() * Сonstants.unitScale, sprite.getHeight() * Сonstants.unitScale);
        spriteAnimation.setPlayMode(Animation.PlayMode.LOOP);
        return spriteAnimation;
    }

    public void update(){
        stateTime += Gdx.graphics.getDeltaTime();
        currentPlatformFrame = (TextureRegion) platformAnimation.getKeyFrame(stateTime, true);
    }

    public void render(SpriteBatch batch){
        platformSprite.draw(batch);
    }
}
