package bankingapp;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Fredrick Class CreateAccount that creates a new bank account for the user. The user is required to provide
 * their first and last name, the system will in turn generate a random account number and PIN-number.
 */
public class CreateAccount {

    String firstName;
    String lastName;
    String accountNumber;
    int pin;
    HashMap<String, Integer> account;
    Statement statement;
    Scanner input;

    CreateAccount(){
        this.firstName = getFirstName();
    }


    /**
     *
     * @param statement
     * @param account
     * @throws SQLException
     */
    public static void createAccount(Statement statement, HashMap<String, Integer> account) throws SQLException {
        Random rand = new Random();
        Random rand2 = new Random();
        long upper = 999999999L;
        long lower = 100000000L;
        String accountNum = "400000" + (rand.nextLong(upper - lower + 1) + lower);

        accountNum = checkSumNum(accountNum);

        int pinNum = rand2.nextInt(9999 - 1000 + 1) + 1000;
        System.out.println("\nYour card has been created\nYour card number:\n" + accountNum +
                "\nYour card PIN:\n" + pinNum);
        account.put(accountNum, pinNum);



        //insertDB(statement, accountNum, String.valueOf(pinNum));

    }

    /**
     * Getter method to get the first name
     * @return firstName
     */
    public String getFirstName() {
        System.out.println("Enter First Name:");
        return firstName = input.nextLine();
    }


    /**
     * Getter method to get the last name
     * @return lastName
     */
    public String getLastName() {
        System.out.println("Enter Last Name:");
        return lastName = input.nextLine();
    }

    /**
     * Getter method to get the Account Number
     * @return accountNumber
     */
    public String getAccountNumber() {
        Random rand = new Random();

        return accountNumber;
    }

    /**
     * Getter method to get the PIN-number
     * @return
     */
    public int getPin() {
        return pin;
    }
}
