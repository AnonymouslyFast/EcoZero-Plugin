// This is the listeners for the Ores SubShop

package studio.ecoprojects.ecozero.economy.shop.listeners;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.utils.Colors;

public class InventoryListenerOres implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Shop shop = Economy.getShop();
        if (e.getView().getTitle().equalsIgnoreCase(Colors.translateCodes(shop.getOresSubShop().getShopInventoryName()))) {
            e.setCancelled(true);
        }
    }

}
