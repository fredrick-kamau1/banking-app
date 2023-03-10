package bankingapp;

import java.sql.SQLException;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Fredrick. Class CreateAccount that creates a new bank account for a user. The user is required to provide
 * their first and last name, the system will in turn generate a random account number and PIN-number.
 */
public class CreateAccount {

    private String firstName;
    private String lastName;
    private String accountNumber;
    private int pin;
    Scanner scanner;

    CreateAccount(){
        scanner = new Scanner(System.in);
        setFirstName();
        setLastName();
        setAccountNumber();
        setPinNumber();
    }

    CreateAccount(IDBTrans dbTrans) throws SQLException{
        this();
        dbTrans.insertDB(getFirstName(), getLastName(), getAccountNumber(), String.valueOf(getPinNumber()));
    }

    /**
     * Setter method to set fistName
     */
    public void setFirstName(){
        String firstName;
        do{
            System.out.println("Enter First Name:");
            firstName = scanner.nextLine();
        }while (firstName.equals(null) || firstName.trim().isEmpty());
        this.firstName = firstName.trim();
    }

    /**
     * Getter method to get the first name
     * @return firstName
     */
    public String getFirstName() {
        return this.firstName;
    }


    /**
     * Setter method to set first name
     */
    public void setLastName(){
        String lastName;
        do {
            System.out.println("Enter Last Name:");
            lastName = scanner.nextLine();
        }while (lastName.equals(null) || lastName.trim().isEmpty());
        this.lastName = lastName.trim();
    }


    /**
     * Getter method to get the last name
     * @return lastName
     */
    public String getLastName() {
        return this.lastName;
    }

    /**
     * Setter method to set the Account Number
     */
    public void setAccountNumber() {
        Random rand = new Random();
        long upperValue = 9999999999L;
        long lowerValue = 1000000000L;
        String accountNumber = "";

        while(true) {
            accountNumber = "400000" + (rand.nextLong(upperValue - lowerValue + 1) + lowerValue);
            //Check if the random number passes the luhn's algorithm
            if (luhnsAlgorithmCheck(accountNumber)) {
                this.accountNumber = accountNumber;
                break;
            }
        }
    }

    /**
     * Getter method to get the Account Number
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * setter method to set the PIN-number
     */
    public void setPinNumber() {
        Random rand = new Random();
        int upperValue = 9999;
        int lowerValue = 1000;
        this.pin = rand.nextInt(upperValue - lowerValue + 1) + lowerValue;
    }

    /**
     * Getter method to get the PIN-number
     * @return pin
     */
    public int getPinNumber() {
        return pin;
    }

    /**
     * luhnsAlgorithmCheck method checks the randomly generated accountNumber to ensure it complies with the
     * luhn's algorithm
     * @param accountNumber
     * @return
     */
    public static boolean luhnsAlgorithmCheck(String accountNumber){
        char numChar;
        int numInteger;
        int sum = 0;
        for (int i = 0; i < accountNumber.length(); i++) {
            numChar = accountNumber.charAt(i);
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

    /**
     * Method toString prints out the first and last name as well as the newly created accountNumber and PIN
     * @return String
     */
    public String toString(){
        return "Thank you "+ getFirstName() + " " + getLastName() +"\nYour account has been created" +
                "\nYour account number is:\n" + getAccountNumber() +
                "\nYour account PIN:\n" + getPinNumber();
    }
}
