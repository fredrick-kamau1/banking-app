/**
 * A simple banking application that allows users to create a new account and log into an existing account.
 * The application uses SQLite as its database and includes helper classes for creating and manipulating the database,
 * as well as classes for creating and logging into user accounts.
 *
 * This program takes one command line argument, which should be the URL of the SQLite database file to use.
 *
 * @author Fredrick Kamau
 * @version 2.0
 * @since 2022
 */
package bankingapp;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    static Scanner input = new Scanner(System.in);

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {

        SQLiteDataSource dataSource = new SQLiteDataSource();

        //String url set as program parameter
        dataSource.setUrl(args[0]);

        //Connect to the database
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                //Create DB
                DBTransactions db = new DBTransactions();
                db.createDB();

                int response = -1;

                do {
                    System.out.println("\n1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit");

                    try {
                        System.out.print("> ");
                        response = Integer.parseInt(input.nextLine());

                        switch (response) {

                            case 1:
                                //Create a new user account
                                String firstName;
                                String lastName;

                                 //Prompt user input for first and last names
                                 System.out.println("Enter First Name: ");
                                 System.out.print("> ");
                                 do {
                                    firstName = input.nextLine();
                                 } while (firstName.equals(null) || firstName.trim().isEmpty());

                                 System.out.println("Enter Last Name: ");
                                 System.out.print("> ");
                                 do {
                                    lastName = input.nextLine();
                                 }while (lastName.equals(null) || lastName.trim().isEmpty());

                                //Create a new account object
                                CreateAccount account = new CreateAccount();

                                //Create a new account number and pin
                                account.setAccountNumber();
                                account.setPinNumber();

                                //Display new user details
                                System.out.println(new CreateAccount(firstName, lastName, account.getAccountNumber(),
                                        account.getPinNumber()));

                                //Input new user info into DB
                                db.insertDB(firstName,lastName, account.getAccountNumber(),
                                        String.valueOf(account.getPinNumber()));
                                break;

                            case 2:
                                //login to an existing account
                                String accountNumber;
                                String pinNumber;

                                //Prompt user input for first and last names
                                System.out.println("\nEnter your account number:");
                                System.out.print("> ");
                                do {
                                    accountNumber = input.nextLine();
                                } while (accountNumber.equals(null) || accountNumber.trim().isEmpty());

                                System.out.println("Enter your PIN:");
                                System.out.print("> ");
                                do {
                                    pinNumber = input.nextLine();
                                } while (pinNumber.equals(null) || pinNumber.trim().isEmpty());

                                //Log in to an existing account
                                LogIn login = new LogIn();
                                login.checkAccount(db, connection, accountNumber, pinNumber);
                                break;
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input");
                        input.nextLine();
                    }
                } while (response != 0);

                System.out.println("\nBye!");

                //Close the Statement Object
            } catch (SQLException e) {
                e.printStackTrace();
            }

            //Close the Connection object
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void transferFunds(DBTransactions db, Connection connection, String accountNum) throws SQLException {

        // Prompt the user for the card number of the account they want to transfer funds to
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumber = input.nextLine();

        // Check if the card number is valid using the Luhn's algorithm
        if (CreateAccount.luhnsAlgorithmCheck(cardNumber)) {

            // Check if the card number is in the database and belongs to another account
            if (!db.checkAcc_withoutPin(cardNumber)) {
                System.out.println("Such a card does not exist");
            } else if (cardNumber.equals(accountNum)) {
                System.out.println("You can't transfer money to the same account!");
            } else {

                // Prompt the user for the amount of money to transfer
                System.out.println("Enter how much money you want to transfer:");
                int funds = input.nextInt();

                // Check if the user has enough balance to transfer the requested amount of money
                if (funds > db.checkBalance(accountNum)) {
                    System.out.println("Not enough money!");
                } else {

                    // Add the transferred funds to the recipient's account and deduct them from the user's account
                    db.addIncome(funds, cardNumber);
                    db.deductBalance(funds,accountNum);
                    System.out.println("Success!");
                }
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. Please try again");

        }
    }
}
