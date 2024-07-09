package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.shop.subshops.SubShopOres;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private final SubShopOres oresSubShop = new SubShopOres();
    private final String shopInventoryName = "&2&lShop";

    public Inventory createShopGui() {
        Inventory inv = Bukkit.createInventory(null, 54, Colors.translateCodes(shopInventoryName));

        ItemStack[] backFills = new ItemStack[0];
        ItemStack backfill = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemUtils.setItemName(backfill, "&7");
        ItemUtils.setItemFlags(backfill, ItemFlag.HIDE_ATTRIBUTES);

        for(int i = 0; i <= inv.getSize() - 1; ++i) {
            ItemStack[] newArray = new ItemStack[i + 1];
            System.arraycopy(backFills, 0, newArray, 0, newArray.length - 1);
            newArray[i] = backfill;
            backFills = newArray;
        }

        inv.setContents(backFills);

        // Loops through the subshops and sets the slots to the subshop.
        SubShop.getSubShops().forEach(subShop -> {
            inv.setItem(subShop.getShopSlot(), subShop.getShopIcon());
            EcoZero.logger.info(subShop.getShopName() + ": " + subShop.getShopSlot());
        });

        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.setItemName(close, "&c&lClose");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to close this GUI"));
        ItemUtils.setItemLore(close, lore);
        ItemUtils.setItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);
        inv.setItem(inv.getSize()-5, close);



        return inv;
    }

    public Inventory createSubShopGUITemplate(String title, boolean hasPastPage, boolean hasNextPage) {
        Inventory inventory = Bukkit.createInventory(null, 54, title);
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
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.setItemName(close, "&c&lClose");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to close this GUI"));
        ItemUtils.setItemLore(close, lore);
        ItemUtils.setItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);

        ItemStack nextPageArrow = new ItemStack(Material.ARROW);
        ItemUtils.setItemName(nextPageArrow, "&6&lNext Page");
        List<String> nextPageLore = new ArrayList<>();
        nextPageLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go to the next page"));
        ItemUtils.setItemLore(nextPageArrow, nextPageLore);
        ItemUtils.setItemFlags(nextPageArrow, ItemFlag.HIDE_ATTRIBUTES);

        ItemStack pastPageArrow = new ItemStack(Material.ARROW);
        ItemUtils.setItemName(pastPageArrow, "&6&lPast Page");
        List<String> pastPageLore = new ArrayList<>();
        pastPageLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go to the past page"));
        ItemUtils.setItemLore(pastPageArrow, pastPageLore);
        ItemUtils.setItemFlags(pastPageArrow, ItemFlag.HIDE_ATTRIBUTES);

        ItemStack backToShop = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.setItemName(backToShop, "&3&lBack to Shop");
        List<String> backToShopLore = new ArrayList<>();
        backToShopLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go back to the shop GUI"));
        ItemUtils.setItemLore(backToShop, backToShopLore);
        ItemUtils.setItemFlags(backToShop, ItemFlag.HIDE_ATTRIBUTES);

        inventory.setItem(inventory.getSize()-5, close);
        if (hasNextPage) {
            inventory.setItem(inventory.getSize()-1, nextPageArrow);
        }
        if (hasPastPage) {
            inventory.setItem(inventory.getSize()-9, pastPageArrow);
        } else {
            inventory.setItem(inventory.getSize()-9, backToShop);
        }

        return inventory;
    }


    public SubShopOres getOresSubShop() {
        return oresSubShop;
    }

    public String getShopInventoryName() {
        return shopInventoryName;
    }
}
