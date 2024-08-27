package studio.ecoprojects.ecozero.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.utils.Colors;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PayCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player player) {
            String prefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
            if (prefix == null) prefix = "";

            if (strings.length != 2) {
                player.sendMessage(getHelpMessage());
            } else {
                Player target = Bukkit.getPlayerExact(strings[0]);
                try {
                    Double amount = Double.parseDouble(strings[1]);
                    if (target == null || !target.hasPlayedBefore() || !target.isOnline()) {
                        player.sendMessage(getHelpMessage());
                    } else {
                        if (amount <= 0) {
                            player.sendMessage(getHelpMessage());
                        } else {
                            if (EcoZero.getEconomy().getBalance(player.getUniqueId()) < amount) {
                                player.sendMessage(Colors.translateCodes(prefix + " &fYou cannot afford this!"));
                            } else {
                                String formattedAmount = NumberFormat.getNumberInstance().format(amount);
                                player.sendMessage(Colors.translateCodes(prefix + " &fSuccessfully paid " + target.getName() + " &2&l$&f" + formattedAmount + "."));
                                target.sendMessage(Colors.translateCodes(prefix + " &fYou were paid &2&l$&f" + formattedAmount + " by " + player.getName() + "."));

                                Double targetBalance = EcoZero.getEconomy().getBalance(target.getUniqueId()) + amount;
                                Double playerBalance = EcoZero.getEconomy().getBalance(player.getUniqueId()) - amount;

                                EcoZero.getEconomy().setBalance(player.getUniqueId(), playerBalance);
                                EcoZero.getEconomy().setBalance(target.getUniqueId(), targetBalance);
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(getHelpMessage());
                }
            }
        } else {
            String prefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
            if (prefix == null) prefix = "";
            commandSender.sendMessage(Colors.translateCodes(prefix + " &cCannot execute this command through console!"));
        }

        return true;
    }

    private String getHelpMessage() {
        return Colors.translateCodes("""
                &7=== &2&lPay Help &7===\

                  &8&l- &f/pay <online player> <number (can't be less than 0)> &8&l- &7gives money to the specified player and removes it from your balance""");
    }

    private final List<String> argument2 = Arrays.asList("1", "8", "16", "32", "64", "128");

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            return null;
        } else if (strings.length == 2) {
            return argument2;
        }

        return new ArrayList<>();
    }
}
