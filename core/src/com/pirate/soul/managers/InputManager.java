package com.pirate.soul.managers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Alexander on 15.11.2016.
 */

public class InputManager extends InputAdapter {
    OrthographicCamera camera;
    static Vector3 vector = new Vector3();
    public InputManager(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            MainManager.pirate.setLeftPressed(true);
        }
        else if (keycode == Input.Keys.RIGHT) {
            MainManager.pirate.setRightPressed(true);
        }
        else if (keycode == Input.Keys.SPACE) {
            MainManager.pirate.jump();
            MainManager.pirate.setJumpPressed(true);
        } else if (keycode == Input.Keys.ALT_LEFT) {
            MainManager.pirate.shoot();
        } else if (keycode == Input.Keys.CONTROL_LEFT) {
            MainManager.pirate.attack();
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) {
            MainManager.pirate.setLeftPressed(false);
        }
        else if (keycode == Input.Keys.RIGHT) {
            MainManager.pirate.setRightPressed(false);
        }
        else if (keycode == Input.Keys.SPACE) {
            MainManager.pirate.setJumpPressed(false);
        }
        return false;
    }

    boolean isLeftPaddleTouched(float touchX, float touchY) {
        if ((touchX >= MainManager.leftPaddleSprite.getX()) && touchX <= (MainManager.leftPaddleSprite.getX() + MainManager.leftPaddleSprite.getWidth()) && (touchY >= MainManager.leftPaddleSprite.getY()) && touchY <= (MainManager.leftPaddleSprite.getY() + MainManager.leftPaddleSprite.getHeight())) {
            return true;
        }
        return false;
    }

    boolean isRightPaddleTouched(float touchX, float touchY) {
        if ((touchX >= MainManager.rightPaddleSprite.getX()) && touchX <= (MainManager.rightPaddleSprite.getX() + MainManager.rightPaddleSprite.getWidth()) && (touchY >= MainManager.rightPaddleSprite.getY()) && touchY <= (MainManager.rightPaddleSprite.getY() + MainManager.rightPaddleSprite.getHeight())) {
            return true;
        }
        return false;
    }

    boolean isjumpButtonTouched(float touchX, float touchY) {
        if ((touchX >= MainManager.jumpButtonSprite.getX()) && touchX <= (MainManager.jumpButtonSprite.getX() + MainManager.jumpButtonSprite.getWidth()) && (touchY >= MainManager.jumpButtonSprite.getY()) && touchY <= (MainManager.jumpButtonSprite.getY() + MainManager.jumpButtonSprite.getHeight())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        vector.set(screenX, screenY, 0);
        camera.unproject(vector);
        float touchX = vector.x;
        float touchY = vector.y;
        if (isLeftPaddleTouched(touchX, touchY)) {
            MainManager.pirate.setLeftPaddleTouched(true);
        } else if (isRightPaddleTouched(touchX, touchY)) {
            MainManager.pirate.setRightPaddleTouched(true);
        } else if (isjumpButtonTouched(touchX, touchY)) {
            MainManager.pirate.setJumpPaddleTouched(true);
            MainManager.pirate.jump();
        } else if (isshootButtonTouched(touchX, touchY)) {
            MainManager.pirate.shoot();
        }
         else if (isAttackButtonTouched(touchX, touchY)){
            MainManager.pirate.attack();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        vector.set(screenX, screenY, 0);
        camera.unproject(vector);
        float touchX = vector.x;
        float touchY = vector.y;
        if (isLeftPaddleTouched(touchX, touchY)) {
            MainManager.pirate.setLeftPaddleTouched(false);
        } else if (isRightPaddleTouched(touchX, touchY)) {
            MainManager.pirate.setRightPaddleTouched(false);
        } else if (isjumpButtonTouched(touchX, touchY)){
            MainManager.pirate.setJumpPaddleTouched(false);
        }
        return false;
    }

    boolean isshootButtonTouched(float touchX, float touchY) {
        if ((touchX >= MainManager.shootButtonSprite.getX()) && touchX
                <= (MainManager.shootButtonSprite.getX() + MainManager.
                shootButtonSprite.getWidth()) && (touchY >=
                MainManager.shootButtonSprite.getY()) && touchY <=
                (MainManager.shootButtonSprite.getY() +
                        MainManager.shootButtonSprite.getHeight())) {
            return true;
        }
        return false;
    }

    boolean isAttackButtonTouched(float touchX, float touchY) {
        if ((touchX >= MainManager.attackButtonSprite.getX()) && touchX
                <= (MainManager.attackButtonSprite.getX() + MainManager.
                attackButtonSprite.getWidth()) && (touchY >=
                MainManager.attackButtonSprite.getY()) && touchY <=
                (MainManager.attackButtonSprite.getY() +
                        MainManager.attackButtonSprite.getHeight())) {
            return true;
        }
        return false;
    }
}
