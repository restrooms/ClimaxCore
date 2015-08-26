package net.climaxmc.core.command;

import net.climaxmc.core.mysql.Rank;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

/**
 * Represents a command
 */
public abstract class Command {
    protected String[] names;
    protected Rank rank;
    protected TextComponent usage;

    /**
     * Defines a command
     *
     * @param names Names of command (includes aliases)
     * @param rank  Rank of command
     */
    public Command(String[] names, Rank rank, TextComponent usage) {
        this.names = names;
        this.rank = rank;
        this.usage = usage;
    }

    /**
     * Executes the command
     *
     * @param player Player that executed command
     * @param args   Arguments of command
     * @return Result of execution
     */
    public abstract TextComponent execute(Player player, String[] args);

    /**
     * Get the command names
     *
     * @return Command names
     */
    public String[] getNames() {
        return names;
    }

    /**
     * Get the command rank
     *
     * @return Command rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Gets the command usage
     *
     * @return Command usage
     */
    public TextComponent getUsage() {
        return usage;
    }
}
