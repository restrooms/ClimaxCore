package net.climaxmc.core.command.commands;

import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.Command;
import net.climaxmc.core.mysql.*;
import net.climaxmc.core.utilities.C;
import net.climaxmc.core.utilities.F;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendsCommand extends Command {
    public FriendsCommand() {
        super(new String[] {"friends", "friend", "f"}, Rank.DEFAULT, F.message("Friends", "/f [player]"));
    }

    @Override
    public TextComponent execute(Player player, String[] args) {
        if (args.length > 1) {
            return usage;
        }

        if (args.length == 1) {
            PlayerData targetData = ClimaxCore.getPlayerData(args[0]);
            if (targetData == null) {
                return F.message("Friends", "That player has never joined!");
            }
            ClimaxCore.getMySQL().executeUpdate(DataQueries.ADD_FRIEND, ClimaxCore.getPlayerData(player).getId(), targetData.getId());
            return F.message("Friends", "You have added " + targetData + " as a friend.");
        }

        String friendsString = F.topLine() + "\n";
        ResultSet friendIdsSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_FRIENDS, ClimaxCore.getPlayerData(ClimaxCore.getPlayerData(player).getId()));

        try {
            while (friendIdsSet != null && friendIdsSet.next()) {
                PlayerData friendData = ClimaxCore.getPlayerData(friendIdsSet.getInt("friendid"));
                if (friendData.getServerID() == null) {
                    friendsString += C.GRAY + C.BOLD + friendData.getName() + C.GRAY + " \u00bb Currently Offline\n";
                } else {
                    ResultSet serversSet = ClimaxCore.getMySQL().executeQuery(DataQueries.GET_SERVER_FROM_ID, friendData.getServerID());
                    if (serversSet != null && serversSet.next()) {
                        String serverName = serversSet.getString("game") + "-" + serversSet.getInt("serverid");
                        friendsString += C.GRAY + C.BOLD + friendData.getName() + C.GRAY + " \u00bb " + C.GOLD + C.BOLD + serverName + "\n";
                    }
                }
            }
        } catch (SQLException e) {
            return F.message("Friends", "There was an error during retrieval of your friends.");
        }

        friendsString += F.bottomLine();
        return friendsString;
    }
}
