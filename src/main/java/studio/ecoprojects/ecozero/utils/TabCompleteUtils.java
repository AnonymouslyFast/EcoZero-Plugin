package studio.ecoprojects.ecozero.utils;

import java.util.ArrayList;
import java.util.List;

public class TabCompleteUtils {

    private static List<String> materialStrings = new ArrayList<>();


    private static final List<String> offlinePlayersNames = new ArrayList<>();

    public static void addOfflinePlayerName(String name) {
        offlinePlayersNames.add(name);
    }

    public static List<String> getOfflinePlayersNames() {
        return offlinePlayersNames;
    }

    public static List<String> getMaterialStrings() {
        return materialStrings;
    }
    public static void setMaterialStrings(List<String> materials) {
        materialStrings = materials;
    }

}
