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
        input = new Scanner(System.in);
        setFirstName();
        setLastName();
        this.firstName = getFirstName();
        this.lastName = getLastName();
        this.toString();
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

        //accountNum = checkSumNum(accountNum);

        int pinNum = rand2.nextInt(9999 - 1000 + 1) + 1000;
        System.out.println("\nYour card has been created\nYour card number:\n" + accountNum +
                "\nYour card PIN:\n" + pinNum);
        account.put(accountNum, pinNum);



        //insertDB(statement, accountNum, String.valueOf(pinNum));

    }

    /**
     * Setter method to set fistName
     */
    public void setFirstName(){
        System.out.println("Enter First Name:");
        this.firstName = input.nextLine();
    }

    /**
     * Getter method to get the first name
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }

    /**
     * Setter method to set fistName
     */
    public void setLastName(){
        System.out.println("Enter Last Name:");
        this.lastName = input.nextLine();
    }

    /**
     * Getter method to get the last name
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Getter method to get the Account Number
     * @return accountNumber
     */
    public String getAccountNumber() {
        Random rand = new Random();
        long upperValue = 999999999L;
        long lowerValue = 100000000L;
        String accountNumber = "";
        String checkedNumber = "";

        while(true) {
            accountNumber = "400000" + (rand.nextLong(upperValue - lowerValue + 1) + lowerValue);
            //Check if the random number passes the luhn's algorithm
            if (luhnsAlgorithmCheck(accountNumber)) {
                checkedNumber = accountNumber;
                break;
            }
        }
        return checkedNumber;

    }

    /**
     * Getter method to get the PIN-number
     * @return
     */
    public int getPinNumber() {
        Random rand = new Random();
        int upperValue = 9999;
        int lowerValue = 1000;
        return rand.nextInt(upperValue - lowerValue + 1) + lowerValue;
    }

    public boolean luhnsAlgorithmCheck(String accountNum){
        char numChar;
        int numInteger;
        int sum = 0;
        for (int i = 0; i < accountNum.length(); i++) {
            numChar = accountNum.charAt(i);
            numInteger = Integer.parseInt(String.valueOf(numChar));

            if (i % 2 == 0) {
                numInteger *= 2;
            }

            if (numInteger > 9) {
                numInteger -= 9;
            }

            sum += numInteger;
        }
        return (sum % 10 == 0);
    }
    
    public String toString(){
        return "Thank you "+ getFirstName() + " " + getLastName() +"\nYour account has been created" +
                "\nYour card number is:\n" + getAccountNumber() +
                "\nYour card PIN:\n" + getPinNumber();
    }
}
