package studio.ecoprojects.ecozero.discordintergration.commands.minecraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.commands.discord.VerifyCommand;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;

import java.util.Random;

public class Verify implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player player = (Player) commandSender;
            if (VerifiedDB.getUserID(player.getUniqueId().toString()).isEmpty()) {
                if (!VerifyCommand.VerifyCodes.containsKey(player.getUniqueId())) {
                    Random random = new Random();
                    int code = random.nextInt(20000, 50000);
                    VerifyCommand.VerifyCodes.put(player.getUniqueId(), code);
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your code is: &f" + VerifyCommand.VerifyCodes.get(player.getUniqueId()) + "&7. This will expire in 1 minute."));
                    Bukkit.getScheduler().scheduleSyncDelayedTask(EcoZero.getPlugin(), () -> {
                        if (VerifiedDB.getUserID(player.getUniqueId().toString()).isEmpty()) {
                            VerifyCommand.VerifyCodes.remove(player.getUniqueId());
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lYour Verification code has expired."));
                        }

                    }, 1200L);
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your code is: &f" + VerifyCommand.VerifyCodes.get(player.getUniqueId()) + "&7."));
                }
            } else {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYour account is already verified! &7Contact a administrator or developer to fix if this is a issue."));
            }
        }

        return true;
    }

}
