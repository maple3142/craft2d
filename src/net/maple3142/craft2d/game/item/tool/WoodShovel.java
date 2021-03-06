package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Dirt;
import net.maple3142.craft2d.game.block.Grass;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.PlankOakBlock;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class WoodShovel extends BasicBreakable implements Item, Tool {

    public static final int id = 8;

    public static final Recipe<WoodShovel> recipe = new SimpleRecipe<>(WoodShovel.class, 1, 1, 3,
            PlankOakBlock.id,
            Stick.id,
            Stick.id);

    private final Image img = new Image(WoodShovel.class.getResource("/item/wood_shovel.png").toString());

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof WoodShovel;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (clz == Dirt.class || clz == Grass.class) {
            return 0.7;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 59;
    }
}
