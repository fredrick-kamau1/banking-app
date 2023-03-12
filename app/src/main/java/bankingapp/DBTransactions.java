package bankingapp;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class DBTransactions implements IDBTransactions {

    static SQLiteDataSource dataSource = new SQLiteDataSource();
    String url;
    Connection connection;

    public DBTransactions() throws SQLException{
        this.url = "jdbc:sqlite:account.db";
        this.connection = DriverManager.getConnection(url);
    }

    /**
     * Method createDB creates a new table in the SQLite database if it does not already exist. It takes a Statement
     * object as input and throws an SQLException if there is an error executing the SQL command.
     * @throws SQLException
     */
    public void createDB()  {
        try {
            Statement statement = this.connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS account(" +
                    "first_name TEXT," +
                    "last_name TEXT," +
                    "id INTEGER, " +
                    "accountNum TEXT," +
                    "pin TEXT," +
                    "balance INTEGER DEFAULT 0)");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * Method InsertDB inserts a new row into the card table with a given first_name, last_name, accountNumber and
     * pinNum. It takes a Statement object, a card number and a pinNum as input, and throws an SQLException if there
     * is an error executing the SQL command.
     *
     * @param first_name
     * @param last_name
     * @param accountNumber
     * @param pinNum
     * @throws SQLException
     */
    public void insertDB(String first_name, String last_name, String accountNumber,
                                String pinNum) {

        String insert = "INSERT INTO account (first_name, last_name, accountNum, pin) VALUES (?,?,?,?)";

        try (PreparedStatement statement = this.connection.prepareStatement(insert)) {
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, accountNumber);
            statement.setString(4, pinNum);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method addIncome adds income amount of money to the balance field in the card table for a specific account
     * identified by accountNum. It takes a Connection object, an income amount and an accountNum as input, and throws an
     * SQLException if there is an error executing the SQL command.
     *
     * @param income
     * @param accountNum
     * @throws SQLException
     */
    public void addIncome(int income, String accountNum) {
        String insert = "UPDATE account SET balance = balance + ? WHERE accountNum = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(insert)){
            statement.setInt(1, income);
            statement.setString(2, accountNum);
            statement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method deductBalance deducts amount of money from the balance field in the card table for a specific account
     * identified by accountNum. It takes a Connection object, a deduct amount and an accountNum as input, and throws an
     * SQLException if there is an error executing the SQL command.
     *
     * @param deductAmount
     * @param accountNum
     * @throws SQLException
     */
    public void deductBalance(int deductAmount, String accountNum) {
        String insert = "UPDATE account SET balance = balance - ? WHERE accountNum = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(insert)) {
            statement.setInt(1, deductAmount);
            statement.setString(2, accountNum);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Method checkBalance checks the balance field in the card table for a specific card identified by accountNum and
     * returns the balance amount. It takes a Connection object and an accountNum as input, and throws an SQLException
     * if there is an error executing the SQL command.
     *
     * @param accountNum
     * @return
     * @throws SQLException
     */
    public int checkBalance(String accountNum) {
        int amount = 0;
        String checkBal = "SELECT balance FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(checkBal)) {
            statement.setString(1,accountNum);
            statement.executeQuery();
            amount = statement.executeQuery().getInt(1);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return amount;
    }

    /**
     * Method checkAcc checks whether there exists a row in the account table with a given accountNum and pin. It takes
     * a Connection object, an accountNumber and a pin as input, and throws an SQLException if there is an error
     * executing the SQL command. It returns a boolean value indicating whether an account with the given accountNumber
     * and pin exists.
     *
     * @param accountNum
     * @param pin
     * @return isAccountAvailable
     * @throws SQLException
     */
    public boolean checkAcc(String accountNum, int pin) {
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ? AND pin = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.setInt(2, pin);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return isAccountAvailable;
    }

    /**
     * Method checkAcc_withoutPin checks whether there exists a row in the account table with a given accountNumber. It
     * takes a Connection object and a accountNumber as input, and throws an SQLException if there is an error executing
     * the SQL command. It returns a boolean value indicating whether an account with the given accountNumber exists.
     *
     * @param accountNum
     * @return isAccountAvailable
     * @throws SQLException
     */
    public boolean checkAcc_withoutPin(String accountNum) {
        boolean isAccountAvailable = false;
        String queryDB = "SELECT * FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            ResultSet ans = statement.executeQuery();
            isAccountAvailable = ans.isBeforeFirst();
        } catch (SQLException e){
            e.printStackTrace();
        }
        return isAccountAvailable;
    }

    /**
     * Method deleteAcc deletes a row in the card table with a given cardNumber. It takes a Connection object and an
     * accountNumber as input and throws an SQLException if there is an error executing the SQL command.
     *
     * @param accountNum
     * @throws SQLException
     */
    public void deleteAcc(String accountNum) {
        String queryDB = "DELETE FROM account WHERE accountNum = ?";

        try (PreparedStatement statement = this.connection.prepareStatement(queryDB)){
            statement.setString(1, accountNum);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
