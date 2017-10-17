package com.pirate.soul;

import com.badlogic.gdx.Game;

/**
 * Created by Alexander on 15.11.2016.
 */

public class Main extends Game {
    @Override
    public void create() {
        setScreen(new Screen(this));
    }
}
