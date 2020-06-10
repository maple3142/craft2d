package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.Wooden;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.CobblestoneBlock;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class StoneAxe extends BasicBreakable implements Item, Tool {

    public static final int id = 17;

    public static final Recipe<StoneAxe> recipe = new SimpleRecipe<>(StoneAxe.class, 1, 2, 3,
            CobblestoneBlock.id, CobblestoneBlock.id,
            CobblestoneBlock.id, Stick.id,
            0, Stick.id);

    private final Image img = new Image(StoneAxe.class.getResource("/item/stone_axe.png").toString());

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
        return item instanceof StoneAxe;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        var clz = target.getClass();
        if (Wooden.class.isAssignableFrom(clz)) { // clz implements Wooden
            return 0.5;
        }
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 131;
    }
}
