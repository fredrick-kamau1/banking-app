package bankingApp;

import java.util.HashMap;

public class Account {
    private final int BIN = 400000;

    private int checkSum;
    private int accountNumber;

    private int pin;

    private HashMap<String, Integer> account;

    public Account() {
        this.accountNumber = accountNumber;
        this.pin = pin;
    }

    public void setAccountNumber(int accountNumber){
        this.accountNumber = ++accountNumber;
    }
    public int getAccountNumber() {
        return ++this.accountNumber;
    }

    public int getPin(){
        pin = (int)(Math.random() * 9999) + 1;
        return pin;
    }

    public int getCheckSum(){

        return this.checkSum;
    }

    @Override
    public String toString() {
        return "Your card has been created \nYour card number: \n" + BIN + String.format("%09d", getAccountNumber()) +
               "\nYour card PIN: " + String.format("%04d", getPin());
    }
}
