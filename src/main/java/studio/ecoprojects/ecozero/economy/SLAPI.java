package studio.ecoprojects.ecozero.economy;

import org.bukkit.entity.Player;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;

import java.util.UUID;

public class SLAPI {

    public static void loadAccount(Player player) {

        if (EconomyDB.getBalance(player.getUniqueId().toString()).isEmpty()) {
            EcoZero.logger.info("Could not find account for " + player.getName());
            Economy.createAccount(player.getUniqueId());
        } else {
            Double balance = EconomyDB.getBalance(player.getUniqueId().toString()).get();
            Economy.setBalance(player.getUniqueId(), balance);
            EcoZero.logger.info("Loaded account for " + player.getName());
        }
    }

    public static void saveAccounts() {
        for (UUID uuid : Economy.getBalanceKeySet()) {
            EconomyDB.removeAccount(uuid.toString());
            EconomyDB.addAccount(uuid.toString(), Economy.getBalance(uuid));
        }
    }

}
