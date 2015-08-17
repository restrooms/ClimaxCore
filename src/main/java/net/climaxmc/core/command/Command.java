package net.climaxmc.core.command;

import net.climaxmc.core.mysql.Rank;
import org.bukkit.entity.Player;

/**
 * Represents a command
 */
public abstract class Command {
    protected String[] names;
    protected Rank rank;
    protected String usage;

    /**
     * Defines a command
     *
     * @param names Names of command (includes aliases)
     * @param rank  Rank of command
     */
    public Command(String[] names, Rank rank, String usage) {
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
    public abstract String execute(Player player, String[] args);

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
    public String getUsage() {
        return usage;
    }
}
