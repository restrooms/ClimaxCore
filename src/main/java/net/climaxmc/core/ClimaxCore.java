package net.climaxmc.core;

import lombok.Getter;
import net.climaxmc.core.command.CommandManager;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.UtilPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class ClimaxCore {
    @Getter
    private static Plugin plugin;
    @Getter
    private static MySQL mySQL;
    private static Set<PlayerData> cachedPlayerData = new HashSet<>();
    @Getter
    private static Map<UUID, Long> playerOnTimes = new HashMap<>();
    @Getter
    private static int serverID;
    @Getter
    private static CommandManager commandManager;

    private ClimaxCore() {} // Everything is static!

    /**
     * Enables ClimaxCore
     *
     * @see Plugin#onEnable()
     * @param pluginInstance Host plugin
     */
    public static void onEnable(Plugin pluginInstance, GameType gameType) {
        plugin = pluginInstance;
        mySQL = new MySQL(plugin, "localhost", 3306, "climax", "plugin", "rR6nCbqaFTPCZqHA");
        commandManager = new CommandManager();
        plugin.getServer().getPluginManager().registerEvents(new CoreListeners(plugin), plugin);
        if (!gameType.equals(GameType.HUB)) {
            mySQL.createServer(gameType);
        }
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (gameType.equals(GameType.HUB)) {
                serverID = 0;
            } else {
                serverID = mySQL.getServerID();
            }
            UtilPlayer.getAll().forEach(player -> getPlayerData(player).setServerID(serverID));
        }, 2);
    }

    /**
     * Disables ClimaxCore
     *
     * @see Plugin#onDisable()
     */
    public static void onDisable() {
        UtilPlayer.getAll().forEach(player -> getPlayerData(player).setServerID(null));
        if (serverID != 0) {
            mySQL.deleteServer();
        }
        mySQL.closeConnection();
    }

    /**
     * Gets the data of a player
     *
     * @param player Player to get data of
     * @return Data of player
     */
    public static PlayerData getPlayerData(Player player) {
        return getPlayerData(player.getUniqueId());
    }

    /**
     * Gets the data of a player
     *
     * @param playerUUID UUID of the player to get data of
     * @return Data of player
     */
    public static PlayerData getPlayerData(UUID playerUUID) {
        for (PlayerData playerData : cachedPlayerData) {
            if (playerData != null && playerData.getUuid() != null && playerData.getUuid().equals(playerUUID)) {
                return playerData;
            }
        }
        PlayerData playerData = mySQL.getPlayerData(playerUUID);
        cachedPlayerData.add(playerData);
        return playerData;
    }

    /**
     * Gets the data of a player
     *
     * @param playerName Name of the player to get data of
     * @return Data of player
     */
    public static PlayerData getPlayerData(String playerName) {
        for (PlayerData playerData : cachedPlayerData) {
            if (playerData != null && playerData.getName() != null && playerData.getName().equalsIgnoreCase(playerName)) {
                return playerData;
            }
        }
        PlayerData playerData = mySQL.getPlayerData(playerName);
        cachedPlayerData.add(playerData);
        return playerData;
    }

    /**
     * Gets the data of a player
     *
     * @param playerID ID of the player to get data of
     * @return Data of player
     */
    public static PlayerData getPlayerData(int playerID) {
        for (PlayerData playerData : cachedPlayerData) {
            if (playerData != null && playerData.getId() == playerID) {
                return playerData;
            }
        }
        PlayerData playerData = mySQL.getPlayerData(playerID);
        cachedPlayerData.add(playerData);
        return playerData;
    }

    /**
     * Clears the data of a player
     *
     * @param playerData Player data to clear
     */
    public static void clearCache(PlayerData playerData) {
        cachedPlayerData.remove(playerData);
    }
}
