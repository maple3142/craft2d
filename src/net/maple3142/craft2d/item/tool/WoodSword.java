package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.PlankOakBlock;
import net.maple3142.craft2d.item.ingredient.Stick;

public class WoodSword extends BasicBreakable implements Item, Tool {

    public static final int id = 9;

    public static final Recipe<WoodSword> recipe = new SimpleRecipe<>(WoodSword.class, 1, 1, 3,
            PlankOakBlock.id,
            PlankOakBlock.id,
            Stick.id);

    private final Image img = new Image(WoodSword.class.getResource("/item/wood_sword.png").toString());

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
        return item instanceof WoodSword;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 59;
    }
}
