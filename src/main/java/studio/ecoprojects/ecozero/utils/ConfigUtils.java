package studio.ecoprojects.ecozero.utils;

import studio.ecoprojects.ecozero.EcoZero;

public class ConfigUtils {


    public static String getServerIP() {
        return EcoZero.getPlugin().getConfig().getString("server-ip");
    }

    public static String getServerName() {
        return EcoZero.getPlugin().getConfig().getString("server-name");
    }

    public static void ReloadConfigFiles() {
        EcoZero.getPlugin().saveConfig();
        EcoZero.getPlugin().reloadConfig();
    }

}
