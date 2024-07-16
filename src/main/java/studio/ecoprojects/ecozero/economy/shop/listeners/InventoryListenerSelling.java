package studio.ecoprojects.ecozero.economy.shop.listeners;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;

import java.text.NumberFormat;
import java.util.Objects;

public class InventoryListenerSelling implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getPlayer().getOpenInventory().getTitle().contains(Colors.translateCodes("&2&lSell GUI: &f"))) {
            e.setCancelled(true);
            ItemStack item = e.getView().getPlayer().getOpenInventory().getItem(4);
            Player player = (Player) e.getView().getPlayer();
            NBT.get(Objects.requireNonNull(item), nbt -> {
                Integer amount = NBT.readNbt(Objects.requireNonNull(e.getCurrentItem())).getInteger("Amount");
                Product product = Economy.getShop().getProduct(nbt.getUUID("ItemUUID"));
                if (product != null) {
                    if (e.getSlot() == e.getInventory().getSize()-9) {
                        SubShop subShop = product.getSubShop();
                        player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
                        if (subShop.shopPageContainsPlayer(player)) {
                            player.openInventory(subShop.getInventory(subShop.getShopPagePlayerIsOn(player)));
                        } else {
                            player.openInventory(subShop.getInventory(1));
                        }
                    } else {
                        if (amount != null && amount > 0) {
                            String prefix = EcoZero.getPlugin().getConfig().getString("economy-prefix");
                            ItemStack price = new ItemStack(product.getMaterial(), amount);
                            if (!player.getInventory().contains(price) || !player.getInventory().contains(product.getMaterial(), amount)) {
                                player.playSound(player, Sound.ENTITY_VILLAGER_NO, 1f, 1f);
                                player.sendMessage(Colors.translateCodes(prefix + " &cYour inventory does not contain " + amount + " of " + product.getDisplayName() + "."));
                            } else {
                                // TODO some day, redo this code to make it in a class and with buying.
                                // Selling
                                player.playSound(player, Sound.ENTITY_VILLAGER_CELEBRATE, 1f, 1f);
                                Double newBalance = Economy.getBalance(player.getUniqueId())+(product.getSellPrice()*amount);
                                Economy.setBalance(player.getUniqueId(), newBalance);
                                player.getInventory().remove(price);
                                player.sendMessage(Colors.translateCodes(prefix + " &aSold " + amount + " of " + product.getDisplayName() + ". &7Your new balance is: &2&l$&f" + NumberFormat.getInstance().format(newBalance)));

                            }
                        }
                    }
                }
            });
        }
    }


}
