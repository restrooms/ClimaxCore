package net.climaxmc.core.command.commands.punishments;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class WarnCommand extends Command {
    public WarnCommand() {
        super(new String[] {"warn"}, Rank.HELPER, F.message("Punishments", "/warn <player> <reason>"));
    }

    @Override
    @SuppressWarnings("deprecation")
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
            targetData.addPunishment(new Punishment(targetData.getId(), Punishment.PunishType.WARNING, System.currentTimeMillis(), -1, playerData.getId(), reason));
            UtilPlayer.getAll(Rank.HELPER).forEach(staff -> staff.sendMessage(F.message("Punishments", C.RED + player.getName() + " warned " + targetData.getName() + " for " + finalReason + ".")));
            target.sendTitle(C.RED + C.BOLD + "Warning", C.RED + reason);
            UtilPlayer.sendActionBar(target, C.RED + "From " + player.getName());
        } else {
            return F.message("Punishments", "That player is not online!");
        }

        return null;
    }
}
