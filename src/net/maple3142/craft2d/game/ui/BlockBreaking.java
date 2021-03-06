package net.maple3142.craft2d.game.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.maple3142.craft2d.game.Game;
import net.maple3142.craft2d.game.block.BreakableBlock;
import net.maple3142.craft2d.game.block.Loopable;
import net.maple3142.craft2d.game.item.ItemStack;
import net.maple3142.craft2d.game.utils.BreakingTimeCalculator;
import net.maple3142.craft2d.game.utils.MouseTracker;

public class BlockBreaking {

    private static final double bottomPadding = 5;
    private static final double width = 80;
    private static final double height = 10;
    private static final double borderWidth = 1;
    private final Game game;
    public boolean isBreaking = false;
    public int currentBreakingX;
    public int currentBreakingY;
    public int startBreakingTime = 0;
    public int endBreakingTime = 0;

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
        var blk = game.world.blocks[y][x];
        if (!(blk instanceof BreakableBlock)) return;
        isBreaking = true;
        startBreakingTime = (int) (System.nanoTime() / 1000000);
        currentBreakingX = x;
        currentBreakingY = y;
        var tool = game.player.inventory.getSelectedTool();
        var time = BreakingTimeCalculator.calculate(tool, (BreakableBlock) blk);
        endBreakingTime = startBreakingTime + time;
    }

    public ItemStack endBreaking(boolean success) {
        // returns dropped item stack if exists
        isBreaking = false;
        endBreakingTime = 0;
        if (success) {
            var blk = game.world.blocks[currentBreakingY][currentBreakingX];
            if (blk instanceof BreakableBlock) {
                if (blk instanceof Loopable) {
                    game.loopables.remove(blk);
                }
                game.world.blocks[currentBreakingY][currentBreakingX] = null;
                var tool = game.player.inventory.getSelectedTool();
                return ((BreakableBlock) blk).getDroppedItem(tool);
            }
        }
        return null;
    }

    public double getBreakingPercentage(int timeMs) {
        int breakingTotal = endBreakingTime - startBreakingTime;
        int currentBreakingTime = timeMs - startBreakingTime;
        return (double) currentBreakingTime / breakingTotal;
    }
}
