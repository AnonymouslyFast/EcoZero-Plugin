package studio.ecoprojects.ecozero.economy.shop;

import org.bukkit.Material;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.utils.Colors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class SubShop {

    private final String name;
    private String displayName;
    private final List<Product> products = new ArrayList<>();
    private final List<SubShopPage> pageCache = new ArrayList<>();
    private final HashMap<String, Product> cachedProductNames = new HashMap<>();
    private final UUID uuid;
    private int slot;
    private Material material;
    private boolean isEnabled = false;

    public SubShop(String name, Material material, int slot) {
        this.name = name;
        displayName = Colors.translateCodes(name);
        this.slot = slot;
        this.material = material;
        uuid = UUID.randomUUID();
        EcoZero.getEconomy().getShop().addSubShop(this);
        reloadSubShop();
    }

    public void reloadSubShop() {
        reloadCachedProductNames();
        loadPageCache();
    }

    public String getName() {
        return name;
    }

    public List<Product> getProducts() {
        return products;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
    public void removeProduct(Product product) {
        products.remove(product);
    }

    public List<SubShopPage> getPageCache() {
        return pageCache;
    }
    public void addPageToCache(SubShopPage page) {
        pageCache.add(page);
    }
    public SubShopPage getPageFromCache(int index) {
        if (pageCache.isEmpty()) loadPageCache();
        if (index > pageCache.size()) return null;
        return pageCache.get(index);
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getSlot() {
        return slot;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }

    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = Colors.translateCodes(displayName);
    }

    public boolean isEnabled() {
        return isEnabled;
    }
    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public HashMap<String, Product> getCachedProducts() {
        return cachedProductNames;
    }

    private void reloadCachedProductNames() {
        cachedProductNames.clear();
        if (!products.isEmpty()) {
            for (Product product : products) {
                cachedProductNames.put(product.getMaterial().toString().toLowerCase(), product);
            }
        }
    }

    private void loadPageCache() {
        pageCache.clear();
        int maxItemsPerPage = 21;
        int currentPage = 1;
        int currentCount = 1;
        List<Product> tmpProducts = new ArrayList<>();
        for (Product product : products) {
            if (currentCount <= maxItemsPerPage) {
                tmpProducts.add(product);
            } else {
                boolean hasNextPage = products.size() > currentPage * currentCount;
                boolean hasPreviousPage = currentPage != 1;
                SubShopPage page = new SubShopPage(this, tmpProducts, currentPage, hasNextPage, hasPreviousPage);
                pageCache.add(page);
                tmpProducts.clear();
                currentPage++;
                currentCount = 1;
            }
            currentCount++;
        }
    }
}
