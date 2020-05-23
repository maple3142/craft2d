package net.maple3142.craft2d.ui.storage;

public class PlayerInventoryStorageLayout implements StorageLayout {

    // https://minecraft.gamepedia.com/File:Items_slot_number.png

    @Override
    public int getId(int row, int col) {
        if (col < 0 || col > 8 || row < 0 || row > 7) return -1;
        if (row == 0 && col == 0) return 103;
        if (row == 1 && col == 0) return 102;
        if (row == 2 && col == 0) return 101;
        if (row == 3 && col == 0) return 100;
        if (row == 4) return 9 + col;
        if (row == 5) return 18 + col;
        if (row == 6) return 27 + col;
        if (row == 7) return col;
        return -1;
    }
}
