package bankingapp;

import java.sql.*;
import org.sqlite.SQLiteDataSource;

public class jdbc {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:westeros.db";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection is valid.");
            }

            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("CREATE TABLE IF NOT EXISTS Houses(" +
                        "id INTEGER PRIMARY KEY, " +
                        "name TEXT NOT NULL," +
                        "words TEXT NOT NULL)");

                /*int i = statement.executeUpdate("INSERT INTO Houses VALUES " +
                        "(1, 'Jesus''s Servant', 'His Love')," +
                        "(2, 'He Loves me', 'This I know')," +
                        "(3, 'Precious savior', 'My Lord')");

                System.out.println(i);*/

                /*int u = statement.executeUpdate("UPDATE Houses " +
                        "SET words = 'Sweet Jesus'" +
                        "WHERE id = 2");
                System.out.println(u);

                 */

                ResultSet houses = statement.executeQuery("SELECT * FROM Houses where id = 3");
                while (houses.next()) {
                    int id = houses.getInt("id");
                    String name = houses.getString("name");
                    String words = houses.getString("words");

                    System.out.printf("House %d%n", id);
                    System.out.printf("\tName: %s%n", name);
                    System.out.printf("\tWords: %s%n", words);

                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
