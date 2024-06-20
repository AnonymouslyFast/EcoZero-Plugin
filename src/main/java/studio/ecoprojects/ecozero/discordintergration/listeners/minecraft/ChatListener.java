package studio.ecoprojects.ecozero.discordintergration.listeners.minecraft;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;
import studio.ecoprojects.ecozero.utils.ConfigUtils;

import java.util.Objects;
import java.util.Optional;

public class ChatListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(final AsyncPlayerChatEvent event) {
        if (DiscordDashboard.isRemoving.contains(event.getPlayer().getUniqueId())) {
            event.setCancelled(true);
            if (event.getMessage().equalsIgnoreCase("cancel")) {
                DiscordDashboard.isRemoving.remove(event.getPlayer().getUniqueId());
                event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&lCanceled"));
                Bukkit.getScheduler().scheduleSyncDelayedTask(EcoZero.getPlugin(), () -> event.getPlayer().openInventory(DiscordDashboard.CreateDashBoard()), 1L);
            } else {
                Player player = Bukkit.getPlayerExact(event.getMessage());
                if (player != null && player.hasPlayedBefore()) {
                    Optional<String> userID = VerifiedDB.getUserID(player.getUniqueId().toString());
                    if (userID.isPresent()) {
                        DiscordDashboard.isRemoving.remove(event.getPlayer().getUniqueId());
                        User discordUser = BotEssentials.jda.getUserById(userID.get());
                        if (player.isOnline()) {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lVerified &8» &fYour account has been unverified. Please contact a admin if this is a issue."));
                        } else {
                            PrivateChannel DM = Objects.requireNonNull(discordUser).openPrivateChannel().complete();
                            DM.sendMessage("Your discord to minecraft connection on " + ConfigUtils.getServerName() + " has been removed. Please contact admin if you think this is a mistake.").queue();
                        }

                        VerifiedDB.removeVerifiedByUUID(player.getUniqueId().toString());
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lVerification: &fremoved &c" + player.getDisplayName() + " &ffrom verified."));
                    } else {
                        event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not verified!"));
                    }
                } else {
                    event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player does not exist/hasn't played before!"));
                }
            }
        }

        if (!event.isCancelled()) {
            String message = event.getMessage();
            if (!message.contains("@everyone") || !message.contains("@here")) {
                net.luckperms.api.model.user.User user = EcoZero.luckperms.getPlayerAdapter(Player.class).getUser(event.getPlayer());
                String prefix = user.getCachedData().getMetaData().getPrefix();
                String newprefix;
                TextChannel channel = BotEssentials.getMinecraftChannel();
                if (event.getPlayer().isOp()) {
                    if (!message.startsWith("!")) {
                        if (prefix == null) {
                            prefix = "Default";
                        }

                        newprefix = ChatColor.translateAlternateColorCodes('&', prefix);
                        newprefix = ChatColor.stripColor(newprefix);

                        channel.sendMessage("**" + newprefix + " " + event.getPlayer().getName() + " »** " + event.getMessage()).queue();
                    }
                } else {
                    if (prefix == null) {
                        prefix = "Default";
                    }

                    newprefix = ChatColor.translateAlternateColorCodes('&', prefix);
                    newprefix = ChatColor.stripColor(newprefix);

                    channel.sendMessage("**" + newprefix + " " + event.getPlayer().getName() + " »** " + event.getMessage()).queue();
                }
            }
        }

    }
}
