package bankingApp;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Main {
    static Scanner input = new Scanner(System.in);


    public static void main(String[] args) {

        int response = -1;
        HashMap<String, Integer> account = new HashMap<>();

        do {
            System.out.println("\n1. Create an account\n" +
                    "2. Log into account\n" +
                    "0. Exit");

            response = input.nextInt();
            switch (response) {
                case 1:
                    createAccount(account);
                    break;

                case 2:
                    logIn(account);
                    break;
            }
        } while (response != 0);

        System.out.println("\nBye!");

    }

    public static void createAccount (HashMap<String, Integer> account) {
        Random rand = new Random();
        Random rand2 = new Random();
        long upper = 999999999L;
        long lower = 100000000L;
        String accountNum = "400000" + (rand.nextLong( upper - lower + 1) + lower);

        accountNum = checkSum(accountNum);


        int pin = rand2.nextInt(9999 - 1000 + 1) + 1000;
        System.out.println("\nYour card has been created\nYour card number:\n" + accountNum +
                "\nYour card PIN:\n" + pin);
        account.put(accountNum, pin);
    }


    //Luhn's Algorithm to check card validity
    public static String checkSum(String accountNum){
        char numChar;
        int numInteger;
        int sum = 0;
        final int MAX_NUM = 10;
        int checkSum = 0;
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

        for (int i = 0; i < MAX_NUM; i++) {
            if ((sum + i) % 10 == 0) {
                checkSum = i;
            }
        }
        return accountNum + checkSum;
    }

    public static void logIn (HashMap<String, Integer> account) {
        int response = -1;
        profile:
        do {
            System.out.println("\nEnter your card number:");
            String ans = input.next();
            System.out.println("Enter your PIN:");
            int resp = input.nextInt();

            if (account.containsKey(ans) && account.get(ans).equals(resp)) {
                System.out.println("\nsuccessfully logged in");
                int answer = -1;
                int balance = 0;

                do {
                    System.out.println("\n1. Balance\n" +
                            "2. Log out\n" +
                            "0. Exit");

                    answer = input.nextInt();

                    switch (answer) {
                        case 1:
                            System.out.println("\nBalance: " + balance);
                            break;
                        case 2:
                        case 0:
                            System.out.println("\nYou have successfully logged out");
                            break profile;
                    }
                } while (answer != 0);

            } else {
                System.out.println("Wrong card number or PIN");
            }
        } while (response != 0);
    }
}

