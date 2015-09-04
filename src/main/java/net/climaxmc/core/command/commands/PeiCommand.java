package net.climaxmc.core.command.commands;

import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.Rank;
import net.climaxmc.core.utilities.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PeiCommand extends Command {
    public PeiCommand() {
        super(new String[]{"pei"}, Rank.DEFAULT, F.message("Pei", "You have received a Pei!"));
    }

    @Override
    public String execute(Player player, String[] args) {
        player.getInventory().addItem(new I(Material.PUMPKIN_PIE).name(C.GOLD + "Pei"));
        return usage;
    }
}
