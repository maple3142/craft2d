package net.maple3142.craft2d.game.entity;

import com.google.gson.annotations.Expose;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.World;
import net.maple3142.craft2d.game.ui.storage.PlayerInventory;
import net.maple3142.craft2d.game.utils.Vector2;

public class Player implements Entity {
    private static final Image imgFront = new Image(Player.class.getResource("/entity/steve_front.png").toString());
    private static final Image imgLeft = new Image(Player.class.getResource("/entity/steve_left.png").toString());
    private static final Image imgRight = new Image(Player.class.getResource("/entity/steve_right.png").toString());
    private final int entityWidth = 1;
    private final int entityHeight = 2;

    // TODO: Introduce real acceleration calculation, or the speed may be affected by framerate
    @Expose
    public Vector2 position; // (x, y) represents left bottom of the entity
    @Expose
    public PlayerInventory inventory = new PlayerInventory();
    @Expose
    public PlayerFacing facing = PlayerFacing.FRONT;
    public World world;
    @Expose
    private final Vector2 velocity = new Vector2();

    public Player(World world, double x, double y) {
        position = new Vector2(x, y);
        this.world = world;
    }

    public void loop(World world, int dt) {
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

    public Image getImage() {
        switch (facing) {
            case FRONT:
                return imgFront;
            case LEFT:
                return imgLeft;
            case RIGHT:
                return imgRight;
        }
        return null;
    }

    public void draw(GraphicsContext ctx, double leftX, double topY) {
        int pX = (int) ((position.x - 0.5 - leftX) * Game.blockWidth);
        int pY = (int) ((topY - (position.y + 1)) * Game.blockHeight);

        ctx.drawImage(getImage(), pX, pY, entityWidth * Game.blockWidth, entityHeight * Game.blockHeight);
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
        return world.getPos(position.x + 0.5, position.y - 0.1) == null && world.getPos(position.x + 0.5, position.y) == null;
    }

    public boolean canGoLeft() {
        return world.getPos(position.x - 0.5, position.y - 0.1) == null && world.getPos(position.x - 0.5, position.y) == null;
    }

    public void faceFront() {
        facing = PlayerFacing.FRONT;
    }

    public void moveRight() {
        facing = PlayerFacing.RIGHT;
        if (canGoRight()) {
            velocity.x += 0.0007;
        }
    }

    public void moveLeft() {
        facing = PlayerFacing.LEFT;
        if (canGoLeft()) {
            velocity.x -= 0.0007;
        }
    }

    public void jump() {
        if (velocity.y == 0 && !bottomIsEmpty()) {
            velocity.y += 0.02; // v=sqrt(2gh)
        }
    }

    public boolean onInteractedWithEntity(Entity ent) {
        // returns true if entity should be removed
        if (ent instanceof FloatingItem) {
            var remain = inventory.tryFillItemStack(((FloatingItem) ent).stack);
            if (remain != null) {
                ((FloatingItem) ent).stack = remain;
                return false;
            } else {
                return true;
            }
        }
        return true;
    }
}
