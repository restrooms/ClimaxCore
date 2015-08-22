package net.climaxmc.core.command.commands;

import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.F;
import org.bukkit.entity.Player;

public class MeCommand extends Command {
    public MeCommand() {
        super(new String[]{"me", "minecraft:me"}, Rank.OWNER, F.message("You", "Really? You think you're so great, don't you?"));
    }

    @Override
    public String execute(Player player, String[] args) {
        return usage;
    }
}
