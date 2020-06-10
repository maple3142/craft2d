package net.maple3142.craft2d.game.entity;

import javafx.scene.canvas.GraphicsContext;
import net.maple3142.craft2d.game.World;
import net.maple3142.craft2d.game.utils.Vector2;

public interface Entity {
    Vector2 gravityAcceleration = new Vector2(0, -0.0001);

    void loop(World world, int dt);

    void draw(GraphicsContext ctx, double leftX, double topY);

    Vector2 getPosition();
}
