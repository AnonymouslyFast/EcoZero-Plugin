// This is the listeners for the Ores SubShop

package studio.ecoprojects.ecozero.economy.shop.listeners;


import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.utils.Colors;

import java.util.Objects;

public class InventoryListenerOres implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Shop shop = Economy.getShop();
        Player player = (Player) e.getView().getPlayer();
        String title = Colors.translateCodes(shop.getOresSubShop().getShopInventoryName());
        if (shop.getOresSubShop().shopPageContainsPlayer(player) && shop.getOresSubShop().getShopPagePlayerIsOn(player) > 1) {
            title = Colors.translateCodes(shop.getOresSubShop().getShopInventoryName() + " &7(" + shop.getOresSubShop().getShopPagePlayerIsOn(player) + ")");
        }
        if (e.getView().getTitle().equalsIgnoreCase(title)) {
            e.setCancelled(true);
            // pages
            if (e.getSlot() == e.getInventory().getSize()-9) {      // back page & back to shop
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f,1f);
                if (shop.getOresSubShop().shopPageContainsPlayer(player) && shop.getOresSubShop().getShopPagePlayerIsOn(player) != 1) {     // back page
                    int currentPage = shop.getOresSubShop().getShopPagePlayerIsOn(player);
                    shop.getOresSubShop().removePlayerFromPage(player);
                    shop.getOresSubShop().addPlayerToPage(player, currentPage-1);
                    player.openInventory(shop.getOresSubShop().getInventory(currentPage-1));
                } else {    // Back to shop
                    if (shop.getOresSubShop().shopPageContainsPlayer(player)) shop.getOresSubShop().removePlayerFromPage(player);
                    player.openInventory(shop.getShopInventory());
                }
            } else if (e.getSlot() == e.getInventory().getSize()-1 && Objects.requireNonNull(e.getCurrentItem()).getType() == Material.ARROW) {     // Next page
                player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f,1f);
                int currentPage = 1;
                if (shop.getOresSubShop().shopPageContainsPlayer(player)) currentPage = shop.getOresSubShop().getShopPagePlayerIsOn(player);
                shop.getOresSubShop().removePlayerFromPage(player);
                shop.getOresSubShop().addPlayerToPage(player, currentPage+1);
                player.openInventory(shop.getOresSubShop().getInventory(currentPage+1));
            } else if (e.getSlot() == e.getInventory().getSize()-5) {   // Close GUI
                player.playSound(player, Sound.ITEM_BOOK_PUT, 1f,1f);
                if (shop.getOresSubShop().shopPageContainsPlayer(player)) shop.getOresSubShop().removePlayerFromPage(player);
                player.closeInventory();
                e.setCancelled(false);
            }
        }
    }

}
