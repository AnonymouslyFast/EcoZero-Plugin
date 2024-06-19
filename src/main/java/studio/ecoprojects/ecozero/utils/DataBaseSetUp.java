package studio.ecoprojects.ecozero.utils;

import org.jdbi.v3.core.Jdbi;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;
import studio.ecoprojects.ecozero.economy.Economy;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;

public class DataBaseSetUp {

    public static String username;
    public static String password;
    public static String url;
    public static Jdbi jdbi;

    public static void Login() {
        jdbi = Jdbi.create(url, username, password);
        if (!VerifiedDB.DataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'verified' table.. creating it");
            VerifiedDB.CreateVerifiedTable();
        }
        if (!EconomyDB.DataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'balances' table.. creating it");
            EconomyDB.CreateBalancesTable();
        }

        EcoZero.logger.info("DataBase Connected");
    }

}
