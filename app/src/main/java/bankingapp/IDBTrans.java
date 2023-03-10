package bankingapp;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface IDBTrans {
    void createDB(Statement statement) throws SQLException;
}