package net.maple3142.craft2d.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.maple3142.craft2d.BreakingTimeCalculator;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.item.ItemStack;

public class BlockBreaking {

    private static final double bottomPadding = 5;
    private static final double width = 80;
    private static final double height = 10;
    private static final double borderWidth = 1;
    public boolean isBreaking = false;
    public int currentBreakingX;
    public int currentBreakingY;
    public int startBreakingTime = 0;
    public int endBreakingTime = 0;
    private final Game game;

    public BlockBreaking(Game game) {
        this.game = game;
    }

    public void drawProgressBar(GraphicsContext ctx, MouseTracker mouse, double percent) {
        double x = mouse.getX() - width / 2;
        double y = mouse.getY() - bottomPadding - height;

        ctx.setFill(Color.WHITE);
        ctx.fillRect(x, y, width, height);

        ctx.setStroke(Color.BLACK);
        ctx.setLineWidth(borderWidth);
        ctx.strokeRect(x, y, width, height);

        ctx.setFill(Color.AQUA);
        ctx.fillRect(x + borderWidth, y + borderWidth, width * percent, height - 2 * borderWidth);
    }

    public void startBreaking(int x, int y) {
        isBreaking = true;
        startBreakingTime = (int) (System.nanoTime() / 1000000);
        currentBreakingX = x;
        currentBreakingY = y;
        var tool = game.player.inventory.getSelectedTool();
        var time = BreakingTimeCalculator.calculate(tool, game.world.blocks[currentBreakingY][currentBreakingX]);
        endBreakingTime = startBreakingTime + time;
    }

    public ItemStack endBreaking(boolean success) {
        // returns dropped item stack if exists
        if (success) {
            var blk = game.world.blocks[currentBreakingY][currentBreakingX];
            if (blk != null) {
                game.world.blocks[currentBreakingY][currentBreakingX] = null;
                var tool = game.player.inventory.getSelectedTool();
                return blk.getDroppedItem(tool);
            }
        }
        isBreaking = false;
        endBreakingTime = 0;
        return null;
    }

    public double getBreakingPercentage(int timeMs) {
        int breakingTotal = endBreakingTime - startBreakingTime;
        int currentBreakingTime = timeMs - startBreakingTime;
        return (double) currentBreakingTime / breakingTotal;
    }
}
