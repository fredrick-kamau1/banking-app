
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
        Scanner input = new Scanner(System.in);

        if (DBTrans.checkAcc(con, accountNumber, Integer.parseInt(pinNumber))) {
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

            answer = input.nextInt();

            switch (answer) {
                case 1:
                    int balance = DBTrans.checkBalance(con, accountNumber);
                    System.out.println("\nBalance: " + balance);
                    break;

                case 2:
                    System.out.println("Enter income:");
                    income = input.nextInt();
                    DBTrans.addIncome(con, income, accountNumber);
                    System.out.println("Income was added!");
                    break;

                case 3:
                    App.transferFunds(con, accountNumber);
                    break;

                case 4:
                    DBTrans.deleteAcc(con, accountNumber);
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


