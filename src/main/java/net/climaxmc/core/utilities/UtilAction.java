package net.climaxmc.core.utilities;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class UtilAction {
    public static void velocity(Entity entity, double str, double yAdd, double yMax, boolean groundBoost) {
        velocity(entity, entity.getLocation().getDirection(), str, false, 0.0, yAdd, yMax, groundBoost);
    }

    public static void velocity(Entity entity, Vector vector, double str, boolean ySet, double yBase, double yAdd, double yMax, boolean groundBoost) {
        if (Double.isNaN(vector.getX()) || Double.isNaN(vector.getY()) || Double.isNaN(vector.getZ()) || vector.length() == 0.0) {
            return;
        }
        if (ySet) {
            vector.setY(yBase);
        }
        vector.normalize();
        vector.multiply(str);
        vector.setY(vector.getY() + yAdd);
        if (vector.getY() > yMax) {
            vector.setY(yMax);
        }
        if (groundBoost && entity.isOnGround()) {
            vector.setY(vector.getY() + 0.2);
        }
        entity.setFallDistance(0.0f);
        entity.setVelocity(vector);
    }
}
