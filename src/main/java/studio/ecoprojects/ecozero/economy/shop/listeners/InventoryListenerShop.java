// Inventory listener for main class Shop

package studio.ecoprojects.ecozero.economy.shop.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;


public class InventoryListenerShop implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Shop shop = Economy.getShop();
        if (e.getView().getTitle().equalsIgnoreCase(Colors.translateCodes("&2&lShop"))) {
            e.setCancelled(true);
            SubShop.getSubShops().forEach(subShop -> {
                if (e.getSlot() == subShop.getShopSlot()) {
                    if (subShop.getInventory(1) != null) {
                        e.getView().getPlayer().openInventory(subShop.getInventory(1));
                        Player player = (Player) e.getView().getPlayer();
                        player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f,1f);
                    } else {
                        e.getView().getPlayer().sendMessage(Colors.translateCodes("&cThis shop is currently down."));
                    }
                }
            });
        }
    }


}
