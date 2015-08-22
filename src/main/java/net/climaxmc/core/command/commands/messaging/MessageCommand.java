package net.climaxmc.core.command.commands.messaging;

import lombok.Getter;
import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.F;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.*;

public class MessageCommand extends Command {
    @Getter
    private static Map<UUID, UUID> messagers = new HashMap<>();

    public MessageCommand() {
        super(new String[] {"message", "msg", "m", "tell", "w", "t"}, Rank.DEFAULT, F.message("Message", "/msg <player> <message>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length <= 1) {
            return usage;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            return F.message("Message", "That player is not online.");
        }

        messagers.put(player.getUniqueId(), target.getUniqueId());
        messagers.put(target.getUniqueId(), player.getUniqueId());

        String message = StringUtils.join(args, ' ', 1, args.length);

        player.sendMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.BOLD + " -> " + ChatColor.AQUA + "" + ChatColor.BOLD + target.getName() + ChatColor.AQUA + ": " + message.trim());
        target.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " -> " + ChatColor.DARK_AQUA + target.getName() + ChatColor.AQUA + ": " + message);

        if (ClimaxCore.getPlugin().getDescription().getAuthors().contains(target.getName())) {
            player.sendMessage(F.message("Message", "Owners and developers are often AFK due to development. Please be patient for a reply."));
        }

        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
        target.playSound(target.getLocation(), Sound.NOTE_PIANO, 2, 2);

        return null;
    }
}
