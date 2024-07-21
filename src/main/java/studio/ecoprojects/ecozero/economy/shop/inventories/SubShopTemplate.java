package studio.ecoprojects.ecozero.economy.shop.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class SubShopTemplate {

    private final Inventory inventory;

    public SubShopTemplate() {
        inventory = Bukkit.createInventory(null, 54, "PLACEHOLDER");
        createInventory();
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

        ItemStack backToShop = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.setItemName(backToShop, "&3&lBack to Shop");
        List<String> backToShopLore = new ArrayList<>();
        backToShopLore.add(Colors.translateCodes("&7Click to go back to the shop GUI"));
        ItemUtils.setItemLore(backToShop, backToShopLore);
        ItemUtils.setItemFlags(backToShop, ItemFlag.HIDE_ATTRIBUTES);

        inventory.setItem(inventory.getSize() - 5, backToShop);

    }

    public Inventory getInventory() {
        return inventory;
    }
}
