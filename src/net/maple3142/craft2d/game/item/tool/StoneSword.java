package net.maple3142.craft2d.game.item.tool;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.crafting.Recipe;
import net.maple3142.craft2d.game.crafting.SimpleRecipe;
import net.maple3142.craft2d.game.item.Item;
import net.maple3142.craft2d.game.item.Tool;
import net.maple3142.craft2d.game.item.block.CobblestoneBlock;
import net.maple3142.craft2d.game.item.ingredient.Stick;

public class StoneSword extends BasicBreakable implements Item, Tool {

    public static final int id = 20;

    public static final Recipe<StoneSword> recipe = new SimpleRecipe<>(StoneSword.class, 1, 1, 3,
            CobblestoneBlock.id,
            CobblestoneBlock.id,
            Stick.id);

    private final Image img = new Image(StoneSword.class.getResource("/item/stone_sword.png").toString());

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
        return item instanceof StoneSword;
    }

    @Override
    public double getHardnessMultiplier(Block target) {
        return 1;
    }

    @Override
    public int getFullDurability() {
        return 131;
    }
}
