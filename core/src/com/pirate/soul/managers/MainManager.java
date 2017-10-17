package com.pirate.soul.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.pirate.soul.Сonstants;
import com.pirate.soul.Screen;
import com.pirate.soul.gameobjects.Bullet;
import com.pirate.soul.gameobjects.Enemy;
import com.pirate.soul.gameobjects.Platform;
import com.pirate.soul.gameobjects.Rope;
import com.pirate.soul.gameobjects.Stairs;
import com.pirate.soul.gameobjects.Traps;
import com.pirate.soul.gameobjects.Zombie;
import com.pirate.soul.gameobjects.Pirate;
import com.pirate.soul.utils.MapUtils;

/**
 * Created by Alexander on 15.11.2016.
 */

public class MainManager {
    public static Pirate pirate;
    //texture atlas
    public static TextureAtlas texturePack;
    //Paddles
    public static final float PADDLE_RESIZE = 700f;
    public static final float PADDLE_ALPHA = 0.25f;
    public static final float PADDLE_HORIZ_POSITION = 0.02f;
    public static final float PADDLE_VERT_POSITION = 0.01f;
    static TextureRegion leftPaddleTexture;
    static TextureRegion rightPaddleTexture;
    static Sprite leftPaddleSprite;
    static Sprite rightPaddleSprite;
    static TextureRegion jumpButtonTexture;
    static Sprite jumpButtonSprite;
    static TextureRegion shootButtonTexture;
    static Sprite shootButtonSprite;
    static TextureRegion attackButtonTexture;
    static Sprite attackButtonSprite;
    public static final float SHOOT_BUTTON_RESIZE = 700f;
    //asset
    static AssetManager assetManager;
    //map
    public static TiledMap map;
    public static OrthogonalTiledMapRenderer renderer;
    public static float Width;
    public static float Height;
    public static int mapWidth;
    public static int mapHeight;
    //fore ground
    public static TiledMap mapForeGround;
    public static OrthogonalTiledMapRenderer rendererForeGround;
    //text and font
    static BitmapFont font;
    //Next level
    public static Rectangle door;
    static short currentLevelIndex = 0;
    //Enemies
    public static Array<Enemy> enemies;
    //zombieWalk
    public static Zombie zombie;
    //bullet
    public static Array<Bullet> bullets;
    public static Bullet bullet;
    //platforms
    public static Array<Platform> platforms;
    public static Array<Rectangle> platformsRects;
    public static Rectangle emptyRectangle;
    static long platformWorkTimeStart = 0;
    static long platformWorkTimeEnd = 0;
    //stairs
    public static Array<Stairs> stairs;
    public static Array<Rectangle> stairsRectangles;
    //traps
    public static Array<Traps> traps;
    public static Array<Rectangle> trapsRectangles;
    //rope
    public static Array<Rope> ropes;
    public static Array<Rectangle> ropesRectangles;
    //trampoline
    public static Array<Rectangle> trampRectangles;

    public static void initialize(float width, float height) {
        MainManager.Width = width;
        MainManager.Height = height;
        //asset
        assetManager = new AssetManager();
        loadAssets();
        //get the map
        map = assetManager.get(Сonstants.levels[currentLevelIndex]);
        mapForeGround = assetManager.get(Сonstants.foreGround[currentLevelIndex]);
        setMapDimensions();
        //camera options
        renderer = new OrthogonalTiledMapRenderer(map, Сonstants.unitScale);
        rendererForeGround = new OrthogonalTiledMapRenderer(mapForeGround, Сonstants.unitScale);
        Screen.camera.setToOrtho(false, 10, 15);
        Screen.camera.update();
        // set the renderer's view to the game's main camera
        renderer.setView(Screen.camera);
        rendererForeGround.setView(Screen.camera);
        // get the packed texture from asset manager
        texturePack = assetManager.get(Сonstants.texturePack);
        //load pirate texture
        pirate = new Pirate();
        pirate.initialize(width, height);
        //text and font
        font = assetManager.get(Сonstants.fontPath);
        TextManager.initialize(width, height, font);
        MapUtils.initialize(mapForeGround);
        //collision detection
        MapUtils.initialize(map);
        //paddles
        initializeLeftPaddle(width, height);
        initializeRightPaddle(width, height);
        initializeJumpButton(width, height);
        bullets = new Array<Bullet>();
        bullet = new Bullet(width, height, texturePack.findRegion(Сonstants.bullet));
        bullets.add(bullet);
        initializeShootButton(width, height);
        initializeAttackButton(width, height);
        //doors
        door = MapUtils.spawnDoor(mapWidth, mapHeight);
        //platforms
        platforms = new Array<Platform>();
        MapUtils.spawnPlatforms(platforms, width, height, texturePack);
        platformsRects = new Array<Rectangle>();
        MapUtils.getPlatformRect(platformsRects);
        emptyRectangle = new Rectangle();
        emptyRectangle.set(0, 0, 0, 0);
        platformWorkTimeStart = TimeUtils.millis();
        //stairs
        stairs = new Array<Stairs>();
        stairsRectangles = new Array<Rectangle>();
        MapUtils.spawnStairs(stairs, width, height, texturePack);
        MapUtils.getStairsRect(stairsRectangles);
        //traps
        traps = new Array<Traps>();
        trapsRectangles = new Array<Rectangle>();
        MapUtils.spawnTraps(traps, width, height, texturePack);
        MapUtils.getTrapsRect(trapsRectangles);
        //rope
        ropes = new Array<Rope>();
        ropesRectangles = new Array<Rectangle>();
        MapUtils.spawnRopes(ropes, width, height, texturePack);
        MapUtils.getRopesRect(ropesRectangles);
        //enemies
        enemies = new Array<Enemy>();
        MapUtils.spawnEnemies(enemies, width, height, texturePack);
        //trampolines
        trampRectangles = MapUtils.spawnTrampoline();
    }

