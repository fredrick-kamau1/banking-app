
package bankingapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class LogIn {

    String accountNumber;
    String pinNumber;
    Connection connection;
    Scanner scanner;

    public LogIn() {
        scanner = new Scanner(System.in);
        setAccountNumber();
        setPinNumber();
    }

    public LogIn(Connection con) throws SQLException{
        this();
        checkAccount(con, getAccountNumber(), getPinNum());
    }


    public void setAccountNumber() {
        String accountNumber;
        do {
            System.out.println("\nEnter your account number:");
            accountNumber = scanner.next();
        } while (accountNumber.equals(null) || accountNumber.trim().isEmpty());
        this.accountNumber = accountNumber.trim();
    }


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setPinNumber() {
        String pinNumber;
        do {
            System.out.println("Enter your PIN:");
            pinNumber = scanner.next();
        } while (pinNumber.equals(null) || pinNumber.trim().isEmpty());
        this.pinNumber = pinNumber.trim();
    }

    public String getPinNum() {
        return pinNumber;
    }

    public void checkAccount(Connection con, String accountNumber, String pinNumber) throws SQLException {
        if (DBTrans.checkAcc(con, accountNumber, Integer.parseInt(pinNumber))) {
            System.out.println("\nYou have successfully logged in!");

        int answer = -1;
        int income = 0;

        subModule:
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
                    int balance = DBTrans.checkBalance(con, accountNumber);
                    System.out.println("\nBalance: " + balance);
                    break;

                case 2:
                    System.out.println("Enter income:");
                    income = scanner.nextInt();
                    DBTrans.addIncome(con, income, accountNumber);
                    System.out.println("Income was added!");
                    break;

                case 3:
                    DBTrans.transferFunds(con, accountNumber);
                    break;

                case 4:
                    DBTrans.deleteAcc(con, accountNumber);
                    System.out.println("The account has been closed!");
                    break subModule;

                case 5:
                    System.out.println("\nYou have successfully logged out");
                    break subModule;

                case 0:
                    System.exit(0);
            }
        } while (answer != 0);


    } else {
        System.out.println("Wrong card number or PIN");
    }
    }
}


