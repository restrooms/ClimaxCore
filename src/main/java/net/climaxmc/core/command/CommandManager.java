package net.climaxmc.core.command;

import com.google.common.collect.Sets;
import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.command.commands.*;
import net.climaxmc.core.command.commands.messaging.MessageCommand;
import net.climaxmc.core.command.commands.messaging.ReplyCommand;
import net.climaxmc.core.command.commands.punishments.*;
import net.climaxmc.core.mysql.PlayerData;
import net.climaxmc.core.utilities.F;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.Set;

public class CommandManager implements Listener {
    private Set<Command> commands;

    public CommandManager() {
        ClimaxCore.getPlugin().getServer().getPluginManager().registerEvents(this, ClimaxCore.getPlugin());
        initializeCommands();
    }

    /**
     * Adds commands to the set of commands
     *
     * @param commands Commands to add
     */
    public void addCommands(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    private void initializeCommands() {
        commands = Sets.newHashSet(
                new RankCommand(),
                new HubCommand(),
                new MeCommand(),
                new FriendsCommand(),
                new BanCommand(),
                new TempBanCommand(),
                new MuteCommand(),
                new TempMuteCommand(),
                new KickCommand(),
                new WarnCommand(),
                new UnBanCommand(),
                new UnMuteCommand(),
                new MessageCommand(),
                new ReplyCommand(),
                new PeiCommand()
        );
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String[] args = new String[0];
        if (message.contains(" ")) {
            args = StringUtils.substringAfter(message, " ").split(" ");
            message = message.split(" ")[0];
        }
        for (Command possibleCommand : commands) {
            for (String name : possibleCommand.getNames()) {
                if (message.equalsIgnoreCase("/" + name)) {
                    event.setCancelled(true);

                    Player player = event.getPlayer();
                    PlayerData playerData = ClimaxCore.getPlayerData(player);

                    if (playerData.hasRank(possibleCommand.getRank())) {
                        String result = possibleCommand.execute(player, args);

                        if (result != null) {
                            player.sendMessage(result);
                        }
                    } else {
                        player.sendMessage(F.denyPermissions(possibleCommand.getRank()));
                    }
                }
            }
        }
    }
}
