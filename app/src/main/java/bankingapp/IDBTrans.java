package bankingapp;

import java.sql.Connection;
import java.sql.SQLException;

public interface IDBTrans {
    void createDB() throws SQLException;

    void insertDB(Connection con, String first_name, String last_name, String accountNumber,
                  String pinNum) throws SQLException;

    void addIncome(Connection con, int income, String accountNum) throws SQLException;

    int checkBalance(Connection connection, String accountNum) throws SQLException;

    boolean checkAcc(Connection connection, String accountNum, int pin) throws SQLException;

    void deleteAcc(Connection connection, String accountNum) throws SQLException;

    void transferFunds(Connection connection, String accountNum) throws SQLException;
}