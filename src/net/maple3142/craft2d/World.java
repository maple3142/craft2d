package net.maple3142.craft2d;

import net.maple3142.craft2d.block.*;
import net.maple3142.craft2d.utils.OpenSimplexNoise;
import net.maple3142.craft2d.utils.PerlinNoise;

import java.util.Random;

public class World {
    public static int worldHeight = 256;
    public static int worldWidth = 8192;
    public static int groundBaseLevel = 128;
    public static int groundHeightChanges = 16;
    public Block[][] blocks = new Block[worldHeight][worldWidth];
    public int spawnX;
    public int spawnY;

    public static World generateRandom(long seed) {
        var seedGen = new Random(seed);
        var world = new World();
        var dirtTopNoise = new PerlinNoise(seedGen.nextLong());
        var dirtTop = dirtTopNoise.generateNoiseInteger(groundBaseLevel, worldWidth, groundHeightChanges, 12, 2);
        var dirtLayerHeightNoise = new PerlinNoise(seedGen.nextLong());
        var dirtLayerHeight = dirtLayerHeightNoise.generateNoiseInteger(0, worldWidth, 4, 6, 2);
        var treeNoise = new PerlinNoise(seedGen.nextLong());
        var treeThreshold = treeNoise.generateNoise(0, worldWidth, 4, 6, 2);

        int lastTreeX = -10;
        for (int x = 0; x < worldWidth; x++) {
            int stoneTop = dirtTop[x] - dirtLayerHeight[x] - 1;
            for (int y = stoneTop + 1; y < dirtTop[x]; y++) {
                world.blocks[y][x] = new Dirt();
            }
            for (int y = 1; y <= stoneTop; y++) {
                world.blocks[y][x] = new Stone();
            }
            world.blocks[0][x] = new Bedrock();
            world.blocks[dirtTop[x]][x] = new Grass();
            if (treeThreshold[x] >= 4.5 && x - lastTreeX >= 3) {
                int treeHeight = (int) treeThreshold[x];
                for (int i = 1; i <= treeHeight; i++) {
                    world.blocks[dirtTop[x] + i][x] = new LogOak();
                }
                int treeTop = dirtTop[x] + treeHeight;
                if (x + 1 < worldWidth) {
                    world.blocks[treeTop][x + 1] = new LeavesOak();
                    world.blocks[treeTop + 1][x + 1] = new LeavesOak();
                }
                if (x - 1 >= 0) {
                    world.blocks[treeTop][x - 1] = new LeavesOak();
                    world.blocks[treeTop + 1][x - 1] = new LeavesOak();
                }
                world.blocks[treeTop + 1][x] = new LeavesOak();
                world.blocks[treeTop + 2][x] = new LeavesOak();
                lastTreeX = x;
            }
        }
        world.spawnX = World.worldWidth / 2;
        world.spawnY = dirtTop[World.worldWidth / 2] + 1;
        if (world.blocks[world.spawnY][world.spawnX] != null) {
            world.spawnX++;
        }

        var caveNoise = new OpenSimplexNoise(seedGen.nextInt());
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 1; y < dirtTop[x] - 5; y++) {
                if (caveNoise.eval(x / 5.0, y / 5.0) < -0.2) {
                    world.blocks[y][x] = null;
                }
            }
        }

        var coalNoise = new OpenSimplexNoise(seedGen.nextInt());
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 1; y < dirtTop[x] - 7; y++) {
                if (coalNoise.eval(x / 3.0, y / 3.0) < -0.6) {
                    world.blocks[y][x] = new CoalOre();
                }
            }
        }

        var ironNoise = new OpenSimplexNoise(seedGen.nextInt());
        for (int x = 0; x < worldWidth; x++) {
            for (int y = 1; y < dirtTop[x] - 27; y++) {
                if (ironNoise.eval(x / 3.0, y / 3.0) < -0.65) {
                    world.blocks[y][x] = new IronOre();
                }
            }
        }

        return world;
    }

    public Block getPos(double x, double y) {
        return blocks[(int) Math.floor(y)][(int) Math.floor(x)];
    }
}
