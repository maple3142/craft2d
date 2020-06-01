package net.maple3142.craft2d.entity;

import javafx.scene.canvas.GraphicsContext;
import net.maple3142.craft2d.utils.Vector2;

public interface Entity {
    void loop(int dt);

    void draw(GraphicsContext ctx, double leftX, double topY);

    Vector2 getPosition();
}
