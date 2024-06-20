package studio.ecoprojects.ecozero.utils;

import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;

public class ConfigUtils {


    public static String getServerIP() {
        return EcoZero.getPlugin().getConfig().getString("server-ip");
    }

    public static String getServerName() {
        return EcoZero.getPlugin().getConfig().getString("server-name");
    }

    public static void reloadConfigFiles() {
        EcoZero.getPlugin().saveResource("config.yml", false);
        EcoZero.getPlugin().reloadConfig();
        DataBaseSetUp.username = EcoZero.getPlugin().getConfig().getString("db-username");
        DataBaseSetUp.password = EcoZero.getPlugin().getConfig().getString("db-password");
        DataBaseSetUp.url = EcoZero.getPlugin().getConfig().getString("db-url");
        BotEssentials.Token = EcoZero.getPlugin().getConfig().getString("bot-token");
        BotEssentials.MinecraftChannelID = EcoZero.getPlugin().getConfig().getString("minecraft-to-discord-channel-id");
        BotEssentials.MinecraftLogID = EcoZero.getPlugin().getConfig().getString("minecraft-logs-channel-id");
        BotEssentials.DiscordVerificationID = EcoZero.getPlugin().getConfig().getString("discord-verification-channel-id");
        BotEssentials.VerifiedRoleID = EcoZero.getPlugin().getConfig().getString("discord-verified-role-id");
        BotEssentials.guildID = EcoZero.getPlugin().getConfig().getString("guild-id");
        if (BotEssentials.Token == null) { EcoZero.logger.severe("Please Provide the bot Token so I can log into it."); }
        if (BotEssentials.MinecraftChannelID == null) { EcoZero.logger.severe("Please Provide the minecraft-to-discord-channel-id so I send messages in it."); }
        if (BotEssentials.MinecraftLogID == null) { EcoZero.logger.severe("Please Provide the minecraft-logs-channel-id so I send messages in it."); }
        if (BotEssentials.DiscordVerificationID == null) { EcoZero.logger.severe("Please Provide the discord-verification-channel-id so I can access the channel."); }
        if (BotEssentials.VerifiedRoleID == null) { EcoZero.logger.severe("Please Provide the discord-verified-role-ID so I can access the role."); }
        if (BotEssentials.guildID == null) { EcoZero.logger.severe("Please Provide the guild-id so I can access the guild."); }
    }

}
