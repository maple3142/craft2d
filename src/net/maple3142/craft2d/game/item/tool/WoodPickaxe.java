package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.StoneLike;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.PlankOakBlock;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class WoodPickaxe extends BasicBreakable implements Item, Tool {

    public static final int id = 7;

    public static final Recipe<WoodPickaxe> recipe = new SimpleRecipe<>(WoodPickaxe.class, 1, 3, 3,
            PlankOakBlock.id, PlankOakBlock.id, PlankOakBlock.id,
            0, Stick.id, 0,
            0, Stick.id, 0);

    private final Image img = new Image(WoodPickaxe.class.getResource("/item/wood_pickaxe.png").toString());

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
        return item instanceof WoodPickaxe;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (StoneLike.class.isAssignableFrom(clz)) {
            return 0.7;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 59;
    }
}
