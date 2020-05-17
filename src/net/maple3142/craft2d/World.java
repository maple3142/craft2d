package net.maple3142.craft2d;

import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Dirt;
import net.maple3142.craft2d.block.Grass;
import net.maple3142.craft2d.block.Stone;

public class World {
    public static int worldHeight = 64;
    public static int worldWidth = 8192;
    public static int groundLevel = 27;
    public static int groundHeightChanges = 16;
    public Block[][] blocks = new Block[worldHeight][worldWidth];
    public int spawnX;
    public int spawnY;

    public static World generateRandom(long seed) {
        var world = new World();
        var dirtTopNoise = new PerlinNoise(seed);
        var dirtLayerHeightNoise = new PerlinNoise(seed);
        var dirtTop = dirtTopNoise.generateNoiseInteger(groundLevel, worldWidth, groundHeightChanges, 12, 2);
        var dirtLayerHeight = dirtLayerHeightNoise.generateNoiseInteger(0, worldWidth, 4, 6, 2);
        for (int x = 0; x < worldWidth; x++) {
            int stoneTop = dirtTop[x] - dirtLayerHeight[x] - 1;
            for (int y = stoneTop + 1; y < dirtTop[x]; y++) {
                world.blocks[y][x] = new Dirt();
            }
            for (int y = 0; y <= stoneTop; y++) {
                world.blocks[y][x] = new Stone();
            }
            world.blocks[dirtTop[x]][x] = new Grass();
        }
        world.spawnX = World.worldWidth / 2;
        world.spawnY = dirtTop[World.worldWidth / 2] + 1;
        return world;
    }

    public Block getPos(double x, double y) {
        return blocks[(int) Math.floor(y)][(int) Math.floor(x)];
    }
}
