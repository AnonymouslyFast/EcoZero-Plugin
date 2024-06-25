package studio.ecoprojects.ecozero.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.SLAPI;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.RandomUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

public class EconomyCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!commandSender.hasPermission("economy.admin")) {
            commandSender.sendMessage(Colors.translateCodes("&cNo permissions!"));
        } else {
            String prefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
            if (prefix == null) prefix = "";

            // Command: /economy
            if (strings.length == 0) {
                commandSender.sendMessage(helpMessage());

                // Command: /economy help
            } else if (strings[0].equalsIgnoreCase("help")) {
                commandSender.sendMessage(helpMessage());

                // Command: /economy set
            } else if (strings[0].equalsIgnoreCase("set")) {
                if (strings.length != 3) {
                    commandSender.sendMessage("Usage: /economy set <player> <number>");
                } else {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(strings[1]);
                    try {
                        Double number =  Double.parseDouble(strings[2]);
                        if (!player.hasPlayedBefore() || Double.isNaN(number)) {
                            commandSender.sendMessage("Usage: /economy set <player> <number>");
                        } else {
                            Economy.setBalance(player.getUniqueId(), number);
                            if (player.isOnline()) Objects.requireNonNull(player.getPlayer())
                                    .sendMessage(Colors.translateCodes(prefix + " &fYour balance has been set to: &2&l$&f" + getFormattedBalance(Economy.getBalance(player.getUniqueId()))));
                            commandSender.sendMessage(
                                    Colors.translateCodes(prefix + " &fSuccessfully set " + player.getName() + "'s balance to: &2&l$&f" + getFormattedBalance(Economy.getBalance(player.getUniqueId()))));
                        }
                    } catch (NumberFormatException exception) {
                        commandSender.sendMessage("Usage: /economy set <player> <number>");
                    }
                }

                // Command: /economy remove
            } else if (strings[0].equalsIgnoreCase("remove")) {
                if (strings.length != 3) {
                    commandSender.sendMessage("Usage: /economy remove <player> <number> (too many arguments/too little arguments)");
                } else {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(strings[1]);
                    try {
                        double number = Double.parseDouble(strings[2]);
                        if (!player.hasPlayedBefore() || Double.isNaN(number)) {
                            commandSender.sendMessage("Usage: /economy remove <player> <number> (player doesnt exist)");
                        } else {
                            double currentBalance = Economy.getBalance(player.getUniqueId());
                            double balanceSubtracted = currentBalance - number;
                            if (!(balanceSubtracted < 0)) {
                                commandSender.sendMessage("Usage: /economy remove <player> <number> (Can't leave player with negative balance!)");
                            } else {
                                Economy.setBalance(player.getUniqueId(), balanceSubtracted);
                                if (player.isOnline()) Objects.requireNonNull(player.getPlayer())
                                        .sendMessage(Colors.translateCodes(prefix + " &2&l$&f" + getFormattedBalance(number) + " has been removed from your balance. &7(Current balance: &2&l$&f " + getFormattedBalance(Economy.getBalance(player.getUniqueId())) + "&7)"));
                                commandSender.sendMessage(
                                        Colors.translateCodes(prefix + " &fSuccessfully removed &2&l$&f" + getFormattedBalance(number) + " from " + player.getName() + "'s balance &7(their current balance: &2&l&f" + getFormattedBalance(Economy.getBalance(player.getUniqueId())) + "&7)"));
                            }
                        }
                    } catch (NumberFormatException exception) {
                        commandSender.sendMessage("Usage: /economy remove <player> <number>");
                    }
                }

                // Command: /economy add
            } else if (strings[0].equalsIgnoreCase("add")) {
                if (strings.length != 3) {
                    commandSender.sendMessage("Usage: /economy add <player> <number>");
                } else {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(strings[1]);
                    try {
                        double number = Double.parseDouble(strings[2]);
                        if (!player.hasPlayedBefore() || Double.isNaN(number)) {
                            commandSender.sendMessage("Usage: /economy add <player> <number>");
                        } else {
                            double currentBalance = Economy.getBalance(player.getUniqueId());
                            double balanceAdded = currentBalance + number;
                            Economy.setBalance(player.getUniqueId(), balanceAdded);
                            if (player.isOnline()) Objects.requireNonNull(player.getPlayer())
                                    .sendMessage(Colors.translateCodes(prefix + " &2&l$&f" + getFormattedBalance(number) + " has been added tp your balance. &7(Current balance: &2&l$&f " + getFormattedBalance(Economy.getBalance(player.getUniqueId())) + "&7)"));
                            commandSender.sendMessage(
                                    Colors.translateCodes(prefix + " &fSuccessfully added &2&l$&f" + getFormattedBalance(number) + " to " + player.getName() + "'s balance &7(their current balance: &2&l&f" + getFormattedBalance(Economy.getBalance(player.getUniqueId())) + "&7)"));
                        }
                    } catch (NumberFormatException exception) {
                        commandSender.sendMessage("Usage: /economy add <player> <number>");
                    }
                }

                // Command: /economy removeaccount
            } else if (strings[0].equalsIgnoreCase("removeaccount")) {
                if (strings.length != 2) {
                    commandSender.sendMessage("Usage: /economy removeaccount <player/*>");
                } else {
                    if (strings[1].equalsIgnoreCase("*")) {
                        for (UUID uuid : EconomyDB.getAccounts().keySet()) {
                            if (Economy.isCached(uuid)) {
                                Economy.removeAccount(uuid);
                            }
                        }
                        Bukkit.broadcastMessage(Colors.translateCodes(prefix + " &c&lAll accounts have been removed/purged, please re-log to get a default account."));
                    } else {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(strings[1]);
                        if (!player.hasPlayedBefore()) {
                            commandSender.sendMessage("Usage: /economy removeaccount <player/*>");
                        } else {
                            if (EconomyDB.getBalance(player.getUniqueId().toString()).isEmpty()) {
                                commandSender.sendMessage("That player does not have an account");
                            } else {
                                Economy.removeAccount(player.getUniqueId());
                                if (player.isOnline()) Objects.requireNonNull(player.getPlayer())
                                        .sendMessage(Colors.translateCodes(prefix + " &fYour Economy account has been removed/deleted. Re-login to get a default account."));
                                commandSender.sendMessage(
                                        Colors.translateCodes(prefix + " &fSuccessfully removed " + player.getName() + "'s Account"));
                            }
                        }
                    }
                }

                // Command: /economy createaccount
            } else if (strings[0].equalsIgnoreCase("createaccount")) {
                if (strings.length != 2) {
                    commandSender.sendMessage("Usage: /economy createaccount <player/*>");
                } else {
                    if (strings[1].equalsIgnoreCase("*")) {
                        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                            if (EconomyDB.getBalance(player.getUniqueId().toString()).isEmpty()) {
                                Economy.createAccount(player.getUniqueId());
                                if (player.isOnline()) Objects.requireNonNull(player.getPlayer()).sendMessage(Colors.translateCodes(prefix + " &fYou have been given a default account, please re-log to get a default account."));
                            }
                        }
                        Bukkit.broadcastMessage(Colors.translateCodes(prefix + " &a&lAll people who don't have an account now have default accounts"));
                    } else {
                        OfflinePlayer player = Bukkit.getOfflinePlayer(strings[1]);
                        if (!player.hasPlayedBefore()) {
                            commandSender.sendMessage("Usage: /economy createaccount <player/*>");
                        } else {
                            Economy.createAccount(player.getUniqueId());
                            if (player.isOnline()) Objects.requireNonNull(player.getPlayer())
                                    .sendMessage(Colors.translateCodes(prefix + " &fYou have been given a default account, please re-log to get a default account."));
                            commandSender.sendMessage(
                                    Colors.translateCodes(prefix + " &fSuccessfully created account for " + player.getName()));
                        }
                    }
                }

                // Command: /economy forcesaveaccounts
            } else if (strings[0].equalsIgnoreCase("forcesaveaccounts")) {
                if (strings.length > 1) {
                    commandSender.sendMessage("Usage: /economy forcesaveaccounts");
                } else {
                    commandSender.sendMessage(Colors.translateCodes(prefix + " &fSuccessfully saved all online accounts to the database."));
                    SLAPI.saveAccounts();
                }

                // Catch all, if anything slips up it just sends the help message.
            } else {
                commandSender.sendMessage(helpMessage());
            }
        }

        return true;
    }

    private String getFormattedBalance(Double balance) {
        return NumberFormat.getNumberInstance().format(balance);
    }

    // Help message to be sent to the command sender.
    private String helpMessage() {
        return Colors.translateCodes("""
                &7=== &2&lEconomy Help &7===\

                  &8&l- &f/economy help &8&l- &7brings you to this message\

                  &8&l- &f/economy set <player> <number> &8&l- &7sets the balance of the given player to the given amount\

                  &8&l- &f/economy remove <player> <number> &8&l- &7removes the given amount from the given player's balance\

                  &8&l- &f/economy add <player> <number> &8&l- &7adds the given amount to the given player's balance\

                  &8&l- &f/economy removeaccount <player/*> &8&l- &7removes the given players economy account (if put * it removes all accounts)\

                  &8&l- &f/economy createaccount <player/*> &8&l- &7creates an account for the given player (if put * it creates accounts for offline and online players who don't have one.)\
                  &8&l- &f/economy forcesaveaccounts &8&l- &7Saves all accounts to database""");

    }

    private final List<String> arguments1 = Arrays.asList("add", "remove", "set", "help", "removeaccount", "createaccount", "forcesaveaccounts");
    private final List<String> arguments3 = Arrays.asList("1", "8", "16", "32", "64", "128");



    // Tab completer for this command
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender.hasPermission("economy.admin")) {
            if (strings.length == 1) {
                return arguments1;
            } else if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("set") || strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("remove") ) {
                    return RandomUtils.getOfflinePlayersNames();
                } else if (strings[0].equalsIgnoreCase("createaccount") || strings[0].equalsIgnoreCase("removeaccount")) {
                    List<String> args = RandomUtils.getOfflinePlayersNames();
                    args.add("*");
                    return args;
                }
            } else if (strings.length == 3) {
                if (strings[0].equalsIgnoreCase("set") || strings[0].equalsIgnoreCase("add") || strings[0].equalsIgnoreCase("remove") ) {
                    return arguments3;
                }
            }
        }

        return new ArrayList<>();
    }




}


