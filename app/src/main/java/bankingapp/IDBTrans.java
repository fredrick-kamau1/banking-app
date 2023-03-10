package bankingapp;

import java.sql.SQLException;

public interface IDBTrans extends AutoCloseable {
    void createDB() throws SQLException;

    void insertDB(String first_name, String last_name, String accountNumber,
                  String pinNum) throws SQLException;

    void addIncome(int income, String accountNum) throws SQLException;

    int checkBalance(String accountNum) throws SQLException;

    boolean checkAcc(String accountNum, int pin) throws SQLException;

    void deleteAcc(String accountNum) throws SQLException;

    void transferFunds(String accountNum) throws SQLException;
}