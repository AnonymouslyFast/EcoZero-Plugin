package studio.ecoprojects.ecozero.utils;

import java.util.ArrayList;
import java.util.List;

public class RandomUtils {

    private static final List<String> offlinePlayersNames = new ArrayList<>();

    public static void addOfflinePlayerName(String name) {
        offlinePlayersNames.add(name);
    }

    public static List<String> getOfflinePlayersNames() {
        return offlinePlayersNames;
    }

}
