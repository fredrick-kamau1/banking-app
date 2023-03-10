
package bankingapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Fredrick. Class logIn that requires a user to enter their account and PIN number and once authenticated, they
 * are presented with a menu of different transactions they can carry out on their account.
 */
public class LogIn {

    private String accountNumber;
    private String pinNumber;
    private Scanner scanner;
    private IDBTrans dbTrans;

    public LogIn() {
        scanner = new Scanner(System.in);
        setAccountNumber();
        setPinNumber();
    }

    public LogIn(Connection connection, IDBTrans dbTrans) throws SQLException{
        this();
        this.dbTrans = dbTrans;
        checkAccount(connection, getAccountNumber(), getPinNum());
    }

    /**
     * Setter method to input the Account Number
     */
    public void setAccountNumber() {
        String accountNumber;
        do {
            System.out.println("\nEnter your account number:");
            accountNumber = scanner.next();
        } while (accountNumber.equals(null) || accountNumber.trim().isEmpty());
        this.accountNumber = accountNumber.trim();
    }

    /**
     * Getter method to get the user input of the Account Number
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * Setter method to input the PIN Number
     */
    public void setPinNumber() {
        String pinNumber;
        do {
            System.out.println("Enter your PIN:");
            pinNumber = scanner.next();
        } while (pinNumber.equals(null) || pinNumber.trim().isEmpty());
        this.pinNumber = pinNumber.trim();
    }

    /**
     * Getter method to get the user inputted PIN number
     * @return pinNumber
     */
    public String getPinNum() {
        return pinNumber;
    }

    /**
     * Method checkAccount which checks if the user entered account number and password are contained in the
     * database. If the account is available, the user is presented with a menu to carry out different account
     * transactions
     * @param con
     * @param accountNumber
     * @param pinNumber
     * @throws SQLException
     */
    public void checkAccount(Connection con, String accountNumber, String pinNumber) throws SQLException {
        if (dbTrans.checkAcc(con, accountNumber, Integer.parseInt(pinNumber))) {
            System.out.println("\nYou have successfully logged in!");

        int answer = -1;
        int income = 0;

        subMenu:
        do {
            System.out.println("\n1. Balance\n" +
                    "2. Add income\n" +
                    "3. Do transfer\n" +
                    "4. Close Account\n" +
                    "5. Log Out\n" +
                    "0. Exit");

            answer = scanner.nextInt();

            switch (answer) {
                case 1:
                    int balance = dbTrans.checkBalance(con, accountNumber);
                    System.out.println("\nBalance: " + balance);
                    break;

                case 2:
                    System.out.println("Enter income:");
                    income = scanner.nextInt();
                    dbTrans.addIncome(con, income, accountNumber);
                    System.out.println("Income was added!");
                    break;

                case 3:
                    dbTrans.transferFunds(con, accountNumber);
                    break;

                case 4:
                    dbTrans.deleteAcc(con, accountNumber);
                    System.out.println("The account has been closed!");
                    break subMenu;

                case 5:
                    System.out.println("\nYou have successfully logged out");
                    break subMenu;

                case 0:
                    System.exit(0);
            }
        } while (answer != 0);


    } else {
        System.out.println("Wrong account number or PIN");
    }

    }

}


