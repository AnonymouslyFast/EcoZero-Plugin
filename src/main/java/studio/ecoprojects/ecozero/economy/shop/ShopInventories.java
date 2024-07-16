package studio.ecoprojects.ecozero.economy.shop;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopInventories {

    public Inventory createShopGui() {
        Inventory inv = Bukkit.createInventory(null, 54, Colors.translateCodes("&2&lShop"));

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

    public Inventory createSellGUI(Product product) {
        String title = Colors.translateCodes("&2&lSell GUI: &f" + product.getDisplayName());
        Inventory inventory = Bukkit.createInventory(null, 36, title);
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

        ItemStack backToShop = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.setItemName(backToShop, "&3&lBack to SubShop");
        List<String> backToShopLore = new ArrayList<>();
        backToShopLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go back to the sub shop"));
        ItemUtils.setItemLore(backToShop, backToShopLore);
        ItemUtils.setItemFlags(backToShop, ItemFlag.HIDE_ATTRIBUTES);

        // Block
        ItemStack block = new ItemStack(product.getMaterial());
        ItemUtils.setItemName(block, product.getDisplayName());
        ItemUtils.setItemFlags(block, ItemFlag.HIDE_ATTRIBUTES);
        String formatedBuyPrice = NumberFormat.getInstance().format(product.getBuyPrice());
        String formatedSellPrice = NumberFormat.getInstance().format(product.getSellPrice());
        ItemUtils.setItemLore(block,
                List.of(Colors.translateCodes("&bItem's Information:"),
                        Colors.translateCodes("  &7Buy price: &2&l$&f" + formatedBuyPrice),
                        Colors.translateCodes("  &7Sell price: &2&l$&f" + formatedSellPrice),
                        Colors.translateCodes("&7You're currently Selling this item.")));
        NBT.modify(block, nbt -> {
            nbt.setUUID("ItemUUID", product.getUuid());
        });

        // Sell all
        ItemStack sellAll = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemUtils.setItemName(sellAll, "&a&lSell all");
        ItemUtils.setItemFlags(sellAll, ItemFlag.HIDE_ATTRIBUTES);
        ItemUtils.setItemLore(sellAll, List.of(Colors.translateCodes("&7Click to sell all of " + product.getDisplayName() + " in your inventory.")));

        // settings all slots to back-fill
        inventory.setContents(backFills);

        // setting slots
        inventory.setItem(inventory.getSize()-9, backToShop);
        inventory.setItem(4, block);
        inventory.setItem(8, sellAll);
        inventory.setItem(20, createSellItem(product, 1));
        inventory.setItem(21, createSellItem(product, 8));
        inventory.setItem(22, createSellItem(product, 16));
        inventory.setItem(23, createSellItem(product, 32));
        inventory.setItem(24, createSellItem(product, 64));



        return inventory;
    }

    public Inventory createBuyGUI(Product product) {
        String title = Colors.translateCodes("&2&lBuy GUI: &f" + product.getDisplayName());
        Inventory inventory = Bukkit.createInventory(null, 36, title);
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

        ItemStack backToShop = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.setItemName(backToShop, "&3&lBack to SubShop");
        List<String> backToShopLore = new ArrayList<>();
        backToShopLore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go back to the sub shop"));
        ItemUtils.setItemLore(backToShop, backToShopLore);
        ItemUtils.setItemFlags(backToShop, ItemFlag.HIDE_ATTRIBUTES);

        // Block
        ItemStack block = new ItemStack(product.getMaterial());
        ItemUtils.setItemName(block, product.getDisplayName());
        ItemUtils.setItemFlags(block, ItemFlag.HIDE_ATTRIBUTES);
        String formatedBuyPrice = NumberFormat.getInstance().format(product.getBuyPrice());
        String formatedSellPrice = NumberFormat.getInstance().format(product.getSellPrice());
        ItemUtils.setItemLore(block,
                List.of(Colors.translateCodes("&bItem's Information:"),
                        Colors.translateCodes("  &7Buy price: &2&l$&f" + formatedBuyPrice),
                        Colors.translateCodes("  &7Sell price: &2&l$&f" + formatedSellPrice),
                        Colors.translateCodes("&7You're currently Buying this item.")));
        NBT.modify(block, nbt -> {
            nbt.setUUID("ItemUUID", product.getUuid());
        });

        // Sell all
        ItemStack sellAll = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemUtils.setItemName(sellAll, "&a&lCustom Buy");
        ItemUtils.setItemFlags(sellAll, ItemFlag.HIDE_ATTRIBUTES);
        ItemUtils.setItemLore(sellAll, List.of(Colors.translateCodes("&7Click to activate custom buy")));

        // settings all slots to back-fill
        inventory.setContents(backFills);

        // setting slots
        inventory.setItem(inventory.getSize()-9, backToShop);
        inventory.setItem(4, block);
        inventory.setItem(8, sellAll);
        inventory.setItem(20, createBuyItem(product, 1));
        inventory.setItem(21, createBuyItem(product, 8));
        inventory.setItem(22, createBuyItem(product, 16));
        inventory.setItem(23, createBuyItem(product, 32));
        inventory.setItem(24, createBuyItem(product, 64));



        return inventory;
    }

    private ItemStack createSellItem(Product product, int amount) {
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemUtils.setItemName(itemStack, "&a&lSell " + amount);
        String formatedSellPrice = NumberFormat.getInstance().format(product.getSellPrice() * amount);
        ItemUtils.setItemLore(itemStack, List.of(
                Colors.translateCodes("&7Sell " + amount + " of " + product.getDisplayName() + " for:"),
                Colors.translateCodes("  &8- &2&l$&f" + formatedSellPrice)));
        ItemUtils.setItemFlags(itemStack, ItemFlag.HIDE_ATTRIBUTES);
        NBT.modify(itemStack, nbt -> {
            nbt.setInteger("Amount", amount);
        });

        return itemStack;
    }

    private ItemStack createBuyItem(Product product, int amount) {
        ItemStack itemStack = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemUtils.setItemName(itemStack, "&a&lBuy " + amount);
        String formatedBuyPrice = NumberFormat.getInstance().format(product.getBuyPrice() * amount);
        ItemUtils.setItemLore(itemStack, List.of(
                Colors.translateCodes("&7Buy " + amount + " of " + product.getDisplayName() + " for:"),
                Colors.translateCodes("  &8- &2&l$&f" + formatedBuyPrice)));
        ItemUtils.setItemFlags(itemStack, ItemFlag.HIDE_ATTRIBUTES);
        NBT.modify(itemStack, nbt -> {
            nbt.setInteger("Amount", amount);
        });

        return itemStack;
    }

}
