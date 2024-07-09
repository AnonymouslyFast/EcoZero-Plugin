package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ShopItem {

    private final Material material;
    private final Double buyPrice;
    private final Double sellPrice;
    private final String displayName;

    public ShopItem(Material mat, String displayName, Double buyPrice, Double sellPrice) {
        this.material = mat;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.displayName = displayName;
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
}
