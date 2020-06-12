package net.maple3142.craft2d.game.key;

public enum KeyType {
    JUMP1("Jump1"),
    JUMP2("Jump2"),
    LEFT("Left"),
    RIGHT("Right"),
    FACE("Face"),
    OPEN_INVENTORY("Open inventory"),
    PAUSE("Pause");

    private String name;

    KeyType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
