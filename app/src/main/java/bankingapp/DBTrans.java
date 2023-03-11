package bankingapp;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Scanner;

public class DBTrans {
    static Scanner scan;
    static SQLiteDataSource dataSource = new SQLiteDataSource();
    String url;
    Connection connection;

    public DBTrans() throws SQLException{
        url = "jdbc:sqlite:account.db";
        connection = DriverManager.getConnection(url);
    }

    /**
     * Method createDB creates a new table in the SQLite database if it does not already exist. It takes a Statement
     * object as input and throws an SQLException if there is an error executing the SQL command.
     * @param statement
     * @throws SQLException
     */
    public static void createDB(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS account(" +
                "first_name TEXT," +
                "last_name TEXT," +
                "id INTEGER, " +
                "accountNum TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0)");

    }

    /**
     * Method InsertDB inserts a new row into the card table with a given first_name, last_name, accountNumber and
     * pinNum. It takes a Statement object, a card number and a pinNum as input, and throws an SQLException if there
     * is an error executing the SQL command.
     * @param con
     * @param first_name
     * @param last_name
     * @param accountNumber
     * @param pinNum
     * @throws SQLException
     */
    public static void insertDB(Connection con, String first_name, String last_name, String accountNumber,
                                String pinNum) throws SQLException {
        String insert = "INSERT INTO account (first_name, last_name, accountNum, pin) VALUES (?,?,?,?)";

        try (PreparedStatement statement = con.prepareStatement(insert)) {
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, accountNumber);
            statement.setString(4, pinNum);
            statement.executeUpdate();
        }
    }

    /**
     * Method addIncome adds income amount of money to the balance field in the card table for a specific account
     * identified by accountNum. It takes a Connection object, an income amount and an accountNum as input, and throws an
     * SQLException if there is an error executing the SQL command.
     * @param con
     * @param income
     * @param accountNum
     * @throws SQLException
     */
    public static void addIncome(Connection con, int income, String accountNum) throws SQLException{
        String insert = "UPDATE account SET balance = balance + ? WHERE accountNum = ?";

        try (PreparedStatement statement = con.prepareStatement(insert)){
            statement.setInt(1, income);
            statement.setString(2, accountNum);
            statement.executeUpdate();
        }
    }

    /**
     * Method deductBalance deducts amount of money from the balance field in the card table for a specific account
     * identified by accountNum. It takes a Connection object, a deduct amount and an accountNum as input, and throws an
     * SQLException if there is an error executing the SQL command.
     * @param connection
     * @param deduct
     * @param accountNum
     * @throws SQLException
     */
    public static void deductBalance(Connection connection, int deduct, String accountNum) throws SQLException{
        String insert = "UPDATE account SET balance = balance - ? WHERE accountNum = ?";

        try (PreparedStatement statement = connection.prepareStatement(insert)) {
            statement.setInt(1, deduct);
            statement.setString(2, accountNum);
            statement.executeUpdate();
        }
    }

    /**
     * Method checkBalance checks the balance field in the card table for a specific card identified by accountNum and
     * returns the balance amount. It takes a Connection object and an accountNum as input, and throws an SQLException
     * if there is an error executing the SQL command.
     * @param connection
     * @param accountNum
     * @return
     * @throws SQLException
     */
    public static int checkBalance(Connection connection, String accountNum) throws SQLException{
        String checkBal = "SELECT balance FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = connection.prepareStatement(checkBal)) {
            statement.setString(1,accountNum);
            statement.executeQuery();
            return statement.executeQuery().getInt(1);
        }
    }

    /**
     * Method checkAcc checks whether there exists a row in the account table with a given accountNum and pin. It takes
     * a Connection object, an accountNumber and a pin as input, and throws an SQLException if there is an error
     * executing the SQL command. It returns a boolean value indicating whether an account with the given accountNumber
     * and pin exists.
     * @param connection
     * @param accountNum
     * @param pin
     * @return isAccountAvailable
     * @throws SQLException
     */
    public static boolean checkAcc(Connection connection, String accountNum, int pin) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ? AND pin = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.setInt(2, pin);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     * Method checkAcc_withoutPin checks whether there exists a row in the account table with a given accountNumber. It
     * takes a Connection object and a accountNumber as input, and throws an SQLException if there is an error executing
     * the SQL command. It returns a boolean value indicating whether an account with the given accountNumber exists.
     * @param connection
     * @param accountNum
     * @return isAccountAvailable
     * @throws SQLException
     */
    public static boolean checkAcc_withoutPin(Connection connection, String accountNum) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     * Method deleteAcc deletes a row in the card table with a given cardNumber. It takes a Connection object and an
     * accountNumber as input and throws an SQLException if there is an error executing the SQL command.
     * @param connection
     * @param accountNum
     * @throws SQLException
     */
    public static void deleteAcc(Connection connection, String accountNum) throws SQLException{
        String queryDB = "DELETE FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.executeUpdate();
        }
    }

}
