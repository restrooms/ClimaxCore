package net.climaxmc.core.command.commands;

import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.F;
import net.climaxmc.core.utilities.UtilPlayer;
import org.bukkit.entity.Player;

public class HubCommand extends Command {
    public HubCommand() {
        super(new String[]{"hub", "lobby"}, Rank.OWNER, F.message("Hub", "/hub"));
    }

    @Override
    public String execute(Player player, String[] args) {
        UtilPlayer.sendToServer(player, "Hub");
        return null;
    }
}
