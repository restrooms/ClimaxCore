package net.climaxmc.core.logger;

import com.google.common.io.*;
import net.climaxmc.core.ClimaxCore;
import net.climaxmc.core.mysql.DataQueries;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class ChatLogger implements Listener, PluginMessageListener {
    private String serverName;

    public ChatLogger(Plugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", this);

        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("GetServer");
        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ClimaxCore.getMySQL().executeUpdate(DataQueries.CREATE_CHAT_LOG, event.getMessage(), ClimaxCore.getPlayerData(player).getId(), serverName);
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("GetServer")) {
            serverName = in.readUTF();
        }
    }
}
