package net.maple3142.craft2d.item;

public interface Breakable {
    int getFullDurability();

    int getDurability();

    boolean isBroken();
}