    public static void loadAssets() {
        assetManager.load(Сonstants.texturePack, TextureAtlas.class);
        assetManager.load(Сonstants.fontPath, BitmapFont.class);
        assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        assetManager.load(Сonstants.levels[currentLevelIndex], TiledMap.class);
        assetManager.load(Сonstants.foreGround[currentLevelIndex], TiledMap.class);
        assetManager.finishLoading();
    }

    static void pirateRespawn(SpriteBatch batch){
        if (pirate.lifestate == Pirate.Lifestate.ALIVE) {
            pirate.update();
            pirate.render(batch);
        } else {
            if (pirate.respawnCounter > 0) {
                pirate.respawnCounter -= Gdx.graphics.getDeltaTime();
            } else {
                pirate.lifestate = Pirate.Lifestate.ALIVE;
                pirate.respawnPirate();
            }
        }
    }

    static void enemiesRender(SpriteBatch batch){
        for (Enemy enemy : enemies) {
            if (enemy.state == Enemy.State.ALIVE) {
                enemy.update();
                enemy.render(batch);
            }
        }
    }

    static void bulletsRender(SpriteBatch batch){
        for (Bullet bullet : bullets) {
            if (bullet.state == Bullet.State.ALIVE) {
                bullet.update();
                bullet.render(batch);
            }
        }
    }

    static void platformRender(SpriteBatch batch){
        platformWorkTimeEnd = TimeUtils.timeSinceMillis(platformWorkTimeStart);
        if (platformWorkTimeEnd >= 3000) {
            MapUtils.getPlatformRect(platformsRects);
            Platform platform;
            for (int i = 0; i < platforms.size; i++) {
                if (i % 2 == 0) {
                    platform = platforms.get(i);
                    platform.update();
                    platform.render(batch);
                } else {
                    platformsRects.set(i, emptyRectangle);
                }
                if (platformWorkTimeEnd >= 6000) {
                    platformWorkTimeStart = TimeUtils.millis();
                }
            }
        } else {
            MapUtils.getPlatformRect(platformsRects);
            Platform platform;
            for (int i = 0; i < platforms.size; i++) {
                if (i % 2 != 0) {
                    platform = platforms.get(i);
                    platform.update();
                    platform.render(batch);
                } else {
                    platformsRects.set(i, emptyRectangle);
                }
            }
        }
    }

    static void calculateCameraPosition(){
        Screen.camera.position.x = (pirate.runSprite.getX())  ;
        Screen.camera.position.y = (pirate.runSprite.getY())  ;
        if (!((Screen.camera.position.x - (Screen.camera.viewportWidth / 2)) > 0)) {
            Screen.camera.position.x = Screen.camera.viewportWidth / 2;
        } else if (((Screen.camera.position.x + (Screen.camera.viewportWidth / 2)) >= mapWidth)) {
            Screen.camera.position.x = mapWidth - Screen.camera.viewportWidth / 2;
        }
        if (!((pirate.runSprite.getY() - (Screen.camera.viewportHeight / 2)) > 0)) {
            Screen.camera.position.y = Screen.camera.viewportHeight / 2;
        }
        if (pirate.runSprite.getX() >= (mapWidth - pirate.runSprite.getWidth())) {
            pirate.runSprite.setPosition((mapWidth - pirate.runSprite.getWidth()), pirate.runSprite.getY());
        } else if (pirate.runSprite.getX() < 0) {
            pirate.runSprite.setPosition(0, pirate.runSprite.getY());
        }
        renderer.setView(Screen.camera);
        rendererForeGround.setView(Screen.camera);
        Screen.camera.update();
    }

