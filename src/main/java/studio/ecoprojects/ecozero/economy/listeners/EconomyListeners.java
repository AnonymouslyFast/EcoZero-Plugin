package studio.ecoprojects.ecozero.economy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.SLAPI;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;
import studio.ecoprojects.ecozero.utils.RandomUtils;


public class EconomyListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) RandomUtils.addOfflinePlayerName(e.getPlayer().getName());
        if (!Economy.isCached(e.getPlayer().getUniqueId())) {
            if (EconomyDB.getBalance(e.getPlayer().getUniqueId().toString()).isPresent()) {
                SLAPI.loadAccount(e.getPlayer());
            } else {
                Economy.createAccount(e.getPlayer().getUniqueId());
            }
        }
    }

}
