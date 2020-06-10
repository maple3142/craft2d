package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Wooden;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.ingredient.IronIngot;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class IronAxe extends BasicBreakable implements Item, Tool {

    public static final int id = 23;

    public static final Recipe<IronAxe> recipe = new SimpleRecipe<>(IronAxe.class, 1, 2, 3,
            IronIngot.id, IronIngot.id,
            IronIngot.id, Stick.id,
            0, Stick.id);

    private final Image img = new Image(IronAxe.class.getResource("/item/iron_axe.png").toString());

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
        return item instanceof IronAxe;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (Wooden.class.isAssignableFrom(clz)) { // clz implements Wooden
            return 0.35;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 250;
    }
}
