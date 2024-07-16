// This is for ShopItems

package studio.ecoprojects.ecozero.economy.shop.listeners;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.utils.Colors;

import java.util.Objects;

public class InventoryListenerProducts implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains(Colors.translateCodes("&2&lShop"))) {
            ItemStack clickedItem = e.getCurrentItem();
            Player player = (Player) e.getView().getPlayer();
            NBT.get(Objects.requireNonNull(clickedItem), nbt -> {
                Product product = Economy.getShop().getProduct(nbt.getUUID("ItemUUID"));
                if (product != null) {
                    String prefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
                    // Buying
                    if (e.getClick().isLeftClick()) {
                        Inventory buyInventory = product.getBuyInventory();
                        if (buyInventory == null) {
                            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f,1f);
                            player.sendMessage(Colors.translateCodes(prefix + " &cBuying this item is currently disabled."));
                        } else {
                            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f,1f);
                            player.openInventory(buyInventory);
                        }

                    // Selling
                    } else if (e.getClick().isRightClick()) {
                        Inventory sellInventory = product.getSellInventory();
                        if (sellInventory == null) {
                            player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f,1f);
                            player.sendMessage(Colors.translateCodes(prefix + " &cSelling this item is currently disabled."));
                        } else {
                            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f,1f);
                            player.openInventory(sellInventory);
                        }
                    }
                }
            });
        }
    }

}
