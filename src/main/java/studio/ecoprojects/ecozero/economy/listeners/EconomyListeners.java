package studio.ecoprojects.ecozero.economy.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.SLAPI;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;
import studio.ecoprojects.ecozero.utils.TabCompleteUtils;


public class EconomyListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore()) TabCompleteUtils.addOfflinePlayerName(e.getPlayer().getName());
        if (!EcoZero.getEconomy().isCached(e.getPlayer().getUniqueId())) {
            if (EconomyDB.getBalance(e.getPlayer().getUniqueId().toString()).isPresent()) {
                SLAPI.loadAccount(e.getPlayer());
            } else {
                EcoZero.getEconomy().createAccount(e.getPlayer().getUniqueId());
            }
        }
    }

}
