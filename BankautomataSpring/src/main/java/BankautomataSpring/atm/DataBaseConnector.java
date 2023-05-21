package BankautomataSpring.atm;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DataBaseConnector {
    private static Jdbi jdbi;

    public static Jdbi getJdbi() {
        //jdbc:mysql://db4free.net:3306/database-name
        //jdbi = Jdbi.create("jdbc:mysql://db4free.net:3306/kozosdb","kozosuser", "dbjelszo");
        Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost/java","root","password");
        //Jdbi jdbi = Jdbi.create("jdbc:mysql://localhost/probadb","root", "" );

        jdbi.installPlugin(new SqlObjectPlugin());
        //jdbi.open();
        return jdbi;
    }
}


//Egy jó rekord
//kártyaszám: 1111222233334444
//PIN: 2015

