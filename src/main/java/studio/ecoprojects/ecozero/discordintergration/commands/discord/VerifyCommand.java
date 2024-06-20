package studio.ecoprojects.ecozero.discordintergration.commands.discord;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.PrivateChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class VerifyCommand extends ListenerAdapter {
    public static HashMap<UUID, Integer> VerifyCodes = new HashMap<>();


    private static void verify(Player player, User user) {
        VerifiedDB.addVerified(player.getUniqueId().toString(), user.getId());
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7=== &6&lVerification Alert &7 ===\n&f" + player.getName() + " &fhas verified their discord and got some rewards!\n&7(/verify)\n&7=== &6&lVerification Alert &7 ==="));
        Guild guild = BotEssentials.getGuild();
        guild.addRoleToMember(user, BotEssentials.getVerifiedRole()).complete();
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6Thanks for verifying!\n&7Rewards:\n  &8- &f30x Stone (TEMP)\n  &8- &2&l$&f1,500 (TEMP)"));
        String userID = null;
        if (VerifiedDB.getUserID(player.getUniqueId().toString()).isPresent()) userID = VerifiedDB.getUserID(player.getUniqueId().toString()).get();
        DirectMessageUser(user, ":white_check_mark: You've been verified please make sure this information is right:\n\n**Discord ID:** `" + userID + "` (user: <@" + userID + ">) \n**Minecraft username:** `" + player.getName() + "`");
    }

    private static void DirectMessageUser(User user, String message) {
        PrivateChannel dms = user.openPrivateChannel().complete();
        dms.sendMessage(message).complete();
    }

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getChannel() == BotEssentials.getDiscordVerificationChannel()) {
            if (event.getMessage().getContentRaw().startsWith("!verify")) {
                if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
                    String msg = event.getMessage().getContentRaw();
                    event.getMessage().delete().complete();
                    msg = msg.replace("!verify", "");
                    String[] args = msg.split(" ");
                    if (args.length >= 3) {
                        String code = args[1];
                        String username = args[2];
                        Player player = Bukkit.getPlayerExact(username);
                        if (Bukkit.getPlayerExact(username) != null) {
                            if (player != null && VerifiedDB.getUserID(player.getUniqueId().toString()).isEmpty()) {
                                if (VerifyCodes.containsKey(player.getUniqueId())) {
                                    if (code.equals(VerifyCodes.get(player.getUniqueId()).toString())) {
                                        verify(player, event.getMember().getUser());
                                        VerifyCodes.remove(player.getUniqueId());
                                    }
                                } else {
                                    DirectMessageUser(event.getMember().getUser(), ":x: **That code is not the correct code!**");
                                }
                            }
                        } else {
                            DirectMessageUser(event.getMember().getUser(), ":x: **That minecraft username does not exist/haven't played before!**");
                        }
                    } else {
                        DirectMessageUser(event.getMember().getUser(), ":x: Please use the correct command! `!verify <code> <username>` Exclude the <>");
                    }
                }
            } else {
                event.getMessage().delete().complete();
            }
        }

    }
}
