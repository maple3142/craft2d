package net.maple3142.craft2d.game.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.game.block.Block;
import net.maple3142.craft2d.game.block.IronOre;
import net.maple3142.craft2d.game.item.*;
import net.maple3142.craft2d.game.item.ingredient.IronIngot;

public class IronOreBlock implements PlaceableItem, Stackable, Burnable {

    public static final int id = 16;

    private final Block block = new IronOre();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Block getPlacedBlock() {
        return block;
    }

    @Override
    public Image getImage() {
        return block.getImage();
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof IronOreBlock;
    }

    @Override
    public ItemStack getResultItemStack() {
        return new ItemStack(new IronIngot());
    }

    @Override
    public boolean isTargetCompatible(ItemStack stk) {
        if (stk == null) return true;
        if (!stk.isStackable()) return false;
        return stk.getItemsNum() + 1 < ItemStack.maxItems && stk.getItem().getId() == IronIngot.id;
    }
}
