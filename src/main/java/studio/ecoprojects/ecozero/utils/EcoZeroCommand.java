package studio.ecoprojects.ecozero.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class EcoZeroCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!commandSender.hasPermission("ecozero")) {
            commandSender.sendMessage(Colors.translateCodes("&cNo permissions!"));
        } else {
            if (strings.length == 0) {
                commandSender.sendMessage(helpMessage());
            } else {
                if (strings[0].equalsIgnoreCase("reload")) {
                    ConfigUtils.reloadConfigFiles();
                    commandSender.sendMessage(Colors.translateCodes("&aSuccessfully reloaded config files."));
                } else {
                    commandSender.sendMessage(helpMessage());
                }
            }
        }

        return true;
    }

    private String helpMessage() {
        return Colors.translateCodes("&fWelcome to EcoZero!!\n  /ecozero reload - reloads config");
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 1) {
            List<String> args = new ArrayList<>();
            args.add("help");
            args.add("reload");
            return args;
        }

        return new ArrayList<>();
    }
}
