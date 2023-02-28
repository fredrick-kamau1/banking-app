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
        scan = new Scanner(System.in);
        url = "jdbc:sqlite:account.db";
        connection = DriverManager.getConnection(url);
    }

    /**
     * Method createDB
     * @param statement
     * @throws SQLException
     */
    public static void createDB(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS account(" +
                "first_name TEXT" +
                "last_name TEXT" +
                "id INTEGER, " +
                "accountNum TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0)");

    }

    /**
     * Method InsertDB
     * @param statement
     * @param accountNumber
     * @param pinNum
     * @throws SQLException
     */
    public static void insertDB(Statement statement, String accountNumber, String pinNum) throws SQLException {
        statement.executeUpdate("INSERT INTO account (accountNum,pin) VALUES ('" + accountNumber + "', '" + pinNum + "')");
    }

    /**
     *
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
     *
     * @param con
     * @param deduct
     * @param accountNum
     * @throws SQLException
     */
    public static void deductBalance(Connection con, int deduct, String accountNum) throws SQLException{
        String insert = "UPDATE account SET balance = balance - ? WHERE accountNum = ?";

        try (PreparedStatement statement = con.prepareStatement(insert)) {
            statement.setInt(1, deduct);
            statement.setString(2, accountNum);
            statement.executeUpdate();
        }
    }

    /**
     *
     * @param con
     * @param accountNum
     * @return
     * @throws SQLException
     */
    public static int checkBalance(Connection con, String accountNum) throws SQLException{
        String checkBal = "SELECT balance FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = con.prepareStatement(checkBal)) {
            statement.setString(1,accountNum);
            statement.executeQuery();
            return statement.executeQuery().getInt(1);
        }
    }

    /**
     *
     * @param con
     * @param accountNum
     * @param pin
     * @return
     * @throws SQLException
     */
    public static boolean checkAcc(Connection con, String accountNum, int pin) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ? AND pin = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.setInt(2, pin);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     *
     * @param con
     * @param accountNum
     * @return
     * @throws SQLException
     */
    public static boolean checkAcc_withoutPin(Connection con, String accountNum) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     *
     * @param con
     * @param accountNum
     * @throws SQLException
     */
    public static void deleteAcc(Connection con, String accountNum) throws SQLException{
        String queryDB = "DELETE FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.executeUpdate();
        }
    }

    /**
     * 
     * @param con
     * @param accountNum
     * @throws SQLException
     */
    public static void transferFunds(Connection con, String accountNum) throws SQLException {

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = scan.next();

        //int accNum = Integer.parseInt(checkSum(cardNumber));

        if (CreateAccount.luhnsAlgorithmCheck(cardNumber)) {
            if (!checkAcc_withoutPin(con, cardNumber)) {
                System.out.println("Such a card does not exist");
            } else if (cardNumber.equals(accountNum)) {
                System.out.println("You can't transfer money to the same account!");
            } else {
                System.out.println("Enter how much money you want to transfer:");
                int funds = scan.nextInt();

                if (funds > checkBalance(con, accountNum)) {
                    System.out.println("Not enough money!");
                } else {
                    addIncome(con, funds, cardNumber);
                    deductBalance(con,funds,accountNum);
                    System.out.println("Success!");
                }
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again");

        }
    }
}
