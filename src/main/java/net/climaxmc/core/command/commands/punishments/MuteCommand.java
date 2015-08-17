package net.climaxmc.core.command.commands.punishments;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MuteCommand extends Command {
    public MuteCommand() {
        super(new String[] {"mute"}, Rank.MODERATOR, F.message("Punishments", "/mute <player> <reason>"));
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

        targetData.addPunishment(new Punishment(targetData.getId(), Punishment.PunishType.MUTE, System.currentTimeMillis(), -1, playerData.getId(), reason));
        UtilPlayer.getAll(Rank.HELPER).forEach(staff -> staff.sendMessage(F.message("Punishments", C.RED + player.getName() + " permanently muted " + targetData.getName() + " for " + finalReason + ".")));

        Player target = Bukkit.getPlayer(targetData.getUuid());
        if (target != null) {
            target.sendMessage(F.message("Punishments", C.RED + "You were permanently muted by " + player.getName() + " for " + reason + ".\n"
                    + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
        }

        return null;
    }
}
