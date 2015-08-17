package net.climaxmc.core.utilities;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.mysql.Rank;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.*;
import java.util.stream.Collectors;

public class UtilPlayer {
    /**
     * Gets all of the players online
     *
     * @return List of players
     */
    public static List<Player> getAll() {
        return getAll(true);
    }

    /**
     * Gets all of the players online and filters them by if they are spectating
     *
     * @return List of players
     */
    public static List<Player> getAll(boolean includeSpectators) {
        List<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(players::add);
        players.removeIf(player -> player.getGameMode().equals(GameMode.SPECTATOR) && !includeSpectators);
        return players;
    }

    /**
     * Gets all of the players online and filters them by rank
     *
     * @return List of players
     */
    public static List<Player> getAll(Rank rank) {
        return Bukkit.getOnlinePlayers().stream().filter(player -> ClimaxCore.getMySQL().getPlayerData(player).hasRank(rank)).collect(Collectors.<Player>toList());
    }

    /**
     * Gets all of the players online and randomizes them
     *
     * @return List of players
     */
    public static List<Player> getAllShuffled() {
        List<Player> players = getAll();
        Collections.shuffle(players);
        return players;
    }

    /**
     * Resets a player
     *
     * @param player Player to reset
     */
    public static void reset(Player player) {
        clearInventory(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));
        for (PotionEffect potion : player.getActivePotionEffects()) {
            player.removePotionEffect(potion.getType());
        }
        player.setMaxHealth(20);
        player.setHealth(20);
    }

    /**
     * Clears the inventory of a player
     *
     * @param player Player to clear inventory of
     */
    public static void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    /**
     * Sends an action bar message to the player
     *
     * @param player Player to send action bar to
     * @param message Message to send to player
     */
    public static void sendActionBar(Player player, String message) {
        ChatComponentText chatComponent = new ChatComponentText(message);
        PacketPlayOutChat packet = new PacketPlayOutChat(chatComponent, (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
