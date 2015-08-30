package net.climaxmc.core.command.commands;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.F;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendsCommand extends Command {
    public FriendsCommand() {
        super(new String[] {"friends", "friend", "f"}, Rank.DEFAULT, F.message("Friends", "/f [player]"));
    }

    @Override
    public String execute(Player player, String[] args) {
        if (args.length > 2) {
            return usage;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase(player.getName())) {
                ResultSet randomFriendSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_RANDOM_PLAYER_ID, ClimaxCore.getPlayerData(player));
                try {
                    if (randomFriendSet != null && randomFriendSet.next()) {
                        int id = randomFriendSet.getInt("playerid");
                        ClimaxCore.getMySQL().executeUpdate(DataQueries.ADD_FRIEND, ClimaxCore.getPlayerData(player).getId(), id);
                        return F.message("Friends", "Are you feeling lonely today? Here, we've added " + ClimaxCore.getPlayerData(id).getName() + " to your friends list. Go play with them now!");
                    }
                } catch (SQLException e) {
                    ClimaxCore.getPlugin().getLogger().severe("Could not execute MySQL query! " + e.getMessage());
                }
                return F.message("Friends", "You cannot add yourself as a friend.");
            }

            PlayerData targetData = ClimaxCore.getPlayerData(args[0]);
            if (targetData == null) {
                return F.message("Friends", "That player has never joined!");
            }
            ClimaxCore.getMySQL().executeUpdate(DataQueries.ADD_FRIEND, ClimaxCore.getPlayerData(player).getId(), targetData.getId());
            return F.message("Friends", "You have added " + targetData.getName() + " as a friend.");
        } else if (args.length == 2 && args[0].equalsIgnoreCase("delete")) {
            PlayerData targetData = ClimaxCore.getPlayerData(args[1]);
            if (targetData == null) {
                return F.message("Friends", "That player has never joined!");
            }
            ClimaxCore.getMySQL().executeUpdate(DataQueries.DELETE_FRIEND, ClimaxCore.getPlayerData(player).getId(), targetData.getId());
            return F.message("Friends", "You have deleted " + targetData.getName() + " from your friends.");
        }

        player.sendMessage(F.topLine());
        ResultSet friendIdsSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_FRIENDS, ClimaxCore.getPlayerData(player).getId());

        try {
            while (friendIdsSet != null && friendIdsSet.next()) {
                PlayerData friendData = ClimaxCore.getPlayerData(friendIdsSet.getInt("friendid"));
                if (friendData.getServerID() == null) {
                    player.spigot().sendMessage(
                            new ComponentBuilder(friendData.getName()).color(ChatColor.GRAY).bold(true)
                                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f delete " + friendData.getName()))
                                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to unfriend " + friendData.getName()).color(ChatColor.GRAY).create()))
                                    .append(" \u00bb Currently Offline").bold(false)
                                    .event((ClickEvent) null)
                                    .event((HoverEvent) null)
                                    .create());
                } else {
                    ResultSet serversSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_SERVER_FROM_ID, friendData.getServerID());
                    if (serversSet != null && serversSet.next()) {
                        String serverName;
                        if (serversSet.getString("shortname").equals("HUB")) {
                            serverName = "Hub";
                        } else if (serversSet.getString("shortname").equals("KitPvp")) {
                            serverName = "KitPvp";
                        } else {
                            serverName = serversSet.getString("shortname") + "-" + serversSet.getInt("serverid");
                        }
                        player.spigot().sendMessage(
                                new ComponentBuilder(friendData.getName()).color(ChatColor.GRAY).bold(true)
                                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/f delete " + friendData.getName()))
                                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to unfriend " + friendData.getName()).color(ChatColor.GRAY).create()))
                                        .append(" \u00bb ").bold(false)
                                        .event((ClickEvent) null)
                                        .event((HoverEvent) null)
                                        .append(serverName).color(ChatColor.GOLD)
                                        .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/server " + serverName))
                                        .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click to join server " + serverName).color(ChatColor.GOLD).create()))
                                        .create());
                    }
                }
            }
        } catch (SQLException e) {
            ClimaxCore.getPlugin().getLogger().severe("Could not execute MySQL query! " + e.getMessage());
            return F.message("Friends", "There was an error during retrieval of your friends.");
        }

        return F.bottomLine();
    }
}
