package net.climaxmc.core.mysql;

import lombok.Getter;
import net.climaxmc.core.utilities.C;
import org.apache.commons.lang.WordUtils;

/**
 * Represents a rank
 */
@Getter
public enum Rank {
    OWNER(C.RED + C.BOLD + "Owner", Integer.MAX_VALUE),
    DEVELOPER(C.GOLD + C.BOLD + "Developer", 120),
    ADMINISTRATOR(C.RED + C.BOLD + "Admin", 100),
    MODERATOR(C.DARK_PURPLE + C.BOLD + "Mod", 80),
    HEAD_BUILDER(C.YELLOW + C.BOLD + "HeadBuilder", 70),
    BUILDER(C.AQUA + C.BOLD + "Builder", 60),
    JR_DEVELOPER(C.GOLD + C.BOLD + "JrDev", 50),
    HELPER(C.GREEN + C.BOLD + "Helper", 40),
    TWITCH(C.DARK_PURPLE + C.BOLD + "Twi" + C.WHITE + C.BOLD + "tch", 30),
    YOUTUBE(C.RED + C.BOLD + "You" + C.WHITE + C.BOLD + "Tube", 30),
    TRUSTED(C.DARK_AQUA + C.BOLD + "Trusted", 20),
    MASTER(C.DARK_RED + C.BOLD + "Master", 3),
    TITAN(C.GOLD + C.BOLD + "Titan", 2),
    NINJA(C.AQUA + C.BOLD + "Ninja", 1),
    DEFAULT("", 0);

    private String prefix;
    private int permissionLevel;

    /**
     * Defines a rank
     *
     * @param prefix          Prefix of the rank
     * @param permissionLevel Permission level of the rank
     */
    Rank(String prefix, int permissionLevel) {
        this.prefix = prefix;
        this.permissionLevel = permissionLevel;
    }

    @Override
    public String toString() {
        return WordUtils.capitalizeFully(name());
    }

    public static Rank fromString(String rankName) {
        for (Rank rank : values()) {
            if (rank.toString().equalsIgnoreCase(rankName)) {
                return rank;
            }
        }

        return null;
    }
}
