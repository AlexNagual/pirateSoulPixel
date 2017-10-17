package com.pirate.soul;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.pirate.soul.managers.MainManager;
import com.pirate.soul.managers.InputManager;

/**
 * Created by Alexander on 15.11.2016.
 */

public class Screen implements com.badlogic.gdx.Screen {
    Main game ;
    SpriteBatch batch;
    public static OrthographicCamera camera, hudCamera;

    public Screen(Main game){
        this.game=game;
        float height= Gdx.graphics.getHeight();
        float width = Gdx.graphics.getWidth();
        camera = new OrthographicCamera(width,height);
        camera.setToOrtho(true);
        hudCamera = new OrthographicCamera(width,height);
        hudCamera.setToOrtho(false);
        batch = new SpriteBatch();
        MainManager.initialize(width, height);
        Gdx.input.setInputProcessor(new InputManager(hudCamera));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        MainManager.renderer.render();
        batch.begin();
        MainManager.renderGame(batch);
        batch.end();
        batch.begin();
        MainManager.renderHud(batch);
        batch.end();
    }
    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 22f;
        camera.viewportHeight = 22f * height/width;
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        MainManager.dispose();
    }
}
