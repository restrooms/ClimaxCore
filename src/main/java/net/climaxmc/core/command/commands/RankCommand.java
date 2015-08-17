package net.climaxmc.core.command.commands;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.PlayerData;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.F;
import org.bukkit.entity.Player;

public class RankCommand extends Command {
    public RankCommand() {
        super(new String[]{"rank"}, Rank.OWNER, F.message("Administration", "/rank <player> <rank>"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length != 2) {
            return usage;
        }

        PlayerData targetData = ClimaxCore.getPlayerData(args[0]);

        if (targetData == null) {
            return F.message("Administration", "That player has never joined!");
        }

        Rank rank = Rank.fromString(args[1]);

        if (rank == null) {
            return F.message("Administration", "That is not a valid rank!");
        }

        targetData.setRank(rank);

        return F.message("Administration", targetData.getName() + "'s rank has been set to " + rank.toString() + "!");
    }
}
