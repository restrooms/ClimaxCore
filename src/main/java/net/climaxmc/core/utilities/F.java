package net.climaxmc.core.utilities;

import net.climaxmc.core.mysql.Rank;

public class F {
    private static String prefix(String prefix) {
        return C.GOLD + C.BOLD + prefix + C.WHITE + "\u00bb ";
    }

    public static String message(String prefix, String message) {
        return prefix(prefix) + C.GRAY + message;
    }

    public static String denyPermissions(Rank rank) {
        return prefix("Permissions") + C.GRAY + "This requires rank " + C.BLUE + rank.toString() + C.GRAY + ".";
    }

    public static String leftSword() {
        return C.RED + C.STRIKETHROUGH + "----" + C.DARK_GRAY + C.STRIKETHROUGH + "[-";
    }

    public static String rightSword() {
        return C.DARK_GRAY + C.STRIKETHROUGH + "-]" + C.RED + C.STRIKETHROUGH + "----";
    }

    public static String topLine() {
        String line = "\n";
        for (int i = 0; i < 5; i++) {
            line += leftSword() + C.RESET + " ";
        }
        return line;
    }

    public static String bottomLine() {
        String line = "";
        for (int i = 0; i < 5; i++) {
            line += rightSword() + C.RESET + " ";
        }
        return line;
    }
}
