package studio.ecoprojects.ecozero.economy.shop.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import studio.ecoprojects.ecozero.economy.Economy;

public class ShopInventoryListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getPlayer().getOpenInventory().getTopInventory().equals(Economy.getShop().getShopInventory())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getSlot() == Economy.getShop().getShopInventory().getSize()-5) {
                p.playSound(p, Sound.ITEM_BOOK_PUT, 1f, 1f);
                p.closeInventory();
            } else {
                Economy.getShop().getSubShops().iterator().forEachRemaining(subShop -> {
                    if (e.getSlot() == subShop.getSlot()) {
                        p.playSound(p, Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
                        p.openInventory(subShop.getInventory(1));
                    }
                });
            }
        }
    }

}
