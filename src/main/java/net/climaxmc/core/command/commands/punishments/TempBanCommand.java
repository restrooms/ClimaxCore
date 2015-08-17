package net.climaxmc.core.command.commands.punishments;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TempBanCommand extends Command {
    public TempBanCommand() {
        super(new String[] {"tempban"}, Rank.MODERATOR, F.message("Punishments", "/tempban <player> <time (d/h/m)> <reason>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length < 3) {
            return usage;
        }

        PlayerData playerData = ClimaxCore.getPlayerData(player);
        PlayerData targetData = ClimaxCore.getPlayerData(args[0]);

        if (targetData == null) {
            return F.message("Punishments", "That player has never joined!");
        }

        long time;

        String timeString = args[1];
        char timeChar = Character.toLowerCase(timeString.charAt(timeString.length() - 1));
        Time timeUnit;
        String timeNumeral = timeString.substring(0, timeString.length() - 1);

        try {
            time = Long.parseLong(timeNumeral);
        } catch (NumberFormatException e) {
            return F.message("Punishments", "That is not a valid time!");
        }

        timeUnit = Time.fromId(timeChar);

        if (timeUnit == null) {
            return F.message("Punishments", "Incorrect time unit! Please use one of the following: m, h, d");
        }

        time = time * timeUnit.getMilliseconds();

        String reason = "";
        for (int i = 2; i < args.length; i++) {
            reason += args[i] + " ";
        }
        reason = reason.trim();

        final String finalReason = reason;
        final long finalTime = time;
        targetData.addPunishment(new Punishment(targetData.getId(), Punishment.PunishType.BAN, System.currentTimeMillis(), time, playerData.getId(), reason));
        UtilPlayer.getAll(Rank.HELPER).forEach(staff -> staff.sendMessage(F.message("Punishments", C.RED + player.getName() + " temporarily banned " + targetData.getName() + " for " + Time.toString(finalTime) + " for " + finalReason + ".")));

        Player target = Bukkit.getPlayer(targetData.getUuid());
        if (target != null) {
            target.getPlayer().kickPlayer(F.message("Punishments", C.RED + "You were temporarily banned by " + player.getName() + " for " + Time.toString(time) + " for " + reason + ".\n"
                    + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
        }

        return null;
    }
}
