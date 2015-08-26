package net.climaxmc.core.utilities;

import net.climaxmc.core.mysql.Rank;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;

public class F {
    private static BaseComponent[] prefix(String prefix) {
        return new ComponentBuilder(prefix).color(ChatColor.GOLD).bold(true).append(C.WHITE + "\u00bb ").create();
    }

    public static TextComponent message(String prefix, String message) {
        TextComponent component = new TextComponent(prefix(prefix));
        component.setColor(ChatColor.GRAY);
        component.setBold(true);
        component.addExtra(message);
        return component;
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
