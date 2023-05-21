package BankautomataSpring.atm;
import org.jdbi.v3.core.Jdbi;
import java.util.Optional;
import java.util.List;

import org.jdbi.v3.core.Handle;


import java.util.NoSuchElementException;

public class AtmApplication {

    public static List<AtmSet> atmSets;
    private static AtmSetDao atmSetDao;
    private static Optional<AtmSet> atmGetBalance;



    /*
    public static String getBalance() {
        return balance;
    }
    */
    public static String getDbCardNumber() {
        return DbCardNumber;
    }
    public static String getPinCode(){return pinCode;}

    private static String balance = "";
    private static String DbCardNumber = "";
    private static String pinCode;


    public static String getBalance() {
        return isBalance(pinCode);
    }

    public static String isBalance(String PINCode) {
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            balance = String.valueOf(dao.getAtmSet(PINCode).get().getBalance());
        }
        return balance;
    }

    public static void depositFirst(String StringAmount){
        deposit(getPinCode(), Integer.parseInt(StringAmount));
    }

    public static void deposit(String PINCode, int amount) {
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            dao.depositATMSet(PINCode, amount);
        }
    }

    public static void withdrawalFirst(String StringAmount){
        withdrawal(getPinCode(), Integer.parseInt(StringAmount));
    }

    public static void withdrawal(String PINCode, int amount) {
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            dao.withdrawATMSet(PINCode, amount);
        }
    }

    public static void FirstInsertNewRecord(String newPin) {
        //String newPin = ATMLogic.randomPIN();
        insertNewRecord(newPin, 0, getDbCardNumber());
    }

    public static void insertNewRecord(String PINCode, int amount, String cardnumber){
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            dao.insertAtmSet(String.valueOf(PINCode), 0, cardnumber);
        }
    }

    public static void updateAccount(String PINCode, int amount){
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            dao.updateATMSet(PINCode, amount);
        }
    }


    public static boolean isAccountValid( String PinCode) {
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            try {

                balance = String.valueOf(dao.getAtmSet(PinCode).get().getBalance());
                DbCardNumber = String.valueOf(dao.getAtmSet(PinCode).get().getCardNumber());
                System.out.println(DbCardNumber);
            } catch (RuntimeException e) {
                return false;
            }
            return true;
        }
    }

    public static boolean isAccountValidCardNum( String cardNumber) {
        DbCardNumber = cardNumber;
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            try {

                balance = String.valueOf(dao.getAtmSetCardNum(cardNumber).get().getBalance());

            } catch (NoSuchElementException e ) {

                return false;
            }
            return true;
        }
    }

    public static void registerNewCustomer(String pinCode) {
        insertNewRecord(pinCode, 0, DbCardNumber);
    }
    public static boolean checkPinCode(String PINCode) {

        if (getAccountPinCode(DbCardNumber).equals(PINCode) ) {
            return true;
        }

        return false;
    }
    public static String getAccountPinCode( String cardNumber) {
        Jdbi jdbi = DataBaseConnector.getJdbi();
        try (
                Handle handle = jdbi.open()) {
            AtmSetDao dao = handle.attach(AtmSetDao.class);
            try {

                pinCode = String.valueOf(dao.getAtmSetCardNum(cardNumber).get().getAccountId());

            } catch (NoSuchElementException e ) {

                return "-1";
            }
            return pinCode;
        }
    }
}