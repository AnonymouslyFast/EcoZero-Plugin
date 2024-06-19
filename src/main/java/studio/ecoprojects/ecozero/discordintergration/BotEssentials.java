package studio.ecoprojects.ecozero.discordintergration;

import java.awt.Color;
import java.time.Instant;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.bukkit.Bukkit;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.commands.discord.VerifyCommand;
import studio.ecoprojects.ecozero.discordintergration.listeners.discord.DiscordListeners;
import studio.ecoprojects.ecozero.utils.ConfigUtils;

public class BotEssentials {
    public static String Token;
    public static String MinecraftChannelID;
    public static String MinecraftLogID;
    public static String DiscordVerificationID;
    public static String VerifiedRoleID;
    public static String guildID;
    public static JDA jda;


    public static void startBot() {
        try {
            jda = JDABuilder.createDefault(Token).enableIntents(GatewayIntent.MESSAGE_CONTENT, new GatewayIntent[]{GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_MESSAGES}).setAutoReconnect(true).build().awaitReady();
            EcoZero.logger.info("Bot is ON");
        } catch (InterruptedException var1) {
            InterruptedException e = var1;
            throw new RuntimeException(e);
        }

        jda.addEventListener(new DiscordListeners());
        jda.addEventListener(new VerifyCommand());
        String serverName = ConfigUtils.getServerName();
        jda.getPresence().setActivity(Activity.playing(serverName + " (" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers() + ")"));
        EmbedBuilder embed = (new EmbedBuilder()).setColor(Color.green).setDescription(":white_check_mark: **Server Started!**").setTimestamp(Instant.now());
        jda.getTextChannelById(MinecraftChannelID).sendMessageEmbeds(embed.build()).complete();
    }

    public static void stopBot() {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(Color.red)
                .setDescription(":x: **Server Stopped!**")
                .setTimestamp(Instant.now());
        jda.getTextChannelById(MinecraftChannelID).sendMessageEmbeds(embed.build()).queue();
        jda.shutdown();
        EcoZero.logger.info("Bot is OFF");
    }
}
