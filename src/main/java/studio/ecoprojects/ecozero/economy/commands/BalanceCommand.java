package studio.ecoprojects.ecozero.economy.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.ecoprojects.ecozero.economy.BalanceFormatter;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.RandomUtils;

import java.text.NumberFormat;
import java.util.List;


public class BalanceCommand implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            OfflinePlayer target = null;
            if (strings.length == 1) {
                target = Bukkit.getOfflinePlayer(strings[0]);
                if (!player.hasPlayedBefore()) {
                    player.sendMessage(Colors.translateCodes("&cThat player does not exist"));
                    return true;
                }
            }
            if (target == null) target = player;
            if (Economy.getBalance(player.getUniqueId()) != null) {
                String balanace = NumberFormat.getNumberInstance().format(Economy.getBalance(target.getUniqueId()));
                player.sendMessage(Colors.translateCodes("&2&l" + target.getName() + "'s balance is: $&f" + balanace));
            } else {
                player.sendMessage(Colors.translateCodes("&cThat player does not have an Account"));
            }
        }

        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            return RandomUtils.getOfflinePlayersNames();
        }

        return null;
    }
}
