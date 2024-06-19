package studio.ecoprojects.ecozero.discordintergration.listeners.minecraft;

import net.dv8tion.jda.api.utils.TimeFormat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;

public class CommandsListener implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String time = TimeFormat.TIME_LONG.now().toString();
        String message = time + "** | " + player.getName() + "** `" + event.getMessage() + "`";
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftLogID).sendMessage(message).complete();
    }

    @EventHandler
    public void onConsoleCommand(ServerCommandEvent event) {
        String time = TimeFormat.TIME_LONG.now().toString();
        String message = time + "** | CONSOLE ** `/" + event.getCommand() + "`";
        BotEssentials.jda.getTextChannelById(BotEssentials.MinecraftLogID).sendMessage(message).complete();
    }

}