    public static void renderGame(SpriteBatch batch) {
        batch.setProjectionMatrix(Screen.camera.combined);
        pirateRespawn(batch);
        bulletsRender(batch);
        enemiesRender(batch);
        platformRender(batch);
        calculateCameraPosition();
    }

    public static void renderHud(SpriteBatch batch){
        batch.setProjectionMatrix(Screen.hudCamera.combined);
        leftPaddleSprite.draw(batch);
        rightPaddleSprite.draw(batch);
        jumpButtonSprite.draw(batch);
        shootButtonSprite.draw(batch);
        attackButtonSprite.draw(batch);
        TextManager.displayMessage(batch);
    }

    public static void initializeLeftPaddle(float width, float height) {
        leftPaddleTexture = texturePack.findRegion(Сonstants.leftPaddleImage);
        leftPaddleSprite = new Sprite(leftPaddleTexture);
        leftPaddleSprite.setSize(leftPaddleSprite.getWidth() * width / PADDLE_RESIZE, leftPaddleSprite.getHeight() * width / PADDLE_RESIZE);
        leftPaddleSprite.setPosition(width * PADDLE_HORIZ_POSITION, height * PADDLE_VERT_POSITION);
        leftPaddleSprite.setAlpha(PADDLE_ALPHA);
    }

    public static void initializeRightPaddle(float width, float height) {
        rightPaddleTexture = texturePack.findRegion(Сonstants.rightPaddleImage);
        rightPaddleSprite = new Sprite(rightPaddleTexture);
        rightPaddleSprite.setSize(rightPaddleSprite.getWidth() * width / PADDLE_RESIZE, rightPaddleSprite.getHeight() * width / PADDLE_RESIZE);
        rightPaddleSprite.setPosition(leftPaddleSprite.getX() + leftPaddleSprite.getWidth() + width * PADDLE_HORIZ_POSITION, height * PADDLE_VERT_POSITION);
        rightPaddleSprite.setAlpha(PADDLE_ALPHA);
    }

    public static void initializeJumpButton(float width, float height) {
        jumpButtonTexture = texturePack.findRegion(Сonstants.jumpImagePaddle);
        jumpButtonSprite = new Sprite(jumpButtonTexture);
        jumpButtonSprite.setSize(jumpButtonSprite.getWidth() * width / PADDLE_RESIZE, jumpButtonSprite.getHeight() * width / PADDLE_RESIZE);
        jumpButtonSprite.setPosition(width * 0.7f, height * 0.01f);
        jumpButtonSprite.setAlpha(0.25f);
    }

    public static void initializeShootButton(float width, float height) {
        shootButtonTexture = texturePack.findRegion(Сonstants.shootPaddleImage);
        shootButtonSprite = new Sprite(shootButtonTexture);
        shootButtonSprite.setSize(shootButtonSprite.getWidth() * width / SHOOT_BUTTON_RESIZE, shootButtonSprite.getHeight() * width / SHOOT_BUTTON_RESIZE);
        shootButtonSprite.setPosition(width * 0.85f, height * 0.01f);
        shootButtonSprite.setAlpha(0.25f);
    }

    public static void initializeAttackButton(float width, float height) {
        attackButtonTexture = texturePack.findRegion(Сonstants.attackPaddleImage);
        attackButtonSprite = new Sprite(attackButtonTexture);
        attackButtonSprite.setSize(attackButtonSprite.getWidth() * width / SHOOT_BUTTON_RESIZE, attackButtonSprite.getHeight() * width / SHOOT_BUTTON_RESIZE);
        attackButtonSprite.setPosition(width * 0.85f, height * 0.21f);
        attackButtonSprite.setAlpha(0.25f);
    }

    static void setMapDimensions() {
        MapProperties properties = map.getProperties();
        mapHeight = Integer.parseInt(properties.get("height").toString());
        mapWidth = Integer.parseInt(properties.get("width").toString());
    }

    public static void loadLevel() {
        currentLevelIndex++;
        assetManager.load(Сonstants.levels[currentLevelIndex], TiledMap.class);
        assetManager.finishLoading();
        map = assetManager.get(Сonstants.levels[currentLevelIndex]);
        mapForeGround = assetManager.get(Сonstants.foreGround[currentLevelIndex]);
        setMapDimensions();
        renderer.setMap(map);
        rendererForeGround.setMap(mapForeGround);
        MapUtils.initialize(map);
        MapUtils.initialize(mapForeGround);
        enemies.clear();
        MapUtils.spawnEnemies(enemies, Width, Height, texturePack);
        door = MapUtils.spawnDoor(mapWidth, mapHeight);
        pirate.setPosition(Сonstants.spawnPoint.x, Сonstants.spawnPoint.y);
        Screen.camera.update();
    }

    public static void dispose() {
        assetManager.clear();
    }
}
