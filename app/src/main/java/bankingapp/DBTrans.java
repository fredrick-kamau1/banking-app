package bankingapp;

import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Scanner;

public class DBTrans implements IDBTrans {
    static Scanner scan;
    static SQLiteDataSource dataSource = new SQLiteDataSource();
    String url;
    Connection connection;

    public DBTrans() throws SQLException{
        scan = new Scanner(System.in);
        url = "jdbc:sqlite:account.db";
        connection = DriverManager.getConnection(url);
    }

    /**
     * Method createDB creates a new table in the SQLite database if it does not already exist. It takes a Statement
     * object as input and throws an SQLException if there is an error executing the SQL command.
     * @param statement
     * @throws SQLException
     */
    @Override
    public void createDB(Statement statement) throws SQLException {
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
    @Override
    public void insertDB(Connection con, String first_name, String last_name, String accountNumber,
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
    @Override
    public void addIncome(Connection con, int income, String accountNum) throws SQLException{
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
    private void deductBalance(Connection connection, int deduct, String accountNum) throws SQLException{
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
    @Override
    public int checkBalance(Connection connection, String accountNum) throws SQLException{
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
    @Override
    public boolean checkAcc(Connection connection, String accountNum, int pin) throws SQLException{
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
    private boolean checkAcc_withoutPin(Connection connection, String accountNum) throws SQLException{
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
    @Override
    public void deleteAcc(Connection connection, String accountNum) throws SQLException{
        String queryDB = "DELETE FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.executeUpdate();
        }
    }

    /**
     * Method transferFunds transfers funds from the users account to another account
     * @param connection
     * @param accountNum
     * @throws SQLException
     */
    @Override
    public void transferFunds(Connection connection, String accountNum) throws SQLException {

        // Prompt the user for the card number of the account they want to transfer funds to
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = scan.next();

        // Check if the card number is valid using the Luhn's algorithm
        if (CreateAccount.luhnsAlgorithmCheck(cardNumber)) {
            // Check if the card number is in the database and belongs to another account
            if (!checkAcc_withoutPin(connection, cardNumber)) {
                System.out.println("Such a card does not exist");
            } else if (cardNumber.equals(accountNum)) {
                System.out.println("You can't transfer money to the same account!");
            } else {
                // Prompt the user for the amount of money to transfer
                System.out.println("Enter how much money you want to transfer:");
                int funds = scan.nextInt();

                // Check if the user has enough balance to transfer the requested amount of money
                if (funds > checkBalance(connection, accountNum)) {
                    System.out.println("Not enough money!");
                } else {
                    // Add the transferred funds to the recipient's account and deduct them from the user's account
                    addIncome(connection, funds, cardNumber);
                    deductBalance(connection,funds,accountNum);
                    System.out.println("Success!");
                }
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again");

        }
    }
}
