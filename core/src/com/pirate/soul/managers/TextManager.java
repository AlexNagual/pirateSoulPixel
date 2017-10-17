package com.pirate.soul.managers;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.pirate.soul.Datas;


/**
 * Created by Alexander on 23.11.2016.
 */

public class TextManager {
    static BitmapFont font;
    static float width, height;

    public static void initialize(float width, float height, BitmapFont font) {
        TextManager.font = font;
        TextManager.width = width;
        TextManager.height = height;
        font.setColor(Color.ORANGE);
        font.getData().setScale(width / 1000f);
    }

    public static void displayMessage(SpriteBatch batch) {
        GlyphLayout layout = new GlyphLayout();
        layout.setText(font, "justText" + Datas.score);
        float fontWidth = layout.width;
        font.draw(batch, "Score: " + Datas.score, width - fontWidth - width / 15f, height * 0.98f);
        font.draw(batch, "Lives: " + Datas.lives, width * 0.01f, height * 0.98f);
        layout.setText(font, "Score: " + Datas.score);
        font.draw(batch, "Health: " + Datas.health, width * 0.01f, height * 0.90f);
    }
}
