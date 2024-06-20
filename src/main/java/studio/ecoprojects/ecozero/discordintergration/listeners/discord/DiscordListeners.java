package studio.ecoprojects.ecozero.discordintergration.listeners.discord;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.EcoZero;

public class DiscordListeners extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        Member member;
        if (event.getChannel() == BotEssentials.getMinecraftChannel()) {
            member = event.getMember();
            if (!member.getUser().isBot()) {
                Message message = event.getMessage().getReferencedMessage();
                String msg;
                if (message != null) {
                    msg = event.getMessage().getContentDisplay();
                    String author = message.getAuthor().getName();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[&7Discord&b] &f" + member.getEffectiveName() + " (Replying to: " + author + ")&8: &f" + msg));
                } else {
                    msg = event.getMessage().getContentDisplay();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&b[&7Discord&b] &f" + member.getEffectiveName() + "&8: &f" + msg));
                }
            }
        } else if (event.getChannel() == BotEssentials.getMinecraftLogChannel()) {
            member = event.getMember();
            if (!member.getUser().isBot()) {
                String command = event.getMessage().getContentDisplay();
                CommandSender console = Bukkit.getConsoleSender();
                event.getMessage().reply(":white_check_mark: Executed `" + command + "`").complete();
                Bukkit.getScheduler().callSyncMethod(EcoZero.getPlugin(), () -> Bukkit.dispatchCommand(console, command));
            }
        }

    }
}
