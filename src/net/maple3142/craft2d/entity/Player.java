package net.maple3142.craft2d.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.World;
import net.maple3142.craft2d.ui.storage.PlayerInventory;
import net.maple3142.craft2d.utils.Vector2;

public class Player implements Entity {
    private static final Image steve = new Image(Player.class.getResource("/entity/steve_front.png").toString());
    public Vector2 position; // (x, y) represents left bottom of the entity
    public PlayerInventory inventory = new PlayerInventory();
    private final int entityWidth = 1;
    private final int entityHeight = 2;
    private final Vector2 velocity = new Vector2();
    private final Vector2 gravityAcceleration = new Vector2(0, -0.0001);
    private final World world;

    public Player(World world, double x, double y) {
        position = new Vector2(x, y);
        this.world = world;
    }

    public void loop(int dt) {
        if (bottomIsEmpty()) {
            velocity.plus(Vector2.multiply(gravityAcceleration, dt));
        }
        if (velocity.y < 0 && !bottomIsEmpty()) {
            velocity.y = 0;
        }
        if (velocity.y > 0 && !topIsEmpty()) {
            velocity.y = 0;
        }
        if (velocity.x > 0 && !canGoRight()) {
            velocity.x = 0;
        }
        if (velocity.x < 0 && !canGoLeft()) {
            velocity.x = 0;
        }
        position.plus(Vector2.multiply(velocity, dt));
        velocity.x *= 0.9;
        if (world.getPos(position.x, position.y - 1) != null) {
            position.y = Math.ceil(position.y);
        }
    }

    public void draw(GraphicsContext ctx, double leftX, double topY) {
        int pX = (int) ((position.x - 0.5 - leftX) * Game.blockWidth);
        int pY = (int) ((topY - (position.y + 1)) * Game.blockHeight);
        ctx.drawImage(steve, pX, pY, entityWidth * Game.blockWidth, entityHeight * Game.blockHeight);
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public boolean topIsEmpty() {
        return world.getPos(position.x, position.y + 1) == null;
    }

    public boolean bottomIsEmpty() {
        return world.getPos(position.x, position.y - 1.1) == null;
    }

    public boolean canGoRight() {
        return world.getPos(position.x + 0.5, position.y - 1) == null && world.getPos(position.x + 0.5, position.y) == null;
    }

    public boolean canGoLeft() {
        return world.getPos(position.x - 0.5, position.y - 1) == null && world.getPos(position.x - 0.5, position.y) == null;
    }

    public void moveRight() {
        if (canGoRight()) {
            velocity.x += 0.0005;
        }
    }

    public void moveLeft() {
        if (canGoLeft()) {
            velocity.x -= 0.0005;
        }
    }

    public void jump() {
        if (velocity.y == 0 && !bottomIsEmpty()) {
            velocity.y += 0.03;
        }
    }
}
