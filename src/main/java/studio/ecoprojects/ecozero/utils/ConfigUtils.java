package studio.ecoprojects.ecozero.utils;

import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;

public class ConfigUtils {


//    public static String getServerIP() {
//        return EcoZero.getPlugin().getConfig().getString("server-ip");
//    }

    public static String getServerName() {
        return EcoZero.getPlugin().getConfig().getString("server-name");
    }

    public static void reloadConfigFiles() {
        EcoZero.getPlugin().saveResource("config.yml", false);
        EcoZero.getPlugin().reloadConfig();
        DataBaseSetUp.setUsername(EcoZero.getPlugin().getConfig().getString("db-username"));
        DataBaseSetUp.setPassword(EcoZero.getPlugin().getConfig().getString("db-password"));
        DataBaseSetUp.setUrl(EcoZero.getPlugin().getConfig().getString("db-url"));
        BotEssentials.setToken(EcoZero.getPlugin().getConfig().getString("bot-token"));
        BotEssentials.setMinecraftChannelID(EcoZero.getPlugin().getConfig().getString("minecraft-to-discord-channel-id"));
        BotEssentials.setMinecraftLogID(EcoZero.getPlugin().getConfig().getString("minecraft-logs-channel-id"));
        BotEssentials.setDiscordVerificationID(EcoZero.getPlugin().getConfig().getString("discord-verification-channel-id"));
        BotEssentials.setVerifiedRoleID(EcoZero.getPlugin().getConfig().getString("discord-verified-role-id"));
        BotEssentials.setGuildID(EcoZero.getPlugin().getConfig().getString("guild-id"));
    }

}
