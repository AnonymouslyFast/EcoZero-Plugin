package studio.ecoprojects.ecozero.economy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import studio.ecoprojects.ecozero.economy.Economy;

public class EconomyListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!Economy.hasAccount(e.getPlayer().getUniqueId())) {
            Economy.createAccount(e.getPlayer().getUniqueId());
        }
    }

}
