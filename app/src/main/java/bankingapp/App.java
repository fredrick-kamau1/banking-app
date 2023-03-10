/*
 * Copyright Fredrick Kamau
 */
package bankingapp;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static IDBTrans dbTrans;

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        SQLiteDataSource dataSource = new SQLiteDataSource();

        //String url set as program parameter
        dataSource.setUrl(args[0]);

        //Set up DB connection layer
        try (IDBTrans dbTrans = new DBTrans((dataSource))) {
            //Create DB
            dbTrans.createDB();

            int response = -1;

            do {
                System.out.println("\n1. Create an account\n" +
                        "2. Log into account\n" +
                        "0. Exit");

                try {
                    response = input.nextInt();
                    switch (response) {
                        case 1:
                            System.out.println(new CreateAccount(dbTrans));
                            break;
                        case 2:
                            new LogIn(dbTrans);
                            break;
                    }
                }catch (InputMismatchException e){
                    System.out.println("Invalid input");
                    //input.nextLine();
                }
            } while (response != 0);

            System.out.println("\nBye!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
