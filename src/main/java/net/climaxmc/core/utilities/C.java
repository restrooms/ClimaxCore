package net.climaxmc.core.utilities;

import org.bukkit.Color;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class C {
    private static final char SECTION = '\u00a7';

    public static final String BLACK = SECTION + "0";
    public static final String DARK_BLUE = SECTION + "1";
    public static final String DARK_GREEN = SECTION + "2";
    public static final String DARK_AQUA = SECTION + "3";
    public static final String DARK_RED = SECTION + "4";
    public static final String DARK_PURPLE = SECTION + "5";
    public static final String GOLD = SECTION + "6";
    public static final String GRAY = SECTION + "7";
    public static final String DARK_GRAY = SECTION + "8";
    public static final String BLUE = SECTION + "9";
    public static final String GREEN = SECTION + "a";
    public static final String AQUA = SECTION + "b";
    public static final String RED = SECTION + "c";
    public static final String LIGHT_PURPLE = SECTION + "d";
    public static final String YELLOW = SECTION + "e";
    public static final String WHITE = SECTION + "f";

    public static final String RESET = SECTION + "r";

    public static final String OBFUSCATED = SECTION + "k";
    public static final String BOLD = SECTION + "l";
    public static final String STRIKETHROUGH = SECTION + "m";
    public static final String UNDERLINE = SECTION + "n";
    public static final String ITALIC = SECTION + "o";

    public static String getColor(String name) {
        for (Field field : C.class.getFields()) {
            if (field.getType().equals(String.class)) {
                if (field.getName().equalsIgnoreCase(name)) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        try {
                            return (String) field.get(null);
                        } catch (IllegalAccessException e) {
                            return WHITE;
                        }
                    }
                }
            }
        }

        return WHITE;
    }

    public static Color getColorFromCode(String colorCode) {
        if (colorCode.contains("1")) {
            return Color.BLUE;
        }
        if (colorCode.contains("2")) {
            return Color.GREEN;
        }
        if (colorCode.contains("3")) {
            return Color.AQUA;
        }
        if (colorCode.contains("4")) {
            return Color.RED;
        }
        if (colorCode.contains("5")) {
            return Color.PURPLE;
        }
        if (colorCode.contains("6")) {
            return Color.ORANGE;
        }
        if (colorCode.contains("7")) {
            return Color.GRAY;
        }
        if (colorCode.contains("8")) {
            return Color.GRAY;
        }
        if (colorCode.contains("9")) {
            return Color.BLUE;
        }
        if (colorCode.contains("a")) {
            return Color.GREEN;
        }
        if (colorCode.contains("b")) {
            return Color.AQUA;
        }
        if (colorCode.contains("c")) {
            return Color.RED;
        }
        if (colorCode.contains("d")) {
            return Color.PURPLE;
        }
        if (colorCode.contains("e")) {
            return Color.YELLOW;
        }
        return Color.WHITE;
    }
}
