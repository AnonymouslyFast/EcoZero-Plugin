package studio.ecoprojects.ecozero.discordintergration;

import java.awt.Color;
import java.time.Instant;
import java.util.Objects;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.commands.discord.VerifyCommand;
import studio.ecoprojects.ecozero.discordintergration.listeners.discord.DiscordListeners;
import studio.ecoprojects.ecozero.utils.ConfigUtils;

public class BotEssentials {
    private static String Token;
    private static String MinecraftChannelID;
    private static String MinecraftLogID;
    private static String DiscordVerificationID;
    private static String VerifiedRoleID;
    private static String guildID;
    public static JDA jda;


    public static void startBot() {
        try {
            jda = JDABuilder.createDefault(Token).enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES).setAutoReconnect(true).build().awaitReady();
            EcoZero.logger.info("Bot is ON");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        jda.addEventListener(new DiscordListeners());
        jda.addEventListener(new VerifyCommand());
        String serverName = ConfigUtils.getServerName();
        jda.getPresence().setActivity(Activity.playing(serverName + " (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));
        EmbedBuilder embed = (new EmbedBuilder()).setColor(Color.green).setDescription(":white_check_mark: **Server Started!**").setTimestamp(Instant.now());
        Objects.requireNonNull(jda.getTextChannelById(MinecraftChannelID)).sendMessageEmbeds(embed.build()).complete();
    }

    public static void stopBot() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.red)
                .setDescription(":x: **Server Stopped!**")
                .setTimestamp(Instant.now());
        Objects.requireNonNull(jda.getTextChannelById(MinecraftChannelID)).sendMessageEmbeds(embed.build()).queue();
        jda.shutdown();
        EcoZero.logger.info("Bot is OFF");
    }

    // setters
    public static void setToken(String token) {
        if (token == null || token.isEmpty()) {
            EcoZero.logger.severe("Please Provide the bot Token so I can log into it.");
        } else {
            Token = token;
        }
    }

    public static void setMinecraftChannelID(String minecraftChannelID) {
        if (minecraftChannelID == null || minecraftChannelID.isEmpty()) {
            EcoZero.logger.severe("Please Provide the minecraft-to-discord-channel-id so I send messages in it.");
        } else {
            MinecraftChannelID = minecraftChannelID;
        }

    }

    public static void setMinecraftLogID(String minecraftLogID) {
        if (minecraftLogID == null || minecraftLogID.isEmpty()) {
            EcoZero.logger.severe("Please Provide the minecraft-logs-channel-id so I send messages in it.");
        } else {
            MinecraftLogID = minecraftLogID;
        }
    }

    public static void setDiscordVerificationID(String discordVerificationID) {
        if (discordVerificationID == null || discordVerificationID.isEmpty()) {
            EcoZero.logger.severe("Please Provide the discord-verification-channel-id so I can access the channel.");
        } else {
            DiscordVerificationID = discordVerificationID;
        }
    }

    public static void setVerifiedRoleID(String verifiedRoleID) {
        if (verifiedRoleID == null || verifiedRoleID.isEmpty()) {
            EcoZero.logger.severe("Please Provide the discord-verified-role-ID so I can access the role.");
        } else {
            VerifiedRoleID = verifiedRoleID;
        }
    }

    public static void setGuildID(String guildiD) {
        if (guildID == null || guildID.isEmpty()) {
            EcoZero.logger.severe("Please Provide the guild-id so I can access the guild.");
        } else {
            guildID = guildiD;
        }
    }

    // getters
    public static Guild getGuild() {
        return jda.getGuildById(guildID);
    }

    public static TextChannel getMinecraftChannel() {
        return jda.getTextChannelById(MinecraftChannelID);
    }

    public static TextChannel getDiscordVerificationChannel() {
        return jda.getTextChannelById(DiscordVerificationID);
    }

    public static TextChannel getMinecraftLogChannel() {
        return jda.getTextChannelById(MinecraftLogID);
    }

    public static Role getVerifiedRole() {
        return jda.getRoleById(VerifiedRoleID);
    }
}
