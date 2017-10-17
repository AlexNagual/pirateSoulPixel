package com.pirate.soul.utils;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.pirate.soul.Сonstants;
import com.pirate.soul.gameobjects.Enemy;
import com.pirate.soul.gameobjects.Platform;
import com.pirate.soul.gameobjects.Rope;
import com.pirate.soul.gameobjects.Stairs;
import com.pirate.soul.gameobjects.Traps;
import com.pirate.soul.gameobjects.Zombie;

import java.util.Iterator;

/**
 * Created by Alexander on 17.11.2016.
 */

public class MapUtils {
    public static TiledMap map;
    public static void initialize(TiledMap map) {
        MapUtils.map = map;
    }

    private static Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject() {
            return new Rectangle();
        }
    };
    private static Array<Rectangle> tiles = new Array<Rectangle>();

    public static Array<Rectangle> getTiles(int startX, int startY, int endX, int endY, String layerName) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(layerName);
        rectPool.freeAll(tiles);
        tiles.clear();
        for (int y = startY; y <= endY; y++) {
            for (int x = startX; x <= endX; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null) {
                    Rectangle rect = rectPool.obtain();
                    rect.set(x, y, 1, 1);
                    tiles.add(rect);
                }
            }
        }
        return tiles;
    }

    public static Array<Rectangle> getVertNeighbourTiles(Vector2 velocity, Sprite sprite, String layerName) {
        int startX, startY, endX, endY;
        if (velocity.y > 0) {
            startY = endY = (int) (sprite.getY() + sprite.getHeight());
        }
        else {
            startY = endY = (int) (sprite.getY() + velocity.y);
        }
        startX = (int) (sprite.getX());
        endX = (int) (sprite.getX() + sprite.getWidth());
        return getTiles(startX, startY, endX, endY, layerName);
    }

    public static Array<Rectangle> getHorizNeighbourTiles(Vector2 velocity, Sprite sprite, String layerName) {
        int startX, startY, endX, endY;
        if (velocity.x > 0) {
            startX = endX = (int) (sprite.getX() + sprite.getWidth() + velocity.x);
        }
        else {
            startX = endX = (int) (sprite.getX() + velocity.x);
        }
        startY = (int) (sprite.getY());
        endY = (int) (sprite.getY() + sprite.getHeight());
        return getTiles(startX, startY, endX, endY, layerName);
    }

    public static Rectangle spawnDoor(int mapWidth, int mapHeight) {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("NextLevel");
        if (layer == null) return null;
        for (int y = 0; y <= mapHeight; y++) {
            for (int x = 0; x <= mapWidth; x++) {
                TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                if (cell != null && cell.getTile().getProperties().containsKey("door")) {
                    Rectangle rect = new Rectangle(x, y, 1, 1);
                    return rect;
                }
            }
        }
        return null;
    }

    public static void spawnEnemies(Array<Enemy> enemies, float width, float height, TextureAtlas texturePack) {
        Iterator<MapObject> mapObjectIterator = map.getLayers().get("Enemies").getObjects().iterator();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("zombie")) {
                Zombie zombie = new Zombie(width, height, texturePack.findRegion(Сonstants.zombieWalk), mapObject.getX() * unitScale, mapObject.getY() * unitScale, 100);
                enemies.add(zombie);
            }
            if (mapObject.getName().equals("zombieSg")) {
                Zombie zombie = new Zombie(width, height, texturePack.findRegion(Сonstants.zombieSgWalk), mapObject.getX() * unitScale, mapObject.getY() * unitScale, 100);
                enemies.add(zombie);
            }
        }
    }

    public static void spawnPlatforms(Array<Platform> platforms, float width, float height, TextureAtlas texturePack) {
        Iterator<MapObject> mapObjectIterator = map.getLayers().get("Platforms").getObjects().iterator();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("platform")) {
                Platform platform = new Platform(width, height, texturePack.findRegion(Сonstants.platformSheet), (int)(mapObject.getX() * unitScale), (int)(mapObject.getY() * unitScale));
                platforms.add(platform);
            }
        }
    }

    public static void spawnStairs(Array<Stairs> stairs, float width, float height, TextureAtlas texturePack) {
        Iterator<MapObject> mapObjectIterator = map.getLayers().get("Stairs").getObjects().iterator();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("stair")) {
                Stairs stair = new Stairs(width, height, texturePack.findRegion(Сonstants.platformSheet), mapObject.getX() * unitScale, mapObject.getY() * unitScale);
                stairs.add(stair);
            }
        }
    }

    public static Array<Rectangle> getStairsRect(Array<Rectangle> rects) {
        Iterator<MapObject> mapObjectIterator = MapUtils.map.getLayers().get("Stairs").getObjects().iterator();
        rects.clear();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("stair")) {
                Rectangle rect = new Rectangle();
                rect.set(mapObject.getX() * unitScale, mapObject.getY() * unitScale, 1f, 1f );
                rects.add(rect);
            }
        }
        return rects;
    }

    public static void spawnTraps(Array<Traps> traps, float width, float height, TextureAtlas texturePack) {
        Iterator<MapObject> mapObjectIterator = map.getLayers().get("Traps").getObjects().iterator();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("trap")) {
                Traps trap = new Traps(width, height, texturePack.findRegion(Сonstants.platformSheet), mapObject.getX() * unitScale, mapObject.getY() * unitScale);
                traps.add(trap);
            }
        }
    }

    public static Array<Rectangle> getTrapsRect(Array<Rectangle> rects) {
        Iterator<MapObject> mapObjectIterator = MapUtils.map.getLayers().get("Traps").getObjects().iterator();
        rects.clear();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("trap")) {
                Rectangle rect = new Rectangle();
                rect.set(mapObject.getX() * unitScale, mapObject.getY() * unitScale, 1f, 1f );
                rects.add(rect);
            }
        }
        return rects;
    }

    public static Array<Rectangle> getPlatformRect(Array<Rectangle> rects) {
        Iterator<MapObject> mapObjectIterator = MapUtils.map.getLayers().get("Platforms").getObjects().iterator();
        rects.clear();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("platform")) {
                Rectangle rect = new Rectangle();
                rect.set((int)(mapObject.getX() * unitScale), (int)(mapObject.getY() * unitScale), 1f, 1f );
                rects.add(rect);
            }
        }
        return rects;
    }



    public static Array<Rectangle> spawnTrampoline() {
        Array<Rectangle> rects = new Array<Rectangle>();
        Iterator<MapObject> mapObjectIterator = MapUtils.map.getLayers().get("Trampoline").getObjects().iterator();
        rects.clear();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("tramp")) {
                Rectangle rect = new Rectangle();
                rect.set(mapObject.getX() * unitScale, mapObject.getY() * unitScale, 1f, 1f );
                rects.add(rect);
            }
        }
        return rects;
    }

    public static void spawnRopes(Array<Rope> ropes, float width, float height, TextureAtlas texturePack) {
        Iterator<MapObject> mapObjectIterator = map.getLayers().get("Ropes").getObjects().iterator();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("rope")) {
                Rope rope = new Rope(width, height, texturePack.findRegion(Сonstants.platformSheet), mapObject.getX() * unitScale, mapObject.getY() * unitScale);
                ropes.add(rope);
            }
        }
    }

    public static Array<Rectangle> getRopesRect(Array<Rectangle> rects) {
        Iterator<MapObject> mapObjectIterator = MapUtils.map.getLayers().get("Ropes").getObjects().iterator();
        rects.clear();
        float unitScale = Сonstants.unitScale;
        while (mapObjectIterator.hasNext()) {
            TiledMapTileMapObject mapObject = (TiledMapTileMapObject) mapObjectIterator.next();
            if (mapObject.getName().equals("rope")) {
                Rectangle rect = new Rectangle();
                rect.set(mapObject.getX() * unitScale, mapObject.getY() * unitScale, 1f, 1f );
                rects.add(rect);
            }
        }
        return rects;
    }
}
