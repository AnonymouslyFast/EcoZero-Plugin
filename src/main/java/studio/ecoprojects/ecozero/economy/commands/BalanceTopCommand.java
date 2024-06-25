package studio.ecoprojects.ecozero.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.BalanceFormatter;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;
import studio.ecoprojects.ecozero.utils.Colors;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.SortedMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class BalanceTopCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length != 0) {
            return false;
        } else {
            String topBalances = getBalances();
            commandSender.sendMessage(topBalances);
        }

        return true;
    }


    private String getBalances() {
        String economyPrefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
        StringBuilder value = new StringBuilder(Colors.translateCodes("&7====" + economyPrefix + "&7===="));
        SortedMap<Double, UUID> sortedBalances = EconomyDB.getAccountsSortedLowToHigh();
        sortedBalances.values().removeAll(EcoZero.getOperatorUUIDS());
        int count = sortedBalances.size();
        for (int i = 1; i <= 10; i++)  {
            count--;
            if (sortedBalances.size() < i || count < 0) {
                if (i == 10) {
                    value.append(Colors.translateCodes("\n  &b" + i + ") &7No one :("));
                } else {
                    value.append(Colors.translateCodes("\n  &b0" + i + ") &7No one :("));
                }
            } else {
                OfflinePlayer player = Bukkit.getOfflinePlayer(sortedBalances.values().stream().toList().get(count));
                if (player.hasPlayedBefore()) {
                    String balance = new BalanceFormatter().formatNumber(sortedBalances.keySet().stream().toList().get(count));
                    if (i == 10) {
                        value.append(Colors.translateCodes("\n  &b" + i + ") &7" + player.getName() + ": &2&l$&f" + balance));
                    } else {
                        value.append(Colors.translateCodes("\n  &b0" + i + ") &7" + player.getName() + ": &2&l$&f" + balance));
                    }
                }
            }
        }

        value.append(Colors.translateCodes("\n&aUpdates every " + EcoZero.getPlugin().getConfig().getInt("economy-saves-accounts-every") + " minutes."));
        
        return value.toString();
    }

}
