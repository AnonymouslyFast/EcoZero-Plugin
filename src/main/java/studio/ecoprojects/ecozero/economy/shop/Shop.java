package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.inventory.Inventory;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.shop.inventories.ShopInventory;
import studio.ecoprojects.ecozero.economy.shop.inventories.SubShopTemplate;
import studio.ecoprojects.ecozero.economy.shop.subshops.SubShopOres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Shop {

    // SubShops
    private SubShopOres subShopOres;

    // Inventories
    private Inventory subShopTemplate;
    private Inventory shopInventory;

    // Lists
    private final HashMap<UUID, Product> allProducts = new HashMap<>();
    private final List<SubShop> subShops = new ArrayList<>();

    public Shop() {
        try {
            subShopOres = new SubShopOres();
            shopInventory = new ShopInventory().getInventory();
            subShopTemplate = new SubShopTemplate().getInventory();
        } catch (NullPointerException e) {
            EcoZero.logger.info("Something is null!!");
        }
    }


    public SubShopOres getSubShopOres() {
        return subShopOres;
    }

    public Inventory getSubShopTemplate() {
        return subShopTemplate;
    }

    public Inventory getShopInventory() {
        return shopInventory;
    }

    public void addProducts(List<Product> products) {
        for (Product product : products) {
            this.allProducts.put(product.getProductUUID(), product);
        }
    }

    public Product getProduct(UUID uuid) {
        return this.allProducts.get(uuid);
    }

    public void addSubShop(SubShop subShop) {
        subShops.add(subShop);
    }

    public List<SubShop> getSubShops() {
        return subShops;
    }

}
