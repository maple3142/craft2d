package net.maple3142.craft2d.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.crafting.Recipe;
import net.maple3142.craft2d.crafting.SimpleRecipe;
import net.maple3142.craft2d.item.Breakable;
import net.maple3142.craft2d.item.Item;
import net.maple3142.craft2d.item.Tool;
import net.maple3142.craft2d.item.block.CobblestoneBlock;
import net.maple3142.craft2d.item.ingredient.IronIngot;
import net.maple3142.craft2d.item.ingredient.Stick;

public class IronSword implements Item, Breakable, Tool {

    public static final int id = 26;

    public static final Recipe<IronSword> recipe = new SimpleRecipe<>(IronSword.class, 1, 1, 3,
            IronIngot.id,
            IronIngot.id,
            Stick.id);

    private final Image img = new Image(IronSword.class.getResource("/item/iron_sword.png").toString());

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
        return item instanceof IronSword;
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
        return 1;
    }
}
