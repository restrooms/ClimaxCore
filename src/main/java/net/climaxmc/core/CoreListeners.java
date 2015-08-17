package net.climaxmc.core;

import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.*;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CoreListeners implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        PlayerData playerData = ClimaxCore.getPlayerData(event.getUniqueId());

        if (playerData == null) {
            ClimaxCore.getMySQL().createPlayerData(event.getUniqueId(), event.getName(), event.getAddress().getHostAddress());
            playerData = ClimaxCore.getPlayerData(event.getUniqueId());
        }

        if (!playerData.getIp().equals(event.getAddress().getHostAddress())) {
            playerData.setIP(event.getAddress().getHostAddress());
        }

        Set<UUID> ipMatchingPlayers = new HashSet<>();
        try {
            ResultSet uuids = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_PLAYER_UUID_FROM_IP, event.getAddress().getHostAddress());
            while (uuids != null && uuids.next()) {
                ipMatchingPlayers.add(UUID.fromString(uuids.getString("uuid")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Set<PlayerData> matchingData = new HashSet<>();
        ipMatchingPlayers.stream().filter(uuid -> ClimaxCore.getPlayerData(uuid).getPunishments().stream().anyMatch(punishment -> punishment.getType().equals(Punishment.PunishType.BAN))).forEach(uuid -> matchingData.add(ClimaxCore.getPlayerData(uuid)));
        if (playerData.getPunishments().stream().anyMatch(punishment -> punishment.getType().equals(Punishment.PunishType.BAN))) {
            matchingData.add(playerData);
        }
        matchingData.forEach(data -> data.getPunishments().forEach(punishment -> {
            PlayerData punisherData = ClimaxCore.getPlayerData(punishment.getPunisherID());
            if (System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration())) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, F.message("Punishments", C.RED + "You were temporarily banned by " + punisherData.getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "You have " + Time.toString(punishment.getTime() + punishment.getExpiration() - System.currentTimeMillis()) + " left in your ban.\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
            } else if (punishment.getExpiration() == -1) {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, F.message("Punishments", C.RED + "You were permanently banned by " + punisherData.getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
            }
        }));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        ClimaxCore.getPlayerData(player).setServerID(ClimaxCore.getServerID());
        ClimaxCore.getPlayerOnTimes().put(player.getUniqueId(), System.currentTimeMillis());
        ClimaxCore.getMySQL().updateServerPlayers(UtilPlayer.getAll().size(), ClimaxCore.getServerID());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = ClimaxCore.getPlayerData(player);
        if (ClimaxCore.getPlayerOnTimes().containsKey(player.getUniqueId())) {
            playerData.setPlayTime(playerData.getPlayTime() + (System.currentTimeMillis() - ClimaxCore.getPlayerOnTimes().get(player.getUniqueId())));
        }
        playerData.setServerID(null);
        ClimaxCore.clearCache(playerData);
        ClimaxCore.getMySQL().updateServerPlayers(UtilPlayer.getAll().size() - 1, ClimaxCore.getServerID());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = ClimaxCore.getPlayerData(player);
        if (playerData.hasRank(Rank.NINJA)) {
            event.setFormat(C.DARK_GRAY + C.BOLD + "{" + playerData.getRank().getPrefix() + C.DARK_GRAY + C.BOLD + "} " + C.GRAY + "%s" + C.RESET + ": %s");
        } else {
            event.setFormat(C.GRAY + "%s" + C.RESET + ": %s");
        }

        playerData.getPunishments().stream().filter(punishment -> punishment.getType().equals(Punishment.PunishType.MUTE)).forEach(punishment -> {
            PlayerData punisherData = ClimaxCore.getPlayerData(punishment.getPunisherID());
            if (System.currentTimeMillis() <= (punishment.getTime() + punishment.getExpiration())) {
                event.setCancelled(true);
                player.sendMessage(F.message("Punishments", C.RED + "You were temporarily muted by " + punisherData.getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "You have " + Time.toString(punishment.getTime() + punishment.getExpiration() - System.currentTimeMillis()) + " left in your mute.\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
            } else if (punishment.getExpiration() == -1) {
                event.setCancelled(true);
                player.sendMessage(F.message("Punishments", C.RED + "You were permanently muted by " + punisherData.getName()
                        + " for " + punishment.getReason() + ".\n"
                        + "Appeal on forum.climaxmc.net if you believe that this is in error!"));
            }
        });
    }
}
