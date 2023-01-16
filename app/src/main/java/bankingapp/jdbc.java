package bankingapp;

import java.sql.*;
import org.sqlite.SQLiteDataSource;

public class jdbc {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:test.db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection is valid.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
