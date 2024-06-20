package studio.ecoprojects.ecozero.economy.listeners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.utils.RandomUtils;

import java.util.Random;

public class EconomyListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) RandomUtils.addOfflinePlayerName(e.getPlayer().getName());
        if (!Economy.hasAccount(e.getPlayer().getUniqueId())) {
            Economy.createAccount(e.getPlayer().getUniqueId());
        }
    }

}
