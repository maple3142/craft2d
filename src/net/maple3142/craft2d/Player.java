package net.maple3142.craft2d;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Player {
    private static Image steve = new Image(Player.class.getResource("/entity/steve_front.png").toString());
    private int entityWidth = 1;
    private int entityHeight = 2;
    public Vector2 position; // (x, y) represents left bottom of the entity
    public Inventory inventory = new Inventory();
    private Vector2 velocity = new Vector2();
    private Vector2 gravityAcceleration = new Vector2(0, -0.0001);
    private World world;

    public Player(World world, int x, int y) {
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
        if (world.getPos(position.x + 0.5, position.y) != null) {
            position.y = Math.ceil(position.y);
        }
    }

    public void draw(GraphicsContext ctx, double leftX, double topY) {
        int pX = (int) ((position.x - leftX) * Game.blockWidth);
        int pY = (int) ((topY - position.y - entityHeight) * Game.blockHeight);
        ctx.drawImage(steve, pX, pY, entityWidth * Game.blockWidth, entityHeight * Game.blockHeight);
    }

    public boolean topIsEmpty() {
        return world.getPos(position.x + 0.5, position.y + 2) == null;
    }

    public boolean bottomIsEmpty() {
        return world.getPos(position.x + 0.5, position.y - 0.01) == null;
    }

    public boolean canGoRight() {
        return world.getPos(position.x + 1, position.y) == null && world.getPos(position.x + 1, position.y + 1) == null;
    }

    public boolean canGoLeft() {
        return world.getPos(position.x, position.y) == null && world.getPos(position.x, position.y + 1) == null;
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
