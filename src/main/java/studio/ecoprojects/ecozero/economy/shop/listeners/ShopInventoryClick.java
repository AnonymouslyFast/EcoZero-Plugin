package studio.ecoprojects.ecozero.economy.shop.listeners;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.economy.shop.SubShopPage;

import java.util.Objects;

public class ShopInventoryClick implements Listener {

    private Shop shop;

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (shop == null) shop = EcoZero.getEconomy().getShop();
        if (e.getView().getTopInventory().equals(shop.getShopInventory().getInventory())) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getSlot() == shop.getShopInventory().getInventory().getSize()-5) {
                p.closeInventory();
                p.playSound(p, Sound.ITEM_BOOK_PUT, 1f, 1f);
                return;
            }
            ItemStack item = e.getCurrentItem();
            NBT.get(Objects.requireNonNull(item), nbt -> {
                String subShopName = nbt.getString("SubShopName");
                if (subShopName == null) return;
                SubShop subShop = shop.getSubShop(subShopName);
                SubShopPage page = subShop.getPageFromCache(1);
                if (page == null) return;
                p.openInventory(page.getSubShopInventory().getInventory());
                p.playSound(p, Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            });
        }
    }

}
