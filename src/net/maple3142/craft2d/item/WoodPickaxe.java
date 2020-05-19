package net.maple3142.craft2d.item;

import javafx.scene.image.Image;

public class WoodPickaxe implements Item, Breakable {
    private Image img = new Image(WoodPickaxe.class.getResource("/item/wood_pickaxe.png").toString());

    @Override
    public Image getImage() {
        return img;
    }

    @Override
    public boolean equals(Item item) {
        return item instanceof WoodPickaxe;
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
}
