package net.climaxmc.core.command.commands.punishments;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KickCommand extends Command {
    public KickCommand() {
        super(new String[] {"kick"}, Rank.HELPER, F.message("Punishments", "/kick <player> <reason>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length < 2) {
            return usage;
        }

        PlayerData playerData = ClimaxCore.getPlayerData(player);
        PlayerData targetData = ClimaxCore.getPlayerData(args[0]);

        if (targetData == null) {
            return F.message("Punishments", "That player has never joined!");
        }

        String reason = "";
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        reason = reason.trim();
        final String finalReason = reason;

        Player target = Bukkit.getPlayer(targetData.getUuid());
        if (target != null) {
            targetData.addPunishment(new Punishment(targetData.getId(), Punishment.PunishType.KICK, System.currentTimeMillis(), -1, playerData.getId(), reason));
            UtilPlayer.getAll(Rank.HELPER).forEach(staff -> staff.sendMessage(F.message("Punishments", C.RED + player.getName() + " kicked " + targetData.getName() + " for " + finalReason + ".")));
            target.kickPlayer(F.message("Punishments", C.RED + "You were kicked by " + player.getName() + " for " + reason + "."));
        } else {
            return F.message("Punishments", "That player is not online!");
        }

        return null;
    }
}
