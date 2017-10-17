package com.pirate.soul.gameobjects;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.pirate.soul.Сonstants;
import com.pirate.soul.Screen;
import com.pirate.soul.utils.MapUtils;

/**
 * Created by Alexander on 24.11.2016.
 */

public class Bullet {
    Sprite sprite;
    Vector2 velocity;
    Rectangle rectangle;
    enum Direction {LEFT, RIGHT};
    public enum State {ALIVE, DEAD};
    public State state;
    Direction direction;
    Rectangle temp = new Rectangle();
    private static final float BULLET_VELOCITY = 0.4f;
    private static final float RESIZE_FACTOR = 1.25f;

    public Bullet(float width, float height, TextureRegion bulletTexture) {
        sprite = new Sprite(bulletTexture);
        velocity = new Vector2();
        rectangle = new Rectangle();
        sprite.setSize((sprite.getWidth() * (width / (width * RESIZE_FACTOR)) * Сonstants.unitScale), (sprite.getHeight() * (width / (width * RESIZE_FACTOR)) * Сonstants.unitScale));
    }

    public void reset(float x, float y, boolean isLeft) {
        state = State.ALIVE;
        sprite.setPosition(x, y);
        if (isLeft) {
            direction = Direction.LEFT;
            velocity.set(-BULLET_VELOCITY, 0);
        } else {
            direction = Direction.RIGHT;
            velocity.set(BULLET_VELOCITY, 0);
        }
    }

    public void checkWallHit() {
        Array<Rectangle> tiles = MapUtils.getHorizNeighbourTiles(velocity, sprite, "Wall");
        for (Rectangle tile : tiles) {
            if (rectangle.overlaps(tile)) {
                state = State.DEAD;
                break;
            }
        }
    }

    public void update() {
        OrthographicCamera camera = Screen.camera;
        float camX = camera.position.x;
        float camY = camera.position.y;
        rectangle.set(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
        temp.set((camX - camera.viewportWidth / 2), (camY - camera.viewportHeight / 2), camera.viewportWidth, camera.viewportHeight);
        if (!temp.overlaps(rectangle)) {
            state = State.DEAD;
            return;
        }
        checkWallHit();
        sprite.setX(sprite.getX() + velocity.x);
    }

    public void render(SpriteBatch batch) {
        if (direction == Direction.LEFT) {
            sprite.setFlip(true, false);
        } else {
            sprite.setFlip(false, false);
        }
        sprite.draw(batch);
    }
}
