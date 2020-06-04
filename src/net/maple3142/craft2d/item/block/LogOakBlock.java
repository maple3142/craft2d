package net.maple3142.craft2d.item.block;

import javafx.scene.image.Image;
import net.maple3142.craft2d.block.Block;
import net.maple3142.craft2d.block.LogOak;
import net.maple3142.craft2d.item.*;
import net.maple3142.craft2d.item.ingredient.Charcoal;

public class LogOakBlock implements PlaceableItem, Stackable, Burnable, Fuel {

    public static final int id = 4;
    private final Block block = new LogOak();

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
        return item instanceof LogOakBlock;
    }

    @Override
    public ItemStack getResultItemStack() {
        return new ItemStack(new Charcoal());
    }

    @Override
    public boolean isTargetCompatible(ItemStack stk) {
        if (stk == null) return true;
        if (!stk.isStackable()) return false;
        return stk.getItemsNum() + 1 < ItemStack.maxItems && stk.getItem().getId() == Charcoal.id;
    }

    @Override
    public double getEnergyUnit() {
        return 1.5;
    }
}
