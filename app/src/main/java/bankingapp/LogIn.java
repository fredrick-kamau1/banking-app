
package bankingapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * @author Fredrick Kamau. Class logIn that requires a user to enter their account and PIN number and once
 * authenticated, they are presented with a menu of different transactions they can carry out on their account.
 */
public class LogIn {

    /**
     * Method checkAccount which checks if the user entered account number and password are contained in the
     * database. If the account is available, the user is presented with a menu to carry out different account
     * transactions
     * @param con
     * @param accountNumber
     * @param pinNumber
     * @throws SQLException
     */
    public void checkAccount(DBTransactions db, Connection con, String accountNumber, String pinNumber)
            throws SQLException {
        Scanner input = new Scanner(System.in);

        if (db.checkAcc(accountNumber, Integer.parseInt(pinNumber))) {
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
                    int balance = db.checkBalance(accountNumber);
                    System.out.println("\nBalance: " + balance);
                    break;

                case 2:
                    System.out.println("Enter income:");
                    income = input.nextInt();
                    db.addIncome(income, accountNumber);
                    System.out.println("Income was added!");
                    break;

                case 3:
                    App.transferFunds(db,con, accountNumber);
                    break;

                case 4:
                    if(db.checkBalance(accountNumber) > 0){
                        System.out.println("Transfer account balance to a different account before closing");
                    }else {
                        db.deleteAcc(accountNumber);
                        System.out.println("The account has been closed!");
                        break subMenu;
                    }
                    break;

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


