package net.climaxmc.core.mysql;

import lombok.Data;

import java.net.InetSocketAddress;

@Data
public class ServerInfo {
    private final String name;
    private final GameType gameType;
    private final InetSocketAddress address;
    private final int playerAmount;
    private final String gameState;
}
