package studio.ecoprojects.ecozero;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.slf4j.LoggerFactory;
import studio.ecoprojects.ecozero.economy.SLAPI;
import studio.ecoprojects.ecozero.economy.commands.BalanceCommand;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.discordintergration.commands.minecraft.Verify;
import studio.ecoprojects.ecozero.economy.commands.EconomyCommand;
import studio.ecoprojects.ecozero.utils.DataBaseSetUp;
import studio.ecoprojects.ecozero.utils.EcoZeroCommand;
import studio.ecoprojects.ecozero.utils.EventUtils;
import studio.ecoprojects.ecozero.utils.RandomUtils;

public final class EcoZero extends JavaPlugin {
    public static Logger logger;
    public static LuckPerms luckperms;
    public static EcoZero plugin;

    public void onEnable() {
        plugin = this;
        logger = this.getLogger();
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckperms = provider.getProvider();
            }

            // Config
            this.reloadConfig();
            this.saveDefaultConfig();
            DataBaseSetUp.username = getConfig().getString("db-username");
            DataBaseSetUp.password = getConfig().getString("db-password");
            DataBaseSetUp.url = getConfig().getString("db-url");
            BotEssentials.Token = getConfig().getString("bot-token");
            BotEssentials.MinecraftChannelID = getConfig().getString("minecraft-to-discord-channel-id");
            BotEssentials.MinecraftLogID = getConfig().getString("minecraft-logs-channel-id");
            BotEssentials.DiscordVerificationID = getConfig().getString("discord-verification-channel-id");
            BotEssentials.VerifiedRoleID = getConfig().getString("discord-verified-role-id");
            BotEssentials.guildID = getConfig().getString("guild-id");
            if (BotEssentials.Token == null) { logger.severe("Please Provide the bot Token so I can log into it."); }
            if (BotEssentials.MinecraftChannelID == null) { logger.severe("Please Provide the minecraft-to-discord-channel-id so I send messages in it."); }
            if (BotEssentials.MinecraftLogID == null) { logger.severe("Please Provide the minecraft-logs-channel-id so I send messages in it."); }
            if (BotEssentials.DiscordVerificationID == null) { logger.severe("Please Provide the discord-verification-channel-id so I can access the channel."); }
            if (BotEssentials.VerifiedRoleID == null) { logger.severe("Please Provide the discord-verified-role-ID so I can access the role."); }
            if (BotEssentials.guildID == null) { logger.severe("Please Provide the guild-id so I can access the guild."); }
            // End of Config

            BotEssentials.startBot();
            DataBaseSetUp.login();

            // Registering Minecraft Listeners
            EventUtils.registerMinecraftListeners(EcoZero.getPlugin().getClass().getPackageName() + ".discordintergration.listeners.minecraft");
            EventUtils.registerMinecraftListeners(EcoZero.getPlugin().getClass().getPackageName() + ".economy.listeners");

            // Registering Discord Listeners
            EventUtils.registerDiscordListeners(EcoZero.getPlugin().getClass().getPackageName() + ".discordintergration.listeners.discord");

            // Commands
            getCommand("verify").setExecutor(new Verify());
            getCommand("discorddashboard").setExecutor(new studio.ecoprojects.ecozero.discordintergration.commands.minecraft.DiscordDashboard());

            getCommand("balance").setExecutor(new BalanceCommand());
            getCommand("balance").setTabCompleter(new BalanceCommand());

            getCommand("economy").setExecutor(new EconomyCommand());
            getCommand("economy").setTabCompleter(new EconomyCommand());

            getCommand("ecozero").setExecutor(new EcoZeroCommand());
            getCommand("ecozero").setTabCompleter(new EcoZeroCommand());

            // Random
            if (RandomUtils.getOfflinePlayersNames().isEmpty()) {
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    RandomUtils.addOfflinePlayerName(player.getName());
                }
            }

            // Gets the minute number from config and calculates it to Minecraft ticks
            long delay = 20L * (60L * getConfig().getInt("economy-saves-accounts-every"));
            // Saves every account every x (delay) minutes
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

                @Override
                public void run() {
                    SLAPI.saveAccounts();
                }
            }, 0L, delay);

        } else {
            getLogger().severe("LUCKPERMS IS NEEDED FOR THIS PLUGIN: Please download Luckperms since it's needed");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    public static EcoZero getPlugin() {
        return plugin;
    }

    public void onDisable() {
        SLAPI.saveAccounts();
        BotEssentials.stopBot();
    }
}

