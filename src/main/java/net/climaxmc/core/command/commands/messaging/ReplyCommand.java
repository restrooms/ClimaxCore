package net.climaxmc.core.command.commands.messaging;

import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.F;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class ReplyCommand extends Command {
    public ReplyCommand() {
        super(new String[] {"reply", "r"}, Rank.DEFAULT, F.message("Message", "/r <message>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length == 0) {
            return usage;
        }

        if (!MessageCommand.getMessagers().containsKey(player.getUniqueId())) {
            return F.message("Message", "You have not messaged anyone.");
        }

        Player target = Bukkit.getPlayer(MessageCommand.getMessagers().get(player.getUniqueId()));

        if (target == null) {
            return F.message("Message", "That player is no longer online.");
        }

        MessageCommand.getMessagers().put(player.getUniqueId(), target.getUniqueId());
        MessageCommand.getMessagers().put(target.getUniqueId(), player.getUniqueId());

        String message = StringUtils.join(args, ' ', 0, args.length);

        player.sendMessage(ChatColor.DARK_AQUA + player.getName() + ChatColor.BOLD + " -> " + ChatColor.AQUA + "" + ChatColor.BOLD + target.getName() + ChatColor.AQUA + ": " + message.trim());
        target.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + player.getName() + ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " -> " + ChatColor.DARK_AQUA + target.getName() + ChatColor.AQUA + ": " + message);

        player.playSound(player.getLocation(), Sound.NOTE_PIANO, 1, 1);
        target.playSound(target.getLocation(), Sound.NOTE_PIANO, 2, 2);

        return null;
    }
}
