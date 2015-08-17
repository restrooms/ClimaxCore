package net.climaxmc.core.command.commands.punishments;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class UnBanCommand extends Command {
    public UnBanCommand() {
        super(new String[] {"unban"}, Rank.MODERATOR, F.message("Punishments", "/unban <player>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length != 1) {
            return usage;
        }

        PlayerData targetData = ClimaxCore.getPlayerData(args[0]);

        if (targetData == null) {
            return F.message("Punishments", "That player has never joined!");
        }

        if (targetData.getPunishments() != null && targetData.getPunishments().size() != 0) {
            Set<Punishment> remove = new HashSet<>();
            targetData.getPunishments().stream()
                    .filter(punishment -> punishment.getExpiration() == -1 || System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration()))
                    .filter(punishment -> punishment.getType().equals(Punishment.PunishType.BAN))
                    .forEach(remove::add);
            if (remove.size() == 0) {
                return F.message("Punishments", "That player is not banned!");
            }
            remove.forEach(targetData::removePunishment);
            UtilPlayer.getAll(Rank.HELPER).forEach(staff -> staff.sendMessage(F.message("Punishments", C.RED + player.getName() + " unbanned " + targetData.getName() + ".")));
        } else {
            return F.message("Punishments", "That player is not banned!");
        }

        return null;
    }
}
