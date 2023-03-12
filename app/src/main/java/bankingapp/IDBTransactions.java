package bankingapp;

/**
 * The IDBTransactions interface defines a set of methods for managing bank account information
 * in a database in the context of a banking application. These methods include creating a new database,
 * inserting new bank account records, adding and deducting balances, checking account balances and verifying account
 * numbers and PIN numbers. The interface provides a useful set of methods for interacting with a database in a
 * secure and efficient way.
 */

public interface IDBTransactions {

    /**
     * Creates the database schema for storing information about bank accounts and users.     *
     */

    void createDB();

    /**
     * Inserts a new record into the database with information about a new bank account.
     * @param first_name
     * @param last_name
     * @param accountNumber
     * @param pinNum
     */
    void insertDB(String first_name, String last_name, String accountNumber,
                         String pinNum);

    /**
     * Adds a specified amount of income to the balance of a specified bank account.
     * @param income
     * @param accountNum
     */
    void addIncome(int income, String accountNum);

    /**
     * Deducts a specified amount from the balance of a specified bank account.
     * @param deduct
     * @param accountNum
     */
    void deductBalance(int deduct, String accountNum);

    /**
     * Checks the current balance of a specified bank account.
     * @param accountNum
     * @return
     */
    int checkBalance(String accountNum);

    /**
     * Checks if a specified bank account number and PIN number match the records in the database.
     * @param accountNum
     * @param pin
     * @return
     */
    boolean checkAcc(String accountNum, int pin);

    /**
     * Checks if a specified bank account number matches the records in the database without checking the PIN number.
     * @param accountNum
     * @return
     */
    boolean checkAcc_withoutPin(String accountNum);

    /**
     * Deletes the record of a specified bank account from the database.
     * @param accountNum
     */
    void deleteAcc(String accountNum);
}
