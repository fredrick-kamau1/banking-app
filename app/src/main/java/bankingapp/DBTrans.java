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
        url = "jdbc:sqlite:card.db";
        connection = DriverManager.getConnection(url);
    }

    /**
     * Method createDB
     * @param statement
     * @throws SQLException
     */
    public static void createDB(Statement statement) throws SQLException {
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                "first_name TEXT" +
                "last_name TEXT" +
                "id INTEGER, " +
                "num TEXT," +
                "pin TEXT," +
                "balance INTEGER DEFAULT 0)");

    }

    /**
     * Method InsertDB
     * @param statement
     * @param number
     * @param pinNum
     * @throws SQLException
     */
    public static void insertDB(Statement statement, String number, String pinNum) throws SQLException {
        statement.executeUpdate("INSERT INTO card (num,pin) VALUES ('" + number + "', '" + pinNum + "')");
    }

    /**
     *
     * @param con
     * @param income
     * @param cardNum
     * @throws SQLException
     */
    public static void addIncome(Connection con, int income, String cardNum) throws SQLException{
        String insert = "UPDATE card SET balance = balance + ? WHERE num = ?";

        try (PreparedStatement statement = con.prepareStatement(insert)){
            statement.setInt(1, income);
            statement.setString(2, cardNum);
            statement.executeUpdate();
        }
    }

    /**
     *
     * @param con
     * @param deduct
     * @param cardNum
     * @throws SQLException
     */
    public static void deductBalance(Connection con, int deduct, String cardNum) throws SQLException{
        String insert = "UPDATE card SET balance = balance - ? WHERE num = ?";

        try (PreparedStatement statement = con.prepareStatement(insert)) {
            statement.setInt(1, deduct);
            statement.setString(2, cardNum);
            statement.executeUpdate();
        }
    }

    /**
     *
     * @param con
     * @param cardNum
     * @return
     * @throws SQLException
     */
    public static int checkBalance(Connection con, String cardNum) throws SQLException{
        String checkBal = "SELECT balance FROM CARD WHERE num = ?";

        try (PreparedStatement statement = con.prepareStatement(checkBal)) {
            statement.setString(1,cardNum);
            statement.executeQuery();
            return statement.executeQuery().getInt(1);
        }

    }

    /**
     *
     * @param con
     * @param cardNumber
     * @param pin
     * @return
     * @throws SQLException
     */
    public static boolean checkAcc(Connection con, String cardNumber, int pin) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM card WHERE num = ? AND pin = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, cardNumber);
            statement.setInt(2, pin);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     *
     * @param con
     * @param cardNumber
     * @return
     * @throws SQLException
     */
    public static boolean checkAcc_withoutPin(Connection con, String cardNumber) throws SQLException{
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM card WHERE num = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, cardNumber);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        }
        return isAccountAvailable;
    }

    /**
     *
     * @param con
     * @param cardNumber
     * @throws SQLException
     */
    public static void deleteAcc(Connection con, String cardNumber) throws SQLException{
        String queryDB = "DELETE FROM card WHERE num = ?";

        try (PreparedStatement statement = con.prepareStatement(queryDB)){
            statement.setString(1, cardNumber);
            statement.executeUpdate();
        }
    }

    /**
     * 
     * @param con
     * @param cardNum
     * @throws SQLException
     */
    public static void transferFunds(Connection con, String cardNum) throws SQLException {

        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = scan.next();

        //int accNum = Integer.parseInt(checkSum(cardNumber));

        if (CreateAccount.luhnsAlgorithmCheck(cardNumber)) {
            if (!checkAcc_withoutPin(con, cardNumber)) {
                System.out.println("Such a card does not exist");
            } else if (cardNumber.equals(cardNum)) {
                System.out.println("You can't transfer money to the same account!");
            } else {
                System.out.println("Enter how much money you want to transfer:");
                int funds = scan.nextInt();

                if (funds > checkBalance(con, cardNum)) {
                    System.out.println("Not enough money!");
                } else {
                    addIncome(con, funds, cardNumber);
                    deductBalance(con,funds,cardNum);
                    System.out.println("Success!");
                }
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again");

        }
    }
}
