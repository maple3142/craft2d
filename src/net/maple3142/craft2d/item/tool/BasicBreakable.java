package net.maple3142.craft2d.item.tool;

import com.google.gson.annotations.Expose;
import net.maple3142.craft2d.item.Breakable;

public abstract class BasicBreakable implements Breakable {

    @Expose
    protected int durability = getFullDurability();

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public boolean isBroken() {
        return durability == 0;
    }

    @Override
    public boolean isIntact() {
        return durability == getFullDurability();
    }

    @Override
    public void reduceDurabilityByOne() {
        if (durability > 0) durability--;
    }

    @Override
    public void setDurability(int d) {
        durability = d;
    }
}
