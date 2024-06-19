package studio.ecoprojects.ecozero.utils;

import org.bukkit.ChatColor;

public class Colors {

    public static String translateCodes(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
