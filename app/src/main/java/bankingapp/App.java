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

        //Connect to the database
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement = connection.createStatement()) {

                //Create DB
                App.dbTrans = new DBTrans(connection);
                App.dbTrans.createDB(statement);

                int response = -1;

                do {
                    System.out.println("\n1. Create an account\n" +
                            "2. Log into account\n" +
                            "0. Exit");

                    try {
                        response = input.nextInt();
                        switch (response) {
                            case 1:
                                System.out.println(new CreateAccount(connection, dbTrans));
                                break;
                            case 2:
                                new LogIn(connection, dbTrans);
                                break;
                        }
                    }catch (InputMismatchException e){
                        System.out.println("Invalid input");
                        //input.nextLine();
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
}
