package studio.ecoprojects.ecozero.economy.shop.Inventories;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class ShopInventory {

    private final Inventory inventory;
    private final String shopName;

    public ShopInventory() {
        shopName = Colors.translateCodes("&2&lShop");
        inventory = Bukkit.createInventory(null, 54, this.shopName);
        setUpInventory();
    }

    public void reloadInventory() {
        setUpInventory();
    }

    public Inventory getInventory() {
        return inventory;
    }
    public String getShopName() {
        return shopName;
    }

    private void setUpInventory() {
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

        try {
            EcoZero.getEconomy().getShop().getSubShops().values().forEach(subShop -> {
                ItemStack itemStack = new ItemStack(subShop.getMaterial());
                ItemUtils.setItemName(itemStack, subShop.getDisplayName());
                ItemUtils.setItemFlags(itemStack, ItemFlag.HIDE_ATTRIBUTES);
                ItemUtils.setItemLore(itemStack, List.of(Colors.translateCodes("&7Click to go to " + subShop.getName())));

                NBT.modify(itemStack, nbt -> {
                    nbt.setString("SubShopName", subShop.getName());
                });

                inventory.setItem(subShop.getSlot(), itemStack);
            });
        } catch (NullPointerException e) {
            // Once made, make sure this is replaced with loading/seeing if there's any subshops
            e.printStackTrace();
        }


        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.setItemName(close, "&c&lClose");
        List<String> lore = new ArrayList<>();
        lore.add(Colors.translateCodes("&7Click to close this GUI"));
        ItemUtils.setItemLore(close, lore);
        ItemUtils.setItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);

        inventory.setItem(inventory.getSize() - 5, close);
    }

}
