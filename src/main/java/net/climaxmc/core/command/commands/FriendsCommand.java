package net.climaxmc.core.command.commands;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.C;
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
        if (args.length > 1) {
            return usage;
        }

        if (args.length == 1) {
            PlayerData targetData = ClimaxCore.getPlayerData(args[0]);
            if (targetData == null) {
                return F.message("Friends", "That player has never joined!");
            }
            ClimaxCore.getMySQL().executeUpdate(DataQueries.ADD_FRIEND, ClimaxCore.getPlayerData(player).getId(), targetData.getId());
            return F.message("Friends", "You have added " + targetData.getName() + " as a friend.");
        }

        player.sendMessage(F.topLine());
        ResultSet friendIdsSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_FRIENDS, ClimaxCore.getPlayerData(player).getId());

        try {
            while (friendIdsSet != null && friendIdsSet.next()) {
                PlayerData friendData = ClimaxCore.getPlayerData(friendIdsSet.getInt("friendid"));
                if (friendData.getServerID() == null) {
                    player.sendMessage(C.GRAY + C.BOLD + friendData.getName() + C.GRAY + " \u00bb Currently Offline\n");
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
                                        .append(" \u00bb ").bold(false)
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
