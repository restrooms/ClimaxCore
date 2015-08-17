package net.climaxmc.core.events;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

@Getter
public class PlayerBalanceChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private OfflinePlayer player;
    private int amount;

    /**
     * Defines a change of player balance
     *
     * @param uuid UUID of player whose balance changed
     * @param amount Amount of balance changed
     */
    public PlayerBalanceChangeEvent(UUID uuid, int amount) {
        this.player = Bukkit.getOfflinePlayer(uuid);
        this.amount = amount;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
