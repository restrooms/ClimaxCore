package net.climaxmc.core.utilities;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

import java.util.*;

public class UtilMath {
    public static final float PI = 3.141593F;
    public static final float degreesToRadians = 0.01745329F;

    public static double offset(Entity a, Entity b) {
        return offset(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset(Location a, Location b) {
        return offset(a.toVector(), b.toVector());
    }

    public static double offset(Vector a, Vector b) {
        return a.subtract(b).length();
    }

    public static double offset2d(Entity a, Entity b) {
        return offset2d(a.getLocation().toVector(), b.getLocation().toVector());
    }

    public static double offset2d(Location a, Location b) {
        return offset2d(a.toVector(), b.toVector());
    }

    public static double offset2d(Vector a, Vector b) {
        a.setY(0);
        b.setY(0);
        return a.subtract(b).length();
    }

    public static float randRange(float min, float max) {
        return min + (float) Math.random() * (max - min);
    }

    public static int randRange(int min, int max) {
        Random rand = new Random();
        return rand.nextInt(max - min + 1) + min;
    }

    public static double round(double a, int b) {
        return (int) (a * Math.pow(10.0D, b) + 0.5D) / Math.pow(10.0D, b);
    }

    public static List<Vector> createCircle(int i, double y, double radius) {
        double amount = radius * 34.0D;
        double inc = 12.566370614359172D / amount;
        List<Vector> vecs = new ArrayList<>();

        double angle = i * inc;
        double x = radius * Math.cos(angle);
        double z = radius * Math.sin(angle);
        Vector v = new Vector(x, y, z);
        vecs.add(v);

        return vecs;
    }
}
