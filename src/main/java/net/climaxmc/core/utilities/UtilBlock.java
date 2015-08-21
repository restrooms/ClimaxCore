package net.climaxmc.core.utilities;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public class UtilBlock {
    public static Map<Block, Double> getInRadius(Location loc, double radius) {
        return getInRadius(loc, radius, 999.0);
    }

    public static Map<Block, Double> getInRadius(Location loc, double radius, double heightLimit) {
        Map<Block, Double> blockList = new HashMap<>();
        for (int iR = (int)radius + 1, x = -iR; x <= iR; ++x) {
            for (int z = -iR; z <= iR; ++z) {
                for (int y = -iR; y <= iR; ++y) {
                    if (Math.abs(y) <= heightLimit) {
                        Block curBlock = loc.getWorld().getBlockAt((int) (loc.getX() + x), (int)(loc.getY() + y), (int)(loc.getZ() + z));
                        double offset = loc.subtract(curBlock.getLocation().add(0.5, 0.5, 0.5)).length();
                        if (offset <= radius) {
                            blockList.put(curBlock, 1.0 - offset / radius);
                        }
                    }
                }
            }
        }
        return blockList;
    }

    public static Map<Block, Double> getInRadius(Block block, double radius) {
        Map<Block, Double> blockList = new HashMap<>();
        for (int iR = (int)radius + 1, x = -iR; x <= iR; ++x) {
            for (int z = -iR; z <= iR; ++z) {
                for (int y = -iR; y <= iR; ++y) {
                    Block curBlock = block.getRelative(x, y, z);
                    double offset = block.getLocation().subtract(curBlock.getLocation()).length();
                    if (offset <= radius) {
                        blockList.put(curBlock, 1.0 - offset / radius);
                    }
                }
            }
        }
        return blockList;
    }
}
