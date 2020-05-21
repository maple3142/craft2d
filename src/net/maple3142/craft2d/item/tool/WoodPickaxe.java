package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.Stone;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;

public class WoodPickaxe implements Item, Breakable, Tool {

    public static final int id = 7;

    @Override
    public int getId() {
        return id;
    }

    private Image img = new Image(WoodPickaxe.class.getResource("/item/wood_pickaxe.png").toString());

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof WoodPickaxe;
    }

    @Override
    public int getFullDurability() {
        return 0;
    }

    @Override
    public int getDurability() {
        return 0;
    }

    @Override
    public boolean isBroken() {
        return false;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (clz == Stone.class) {
            return 0.5;
        }
        return 1;
    }
}
