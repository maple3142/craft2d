package net.maple3142.craft2d.game.key;

import javafx.scene.input.KeyCode;

import java.util.LinkedHashMap;
import java.util.Map;

public class KeyBinding {

    private Map<KeyType, KeyCode> mapping = getDefaultBinding();

    private Map<KeyType, KeyCode> getDefaultBinding() {
        // LinkedHashMap preserves the order
        return new LinkedHashMap<>() {{
            put(KeyType.JUMP1, KeyCode.W);
            put(KeyType.JUMP2, KeyCode.SPACE);
            put(KeyType.LEFT, KeyCode.A);
            put(KeyType.RIGHT, KeyCode.D);
            put(KeyType.FACE, KeyCode.S);
            put(KeyType.OPEN_INVENTORY, KeyCode.E);
            put(KeyType.PAUSE, KeyCode.ESCAPE);
        }};
    }

    public KeyCode getKeyCode(KeyType type) {
        return mapping.get(type);
    }

    public boolean changeBinding(KeyType type, KeyCode code) {
        if (!mapping.containsKey(type) || mapping.containsValue(code)) return false;
        mapping.replace(type, code);
        return true;
    }

    public Map<KeyType, KeyCode> getMapping() {
        return mapping;
    }
}
