package studio.ecoprojects.ecozero.economy.shop;

import de.tr7zw.nbtapi.NBT;
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

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Shop {

    private final SubShopOres oresSubShop = new SubShopOres();

    public SubShopOres getOresSubShop() {
        return oresSubShop;
    }

    // Inventories
    private final ShopInventories shopInventories = new ShopInventories();
    private final Inventory shopInventory = shopInventories.createShopGui();

    public Inventory getShopInventory() {
        return shopInventory;
    }

    public ShopInventories getInventoriesInstance() {
        return shopInventories;
    }

    // Products
    private final HashMap<UUID, Product> products = new HashMap<>();

    public Product getProduct(UUID uuid) {
        return products.get(uuid);
    }

    public void addProduct(Product product) {
        products.put(product.getUuid(), product);
    }

}
