package studio.ecoprojects.ecozero.discordintergration.commands.minecraft;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.ecoprojects.ecozero.EcoZero;

public class DiscordDashboard implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (!player.isOp() && !player.hasPermission("discord.admin")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo permission!"));
            } else {
                player.openInventory(studio.ecoprojects.ecozero.discordintergration.listeners.minecraft.DiscordDashboard.CreateDashBoard());
            }
        } else {
            EcoZero.logger.info("This command can only be executed by a player!");
        }

        return true;
    }

}
