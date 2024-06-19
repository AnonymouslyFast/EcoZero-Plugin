package studio.ecoprojects.ecozero.discordintergration.listeners.minecraft;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import studio.ecoprojects.ecozero.discordintergration.BotEssentials;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;
import studio.ecoprojects.ecozero.utils.ItemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DiscordDashboard implements Listener {
    public static List<UUID> isRemoving = new ArrayList();
    public static HashMap<UUID, Inventory> LastInventory = new HashMap();


    private static Inventory CreateVerifiedInventory(List<UUID> uuids) {
        String title = ChatColor.translateAlternateColorCodes('&', "&9&lDashboard &8» &6&lVerified List");
        Inventory inventory = Bukkit.createInventory(null, 45, title);
        ItemStack[] backFills = new ItemStack[0];
        ItemStack backfill = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemUtils.SetItemName(backfill, "&7");
        ItemUtils.SetItemFlags(backfill, ItemFlag.HIDE_ATTRIBUTES);

        for(int i = 0; i <= inventory.getSize() - 1; ++i) {
            ItemStack[] newArray = new ItemStack[i + 1];
            System.arraycopy(backFills, 0, newArray, 0, newArray.length - 1);
            newArray[i] = backfill;
            backFills = newArray;
        }

        inventory.setContents(backFills);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.SetItemName(close, "&c&lClose");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to close this GUI"));
        ItemUtils.SetItemLore(close, lore);
        ItemUtils.SetItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);
        ItemStack back = new ItemStack(Material.FEATHER);
        ItemUtils.SetItemName(back, "&3&lBack");
        lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to go back to the previous GUI"));
        ItemUtils.SetItemLore(back, lore);
        ItemUtils.SetItemFlags(back, ItemFlag.HIDE_ATTRIBUTES);
        inventory.setItem(40, close);
        inventory.setItem(36, back);
        int EndingSlot = 34;
        int nextSlot = 10;

        for(int i = 0; i <= uuids.size() - 1; ++i) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(uuids.get(i));
            if (player.hasPlayedBefore()) {
                if (nextSlot == 17 || nextSlot == 18) { nextSlot = 19; }

                if (nextSlot == 26 || nextSlot == 27) { nextSlot = 28; }

                if (nextSlot > EndingSlot) { break; }

                ItemStack head = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta skullMeta = (SkullMeta)head.getItemMeta();
                skullMeta.setOwningPlayer(player);
                head.setItemMeta(skullMeta);
                ItemUtils.SetItemFlags(head, ItemFlag.HIDE_ATTRIBUTES);
                String prefix = EcoZero.luckperms.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix();
                if (prefix == null) { prefix = "&8&lDefault"; }

                ItemUtils.SetItemName(head, prefix + " &f" + player.getName());
                lore = new ArrayList();
                JDA jda = BotEssentials.jda;
                if (VerifiedDB.getUserID(player.getUniqueId().toString()).isPresent()) {
                    String id = VerifiedDB.getUserID(player.getUniqueId().toString()).get();
                    User user = jda.retrieveUserById(id).complete();
                    lore.add(ChatColor.translateAlternateColorCodes('&', "&b&lDiscord:"));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "  &7Username: &f" + user.getName()));
                    lore.add(ChatColor.translateAlternateColorCodes('&', "  &7ID: &f" + user.getId()));
                }

                ItemUtils.SetItemLore(head, lore);
                inventory.setItem(nextSlot, head);
                ++nextSlot;
            }
        }

        return inventory;
    }

    public static Inventory CreateDashBoard() {
        String title = ChatColor.translateAlternateColorCodes('&', "&9&lDiscord Dashboard");
        Inventory inventory = Bukkit.createInventory(null, 36, title);
        ItemStack[] backFills = new ItemStack[0];
        ItemStack backfill = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemUtils.SetItemName(backfill, "&7");
        ItemUtils.SetItemFlags(backfill, ItemFlag.HIDE_ATTRIBUTES);

        for(int i = 0; i <= inventory.getSize() - 1; ++i) {
            ItemStack[] newArray = new ItemStack[i + 1];
            System.arraycopy(backFills, 0, newArray, 0, newArray.length - 1);
            newArray[i] = backfill;
            backFills = newArray;
        }

        inventory.setContents(backFills);
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemUtils.SetItemName(close, "&c&lClose");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to close this GUI"));
        ItemUtils.SetItemLore(close, lore);
        ItemUtils.SetItemFlags(close, ItemFlag.HIDE_ATTRIBUTES);
        ItemStack seeVerified = new ItemStack(Material.BOOK);
        ItemUtils.SetItemName(seeVerified, "&6&lVerified List");
        lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to see all verified"));
        ItemUtils.SetItemLore(seeVerified, lore);
        ItemUtils.SetItemFlags(seeVerified, ItemFlag.HIDE_ATTRIBUTES);
        ItemStack removeVerified = new ItemStack(Material.STRUCTURE_VOID);
        ItemUtils.SetItemName(removeVerified, "&4&lRemove Verified");
        lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to remove verified from a player"));
        ItemUtils.SetItemLore(removeVerified, lore);
        ItemUtils.SetItemFlags(removeVerified, ItemFlag.HIDE_ATTRIBUTES);
        ItemStack Statics = new ItemStack(Material.PAPER);
        ItemUtils.SetItemName(Statics, "&3&lVerify Statistics");
        int size = VerifiedDB.getVerifiedUUIDS().size();
        int UniqueJoins = Bukkit.getOfflinePlayers().length;
        float overJoins = (float)size / (float)UniqueJoins;
        float pre = overJoins * 100.0F;
        int precent = Math.round(pre);
        lore = new ArrayList();
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7" + precent + "% of players are verified"));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7(" + size + " verified / " + UniqueJoins + " joins)"));
        ItemUtils.SetItemLore(Statics, lore);
        ItemUtils.SetItemFlags(Statics, ItemFlag.HIDE_ATTRIBUTES);
        inventory.setItem(31, close);
        inventory.setItem(13, seeVerified);
        inventory.setItem(11, removeVerified);
        inventory.setItem(15, Statics);
        return inventory;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        String title = ChatColor.translateAlternateColorCodes('&', "&9&lDiscord Dashboard");
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            if (event.getRawSlot() == 31) {
                event.getView().close();
            } else if (event.getRawSlot() == 11) {
                isRemoving.add(event.getView().getPlayer().getUniqueId());
                event.getView().close();
                event.getView().getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lVerification: &fPlayer removal is turned on, please enter the player's name in chat. &c&lEnter 'cancel' to cancel."));
            } else if (event.getRawSlot() == 13) {
                LastInventory.put(event.getView().getPlayer().getUniqueId(), event.getInventory());
                event.getView().getPlayer().openInventory(CreateVerifiedInventory(VerifiedDB.getVerifiedUUIDS()));
            }
        } else if (event.getView().getTitle().equals(ChatColor.translateAlternateColorCodes('&', "&9&lDashboard &8» &6&lVerified List"))) {
            event.setCancelled(true);
            if (event.getRawSlot() == 40) {
                event.getView().close();
            } else if (event.getRawSlot() == 36) {
                if (LastInventory.containsKey(event.getView().getPlayer().getUniqueId())) {
                    event.getView().getPlayer().openInventory(LastInventory.get(event.getView().getPlayer().getUniqueId()));
                } else {
                    event.getView().getPlayer().openInventory(CreateDashBoard());
                }
            }
        }

    }
}
