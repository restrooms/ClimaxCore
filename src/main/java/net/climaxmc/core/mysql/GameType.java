package net.climaxmc.core.mysql;

import lombok.*;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public enum GameType {
    HUB(0, "Hub", "HUB"),
    PAINTBALL(1, "Paintball", "PB");

    private final int id;
    private final String name;
    private final String abbreviation;
    @Setter
    private boolean enabled;

    public static GameType fromID(int id) {
        for (GameType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }

        return null;
    }

    public static GameType fromAbbreviation(String abbreviation) {
        for (GameType type : values()) {
            if (type.getAbbreviation().equals(abbreviation)) {
                return type;
            }
        }

        return null;
    }
}
