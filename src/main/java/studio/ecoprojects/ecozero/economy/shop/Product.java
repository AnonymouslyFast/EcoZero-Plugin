package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;

import java.util.UUID;

public class Product {

    private final String productName;
    private final double buyPrice;
    private final double sellPrice;
    private final Material productMaterial;
    private final UUID productUUID;
    private final SubShop subShop;

    public Product(Material productMaterial, String productName, double buyPrice, double sellPrice, SubShop subShop) {
        this.productMaterial = productMaterial;
        this.productName = productName;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.productUUID = UUID.randomUUID();
        this.subShop = subShop;
    }

    public String getProductName() {
        return productName;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public Material getMaterial() {
        return productMaterial;
    }

    public UUID getProductUUID() {
        return productUUID;
    }

    public SubShop getSubShop() {
        return subShop;
    }
}
