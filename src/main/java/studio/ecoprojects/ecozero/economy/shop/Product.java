package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Product {


    private final Material material;
    private final Double buyPrice;
    private final Double sellPrice;
    private final String displayName;
    private final UUID uuid;
    private final SubShop subShop;
    private Inventory buyInventory;
    private Inventory sellInventory;

    public Product(Material mat, String displayName, Double buyPrice, Double sellPrice, SubShop subShop) {
        this.material = mat;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.displayName = displayName;
        this.subShop = subShop;
        this.uuid = UUID.randomUUID();
    }

    public Material getMaterial() {
        return material;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public String getDisplayName() {
        return displayName;
    }

    public UUID getUuid() {
        return uuid;
    }

    public SubShop getSubShop() {
        return subShop;
    }

    public Inventory getSellInventory() {
        return sellInventory;
    }

    public void setSellInventory(Inventory sellInventory) {
        this.sellInventory = sellInventory;
    }

    public Inventory getBuyInventory() {
        return buyInventory;
    }

    public void setBuyInventory(Inventory buyInventory) {
        this.buyInventory = buyInventory;
    }
}
