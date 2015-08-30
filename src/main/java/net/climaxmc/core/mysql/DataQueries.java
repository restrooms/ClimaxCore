package net.climaxmc.core.mysql;

public class DataQueries {
    public static final String CREATE_PLAYERDATA = "INSERT IGNORE INTO `players` (`uuid`, `name`, `ip`) VALUES (?, ?, ?);";
    public static final String GET_PLAYERDATA_ID = "SELECT * FROM `players` WHERE `playerid` = ?;";
    public static final String GET_PLAYERDATA_UUID = "SELECT * FROM `players` WHERE `uuid` = ?;";
    public static final String GET_PLAYERDATA_NAME = "SELECT * FROM `players` WHERE `name` = ?;";
    public static final String GET_PLAYER_UUID_FROM_IP = "SELECT `uuid` FROM `players` WHERE `ip` = ?;";

    public static final String CREATE_PUNISHMENT = "INSERT IGNORE INTO `punishments` (`playerid`, `type`, `time`, `expiration`, `punisherid`, `reason`) VALUES (?, ?, ?, ?, ?, ?);";
    public static final String GET_PUNISHMENTS = "SELECT * FROM `punishments` WHERE `playerid` = ?;";
    public static final String UPDATE_PUNISHMENT_TIME = "UPDATE `punishments` SET `expiration` = ? WHERE `playerid` = ? AND `type` = ? AND `time` = ?;";

    public static final String PURCHASE_KIT = "INSERT IGNORE INTO `kitpurchases` (`playerid`, `gameid`, `kitname`) VALUES (?, ?, ?);";
    public static final String GET_PURCHASED_KITS = "SELECT * FROM `kitpurchases` WHERE `playerid` = ?;";

    public static final String CREATE_SERVER = "INSERT IGNORE INTO `servers` (`globalid`, `gameid`, `serverid`, `ip`, `port`, `players`) VALUES (" +
            "(SELECT `a`.`globalid` + 1 FROM (SELECT 0 AS `globalid` UNION SELECT `globalid` FROM `servers` ORDER BY `globalid`) AS `a` LEFT JOIN `servers` AS `b` ON `b`.`globalid` = `a`.`globalid` + 1 WHERE `b`.`globalid` IS NULL LIMIT 1), ?, " +
            "(SELECT `a`.`serverid` + 1 FROM (SELECT 0 AS `serverid` UNION SELECT `serverid` FROM `servers` ORDER BY `serverid`) AS `a` LEFT JOIN `servers` AS `b` ON `b`.`serverid` = `a`.`serverid` + 1 WHERE `b`.`serverid` IS NULL LIMIT 1), ?, ?, ?);";
    public static final String GET_SERVER_ID = "SELECT `globalid` FROM `servers` WHERE `ip` = ? AND `port` = ?;";
    public static final String GET_SERVERS = "SELECT * FROM `servers` `server` LEFT JOIN `games` `game` ON `server`.`gameid` = `game`.`gameid`;";
    public static final String GET_SERVER_FROM_ID = "SELECT * FROM `servers` `server` LEFT JOIN `games` `game` ON `server`.`gameid` = `game`.`gameid` WHERE `globalid` = ?;";
    public static final String UPDATE_PLAYERS_ONLINE = "UPDATE `servers` SET `players` = ? WHERE `globalid` = ?;";
    public static final String DELETE_SERVER = "DELETE FROM `servers` WHERE `ip` = ? AND `port` = ?;";

    public static final String ADD_FRIEND = "INSERT IGNORE INTO `friends` (`playerid`, `friendid`) VALUES (?, ?);";
    public static final String DELETE_FRIEND = "DELETE FROM `friends` WHERE `playerid` = ? AND `friendid` = ?;";
    public static final String GET_FRIENDS = "SELECT `friendid` FROM `friends` WHERE `playerid` = ?;";

    public static final String CREATE_CHAT_LOG = "INSERT IGNORE INTO `chatlogs` (`message`, `playerid`, `servername`) VALUES (?, ?, ?);";

    public static final String GET_RANDOM_PLAYER_ID = "SELECT `id` FROM `players` ORDER BY RAND() WHERE `serverid` IS NOT NULL AND `playerid` IS NOT ? LIMIT 1;";
}
