package studio.ecoprojects.ecozero.economy;

import org.bukkit.OfflinePlayer;
import studio.ecoprojects.ecozero.EcoZero;

import java.util.*;

public class Economy {

    private static HashMap<UUID, Double> balances = new HashMap<>();


    public static Double getBalance(UUID uuid) {
        return balances.get(uuid);
    }

    public static void setBalance(UUID uuid, Double value) {
        balances.put(uuid, value);
    }

    public static void addBalance(UUID uuid, Double value) {
        balances.put(uuid, value + balances.get(uuid));
    }

    public static void removeBalance(OfflinePlayer player, Double value) {
        balances.put(player.getUniqueId(), value - balances.get(player.getUniqueId()));
    }

    public static Boolean hasAccount(UUID uuid) {
        return balances.containsKey(uuid);
    }

    public static void createAccount(UUID uuid) {
        balances.put(uuid, EcoZero.getPlugin().getConfig().getDouble("starting-balance"));
    }

    public static Set<UUID> getBalanceKeySet() {
        return balances.keySet();
    }



}
