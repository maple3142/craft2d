package net.maple3142.craft2d.game.utils;

import net.maple3142.craft2d.game.block.BreakableBlock;
import net.maple3142.craft2d.game.item.Tool;

public class BreakingTimeCalculator {
    public static int hardnessToTime = 1000;

    public static int calculate(Tool tool, BreakableBlock target) { // ms
        if (target == null) return 0;
        double h = target.getHardness();
        if (h < 0) return -1;
        if (h == 0) return 0;
        var mul = tool == null ? 1 : tool.getHardnessMultiplier(target);
        return (int) (h * mul * hardnessToTime);
    }
}
