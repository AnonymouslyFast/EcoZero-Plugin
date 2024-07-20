package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.inventories.SubShopTemplate;
import studio.ecoprojects.ecozero.utils.Colors;

import java.text.NumberFormat;
import java.util.List;

public class SubShop {

    private String displayName;
    private ItemStack icon;
    private int slot;
    private List<Product> products;
    private final Inventory inventory = Economy.getShop().getSubShopTemplate();

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return this.displayName;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }
    public int getSlot() {
        return this.slot;
    }

    public void setIcon(Material material) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(this.displayName);
            meta.setLore(List.of(Colors.translateCodes("&7Click to open the sub shop: " + this.displayName + "&7.")));
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            item.setItemMeta(meta);
        }
        this.icon = item;
    }
    public ItemStack getIcon() {
        return this.icon;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        Economy.getShop().addProducts(products);
    }
    public List<Product> getProducts() {
        return this.products;
    }

    public Inventory getInventory(int page) {
        return this.inventory;
    }



}
