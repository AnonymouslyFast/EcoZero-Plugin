package studio.ecoprojects.ecozero.economy.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class ShopInventory {

    private Inventory inventory;

    public ShopInventory() {
        inventory = Bukkit.createInventory(null, 54, Colors.translateCodes("&2&lShop"));
        createInventory();
    }

    public Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, 54, Colors.translateCodes("&2&lShop"));
            createInventory();
        }
        return inventory;
    }

    private void createInventory() {
        ItemStack[] backFills = new ItemStack[0];
        ItemStack backfill = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemUtils.setItemName(backfill, "&7");
        ItemUtils.setItemFlags(backfill, ItemFlag.HIDE_ATTRIBUTES);

        for(int i = 0; i <= inventory.getSize() - 1; ++i) {
            ItemStack[] newArray = new ItemStack[i + 1];
            System.arraycopy(backFills, 0, newArray, 0, newArray.length - 1);
            newArray[i] = backfill;
            backFills = newArray;
        }

        inventory.setContents(backFills);

        Economy.getShop().getSubShops().iterator().forEachRemaining(subShop -> {
            inventory.setItem(subShop.getSlot(), subShop.getIcon());
        });

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.setItemName(close, "&c&lClose");
        List<String> lore = new ArrayList<>();
        lore.add(Colors.translateCodes("&7Click to close this GUI"));
        ItemUtils.setItemLore(close, lore);
        ItemUtils.setItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);

        inventory.setItem(inventory.getSize() - 5, close);
    }

}
