package studio.ecoprojects.ecozero.economy.shop.subshops;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Product;
import studio.ecoprojects.ecozero.economy.shop.SubShop;
import studio.ecoprojects.ecozero.utils.Colors;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubShopOres extends SubShop {

    private Inventory inventory;

    public SubShopOres() {
        setUp();
        Economy.getShop().addSubShop(this);
    }

    private void setUp() {
        this.setDisplayName(Colors.translateCodes("&8Ores"));
        this.setIcon(Material.COAL_ORE);
        this.setSlot(10);
        setItems();
        // I'm sorry, but this is probably so fucking shit
        inventory = Bukkit.createInventory(null, 54, Colors.translateCodes("&2&lShop &8&l>> &f" + this.getDisplayName()));
        inventory.setContents(Economy.getShop().getSubShopTemplate().getContents());
    }

    private void setItems() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            products.add(new Product(Material.COAL, "Coal", 15d, 6d, this));
            products.add(new Product(Material.REDSTONE, "Redstone", 6d, 2.25, this));
        }
        this.setProducts(products);
    }


    @Override
    public Inventory getInventory(int page) {
        boolean hasNextPage = this.getProducts().size() > (21 * page);
        boolean hasPreviousPage = page > 1;

        int currentIndex = 0;
        if (hasPreviousPage) { currentIndex = 21 * page; }

        for (int i = 10; i < Objects.requireNonNull(inventory).getSize()-1; i++) {
            if (i == 17 || i == 18) { i = 19; }
            if (i == 26 || i == 27) { i = 28; }
            if (i > 34 || currentIndex > this.getProducts().size()-1) { break; }

            Product product = this.getProducts().get(currentIndex);
            ItemStack itemStack = new ItemStack(product.getMaterial());
            ItemMeta meta = itemStack.getItemMeta();
            String formattedBuyPrice = NumberFormat.getInstance().format(product.getBuyPrice());
            String formattedSellPrice = NumberFormat.getInstance().format(product.getSellPrice());
            if (meta != null) {
                meta.setDisplayName(product.getProductName());
                meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                meta.setLore(List.of(
                        Colors.translateCodes("&bProduct's Information:"),
                        Colors.translateCodes("  &8- &7Buy Price: &2&l$" + formattedBuyPrice),
                        Colors.translateCodes("  &8- &7Sell Price: &2&l$" + formattedSellPrice),
                        Colors.translateCodes("&7Left click to buy, right click to sell.")
                ));
                itemStack.setItemMeta(meta);
            }

            NBT.modify(itemStack, nbt -> {
                nbt.setUUID("ProductUUID", product.getProductUUID());
            });

            inventory.setItem(i, itemStack);
            currentIndex++;
        }

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

        if (hasPreviousPage) { inventory.setItem(inventory.getSize() - 9, pastPageArrow); }
        if (hasNextPage) { inventory.setItem(inventory.getSize() + 1, nextPageArrow); }

        return inventory;
    }


}
