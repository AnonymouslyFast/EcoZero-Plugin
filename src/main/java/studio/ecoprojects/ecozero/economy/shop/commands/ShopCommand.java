package studio.ecoprojects.ecozero.economy.shop.commands;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.shop.Shop;
import studio.ecoprojects.ecozero.utils.Colors;

import java.util.List;
import java.util.Objects;

public class ShopCommand implements CommandExecutor, TabCompleter {

    private Shop shop;
    private Inventory inventory;


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (shop == null) {
            shop = EcoZero.getEconomy().getShop();
            inventory = Objects.requireNonNull(shop).getShopInventory().getInventory();
        }
        if (sender instanceof Player player) {
            player.playSound(player, Sound.ITEM_BOOK_PAGE_TURN, 1f, 1f);
            if (inventory == null && !shop.isEnabled()) {
                player.sendMessage(Colors.translateCodes("&cShop is currently disabled."));
            } else {
                player.openInventory(Objects.requireNonNull(inventory));
            }
        } else {
            sender.sendMessage(Colors.translateCodes("&7Cannot use this command in console!"));
        }
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}
