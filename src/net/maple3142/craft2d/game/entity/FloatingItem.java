package net.maple3142.craft2d.game.entity;

import com.google.gson.annotations.Expose;
import javafx.scene.canvas.GraphicsContext;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.World;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.utils.Vector2;

public class FloatingItem implements Entity {

    private final static double size = 0.3; // size is relative to normal block

    @Expose
    public ItemStack stack;
    @Expose
    private Vector2 position;
    @Expose
    private final Vector2 velocity = new Vector2(0, 0);

    public FloatingItem(ItemStack stack, Vector2 position) {
        if (stack == null) throw new IllegalArgumentException("Stack shouldn't be null");
        this.stack = stack;
        this.position = position;
    }

    @Override
    public void loop(World world, int dt) {
        if (world.getPos(position.x, position.y - size / 2 - 0.01) == null) {
            velocity.plus(Vector2.multiply(gravityAcceleration, dt));
        }
        if (velocity.y < 0 && world.getPos(position.x, position.y - size / 2) != null) {
            velocity.y = 0;
        }
        var nextPos = Vector2.plus(position, Vector2.multiply(velocity, dt));
        while (world.getPos(nextPos.x, nextPos.y - size / 2) != null) {
            nextPos.y += 0.001;
        }
        position = nextPos;
    }

    @Override
    public void draw(GraphicsContext ctx, double leftX, double topY) {
        double x = (position.x - size / 2 - leftX) * Game.blockWidth;
        double y = (topY - (position.y + size / 2)) * Game.blockHeight;
        if (stack.getItemsNum() > 1) {
            ctx.drawImage(stack.getItem().getImage(), x, y, size * Game.blockHeight, size * Game.blockHeight);
            ctx.drawImage(stack.getItem().getImage(), x - 3, y - 3, size * Game.blockHeight, size * Game.blockHeight);
        } else {
            ctx.drawImage(stack.getItem().getImage(), x, y, size * Game.blockHeight, size * Game.blockHeight);
        }
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }
}
