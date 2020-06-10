package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.ingredient.IronIngot;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class IronSword extends BasicBreakable implements Item, Tool {

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
    public double getHardnessMultiplier(Block target) {
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 250;
    }
}
