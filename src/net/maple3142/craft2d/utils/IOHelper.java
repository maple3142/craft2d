package net.maple3142.craft2d.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Furnace;
import net.maple3142.craft2d.block.Loopable;
import net.maple3142.craft2d.entity.Entity;
import net.maple3142.craft2d.entity.FloatingItem;
import net.maple3142.craft2d.item.Item;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class IOHelper {

    private static Gson gson;

    static {
        try {
            var blks = ReflectionHelper.getClasses("net.maple3142.craft2d.block");
            var blockRta = RuntimeTypeAdapterFactory.of(Block.class);
            for (Class<?> c : blks) {
                if (Block.class.isAssignableFrom(c)) {
                    blockRta.registerSubtype(c.asSubclass(Block.class));
                }
            }
            var items = ReflectionHelper.getClasses("net.maple3142.craft2d.item");
            var itemRta = RuntimeTypeAdapterFactory.of(Item.class);
            for (Class<?> c : items) {
                if (Item.class.isAssignableFrom(c)) {
                    itemRta.registerSubtype(c.asSubclass(Item.class));
                }
            }
            var loopableRta = RuntimeTypeAdapterFactory.of(Loopable.class);
            loopableRta.registerSubtype(Furnace.class);
            var entityRta = RuntimeTypeAdapterFactory.of(Entity.class);
            entityRta.registerSubtype(FloatingItem.class);
            gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                    .registerTypeAdapterFactory(itemRta)
                    .registerTypeAdapterFactory(blockRta)
                    .registerTypeAdapterFactory(loopableRta)
                    .registerTypeAdapterFactory(entityRta)
                    .create();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            assert (false);
        }
    }

    public static String serialize(Game game) {
        return gson.toJson(game);
    }

    public static Game deserialize(String json) {
        var game = gson.fromJson(json, Game.class);
        game.player.world = game.world;
        return game;
    }

    public static boolean writeGameToPath(Path path, Game game) {
        var json = serialize(game);
        try {
            Files.write(path, gzipCompress(json));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static Game readGameFromPath(Path path) {
        try {
            var json = gzipDecompress(Files.readAllBytes(path));
            return deserialize(json);
        } catch (IOException e) {
            return null;
        }
    }

    public static byte[] gzipCompress(String data) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length());
        GZIPOutputStream gzip = new GZIPOutputStream(bos);
        gzip.write(data.getBytes());
        gzip.close();
        byte[] compressed = bos.toByteArray();
        bos.close();
        return compressed;
    }

    public static String gzipDecompress(byte[] compressed) throws IOException {
        ByteArrayInputStream bis = new ByteArrayInputStream(compressed);
        GZIPInputStream gis = new GZIPInputStream(bis);
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        gis.close();
        bis.close();
        return sb.toString();
    }
}
