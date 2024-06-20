package studio.ecoprojects.ecozero.utils;

import org.jdbi.v3.core.Jdbi;
import studio.ecoprojects.ecozero.EcoZero;
import studio.ecoprojects.ecozero.discordintergration.database.VerifiedDB;
import studio.ecoprojects.ecozero.economy.database.EconomyDB;

public class DataBaseSetUp {

    public static String username;
    public static String password;
    public static String url;
    public static Jdbi jdbi;

    public static void login() {
        jdbi = Jdbi.create(url, username, password);
        if (!VerifiedDB.dataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'verified' table.. creating it");
            VerifiedDB.createVerifiedTable();
        }
        if (!EconomyDB.dataBaseHasTable()) {
            EcoZero.logger.info("Database does not contain 'balances' table.. creating it");
            EconomyDB.createBalancesTable();
        }

        EcoZero.logger.info("DataBase Connected");
    }

}
