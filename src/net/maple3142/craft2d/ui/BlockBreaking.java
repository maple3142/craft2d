package net.maple3142.craft2d.ui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import net.maple3142.craft2d.BreakingTimeCalculator;
import net.maple3142.craft2d.Game;
import net.maple3142.craft2d.MouseTracker;
import net.maple3142.craft2d.World;

public class BlockBreaking {

    private static final double bottomPadding = 5;
    private static final double width = 80;
    private static final double height = 10;
    private static final double borderWidth = 1;

    private Game game;

    public BlockBreaking(Game game) {
        this.game = game;
    }


    public void drawProgressBar(GraphicsContext ctx, MouseTracker mouse, double percent) {
        double x = mouse.getX() - width / 2;
        double y = mouse.getY() - bottomPadding - height;

        ctx.setFill(Color.WHITE);
        ctx.fillRect(x, y, width, height);

        ctx.setFill(Color.BLACK);
        ctx.setLineWidth(borderWidth);
        ctx.beginPath();
        ctx.rect(x, y, width, height);
        ctx.stroke();
        ctx.closePath();

        ctx.setFill(Color.AQUA);
        ctx.fillRect(x + borderWidth, y + borderWidth, width * percent, height - 2 * borderWidth);
    }

    public boolean isBreaking = false;
    public int currentBreakingX;
    public int currentBreakingY;
    public int startBreakingTime = 0;
    public int endBreakingTime = 0;

    public void tryStartBreaking(World world, int x, int y) {
        if (isBreaking) {
            if (x != currentBreakingX || y != currentBreakingY) {
                endBreaking(false);
            } else {
                return;
            }
        }
        if (world.blocks[y][x] == null) return;
        isBreaking = true;
        startBreakingTime = (int) (System.nanoTime() / 1000000);
        currentBreakingX = x;
        currentBreakingY = y;
        var tool = game.player.inventory.getSelectedTool();
        var time = BreakingTimeCalculator.calculate(tool, game.world.blocks[currentBreakingY][currentBreakingX]);
        endBreakingTime = startBreakingTime + time;
    }

    public void endBreaking(boolean success) {
        if (success) {
            var blk = game.world.blocks[currentBreakingY][currentBreakingX];
            if (blk != null) {
                game.world.blocks[currentBreakingY][currentBreakingX] = null;
                var tool = game.player.inventory.getSelectedTool();
                var drop = blk.getDroppedItem(tool);
                System.out.println(drop);
            }
        }
        isBreaking = false;
        endBreakingTime = 0;
    }

    public double getBreakingPercentage(int timeMs) {
        int breakingTotal = endBreakingTime - startBreakingTime;
        int currentBreakingTime = timeMs - startBreakingTime;
        return (double) currentBreakingTime / breakingTotal;
    }
}
