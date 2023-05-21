package BankautomataSpring.atm;

import java.util.List;
import java.util.Optional;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

@RegisterBeanMapper(AtmSet.class)
public interface AtmSetDao {

    @SqlUpdate("""
        CREATE TABLE atmset (
            accountId VARCHAR(255) PRIMARY KEY,
            balance INTEGER NOT NULL
        )
        """
    )
    void createTable();

    @SqlUpdate("INSERT INTO atmset VALUES (:accountId, :balance, :cardNumber)")
    void insertAtmSet(@BindBean AtmSet atmSet);

    @SqlUpdate("INSERT INTO atmset VALUES (:accountId, :balance, :cardNumber)")
    void insertAtmSet(@Bind("accountId")String valueOf, @Bind("balance") int balance, @Bind("cardNumber") String cardNumber);

    @SqlQuery("SELECT * FROM atmset WHERE cardNumber = :cardNumber")
    Optional<AtmSet> getAtmSetCardNum(@Bind("cardNumber") String cardNumber);

    @SqlQuery("SELECT * FROM atmset WHERE accountId = :accountId")
    Optional<AtmSet> getAtmSet(@Bind("accountId") String accountId);

    @SqlQuery("SELECT * FROM atmset ORDER BY accountId")
    List<AtmSet> listAtmSets();

    @SqlQuery("""
            SELECT 'TRUE'  FROM DUAL WHERE EXISTS (SELECT accountId FROM  atmset WHERE accountId = 2015)
            UNION
            SELECT 'FALSE' FROM DUAL WHERE NOT EXISTS (SELECT accountId FROM  atmset WHERE accountId = 2015);
            """)
    String getIdAccount();

    @SqlUpdate("UPDATE atmset SET balance = balance - :withdraw WHERE accountId = :accountId")
    void withdrawATMSet(@Bind("accountId") String accountId, @Bind("withdraw") int withdraw);

    @SqlUpdate("UPDATE atmset SET balance = balance + :withdraw WHERE accountId = :accountId")
    void depositATMSet(@Bind("accountId") String accountId, @Bind("withdraw") int withdraw);

    @SqlUpdate("UPDATE atmset SET balance = :newBalance WHERE accountId = :accountId")
    void updateATMSet(@Bind("accountId") String accountId, @Bind("newBalance") int newBalance);



}
