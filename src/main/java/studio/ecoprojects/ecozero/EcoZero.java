package studio.ecoprojects.ecozero;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import studio.ecoprojects.ecozero.economy.SLAPI;
import studio.ecoprojects.ecozero.economy.VaultSetUp;
import studio.ecoprojects.ecozero.economy.commands.BalanceCommand;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.discordintergration.commands.minecraft.Verify;
import studio.ecoprojects.ecozero.economy.commands.BalanceTopCommand;
import studio.ecoprojects.ecozero.economy.commands.EconomyCommand;
import studio.ecoprojects.ecozero.economy.commands.PayCommand;
import studio.ecoprojects.ecozero.economy.shop.commands.ShopCommand;
import studio.ecoprojects.ecozero.utils.DataBaseSetUp;
import studio.ecoprojects.ecozero.utils.EcoZeroCommand;
import studio.ecoprojects.ecozero.utils.EventUtils;
import studio.ecoprojects.ecozero.utils.RandomUtils;

public final class EcoZero extends JavaPlugin {
    public static Logger logger;
    public static LuckPerms luckperms;
    private static EcoZero plugin;
    private static final List<UUID> operatorsUUIDS = new ArrayList<>();

    public void onEnable() {
        plugin = this;
        logger = this.getLogger();
        if (Bukkit.getPluginManager().isPluginEnabled("LuckPerms")) {
            RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
            if (provider != null) {
                luckperms = provider.getProvider();
            }

            this.reloadConfig();
            this.saveDefaultConfig();

            setUpDiscordBot();

            setUpDataBase();

            registerListeners();

            registerCommands();

            reloadOperatorUUIDS();

            // Setting up Vault Economy
            getServer().getServicesManager().register(Economy.class, new VaultSetUp(), this, ServicePriority.High);

            // Sets offline players on start.
            if (RandomUtils.getOfflinePlayersNames().isEmpty()) {
                for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                    RandomUtils.addOfflinePlayerName(player.getName());
                }
            }

            // Gets the minute number from config and calculates it to Minecraft ticks
            long delay = 20L * (60L * getConfig().getInt("economy-saves-accounts-every"));
            // Saves every account every x (delay) minutes
            getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
                SLAPI.saveAccounts();
                reloadOperatorUUIDS();
                logger.info("Saved economy accounts to database");
            }, 0L, delay);

        } else {
            getLogger().severe("LUCKPERMS IS NEEDED FOR THIS PLUGIN: Please download Luckperms since it's needed");
            Bukkit.getPluginManager().disablePlugin(this);
        }

    }

    private void registerListeners() {
        // Registering Minecraft Listeners
        EventUtils.registerMinecraftListeners(EcoZero.getPlugin().getClass().getPackageName() + ".discordintergration.listeners.minecraft");
        EventUtils.registerMinecraftListeners(EcoZero.getPlugin().getClass().getPackageName() + ".economy.listeners");
        EventUtils.registerMinecraftListeners(EcoZero.getPlugin().getClass().getPackageName() + ".economy.shop.listeners");

        // Registering Discord Listeners
        EventUtils.registerDiscordListeners(EcoZero.getPlugin().getClass().getPackageName() + ".discordintergration.listeners.discord");
    }

    private void setUpDiscordBot() {
        BotEssentials.setToken(getConfig().getString("bot-token"));
        BotEssentials.setMinecraftChannelID(getConfig().getString("minecraft-to-discord-channel-id"));
        BotEssentials.setMinecraftLogID(getConfig().getString("minecraft-logs-channel-id"));
        BotEssentials.setDiscordVerificationID(getConfig().getString("discord-verification-channel-id"));
        BotEssentials.setVerifiedRoleID(getConfig().getString("discord-verified-role-id"));
        BotEssentials.setGuildID(getConfig().getString("guild-id"));

        BotEssentials.startBot();
    }

    private void setUpDataBase() {
        DataBaseSetUp.setUsername(EcoZero.getPlugin().getConfig().getString("db-username"));
        DataBaseSetUp.setPassword(EcoZero.getPlugin().getConfig().getString("db-password"));
        DataBaseSetUp.setUrl(EcoZero.getPlugin().getConfig().getString("db-url"));

        DataBaseSetUp.login();
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("verify")).setExecutor(new Verify());
        Objects.requireNonNull(getCommand("discorddashboard")).setExecutor(new studio.ecoprojects.ecozero.discordintergration.commands.minecraft.DiscordDashboard());
        Objects.requireNonNull(getCommand("balancetop")).setExecutor(new BalanceTopCommand());

        Objects.requireNonNull(getCommand("pay")).setExecutor(new PayCommand());
        Objects.requireNonNull(getCommand("pay")).setTabCompleter(new PayCommand());

        Objects.requireNonNull(getCommand("balance")).setExecutor(new BalanceCommand());
        Objects.requireNonNull(getCommand("balance")).setTabCompleter(new BalanceCommand());

        Objects.requireNonNull(getCommand("economy")).setExecutor(new EconomyCommand());
        Objects.requireNonNull(getCommand("economy")).setTabCompleter(new EconomyCommand());

        Objects.requireNonNull(getCommand("shop")).setExecutor(new ShopCommand());
        Objects.requireNonNull(getCommand("shop")).setTabCompleter(new ShopCommand());

        Objects.requireNonNull(getCommand("ecozero")).setExecutor(new EcoZeroCommand());
        Objects.requireNonNull(getCommand("ecozero")).setTabCompleter(new EcoZeroCommand());
    }

    public static EcoZero getPlugin() {
        return plugin;
    }

    private void reloadOperatorUUIDS() {
        for (OfflinePlayer player : Bukkit.getOperators()) {
            if (!operatorsUUIDS.contains(player.getUniqueId())) {
                operatorsUUIDS.add(player.getUniqueId());
            }
        }
    }

    public static List<UUID> getOperatorUUIDS() {
        return operatorsUUIDS;
    }

    public void onDisable() {
        SLAPI.saveAccounts();
        BotEssentials.stopBot();
    }
}

