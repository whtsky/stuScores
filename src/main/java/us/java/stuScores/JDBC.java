package us.java.stuScores;

import java.sql.*;

public class JDBC
{
    private static Connection connection;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        }
        try {
            // create a database connection
            connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS student(" +
                            "id INTEGER PRIMARY KEY NOT NULL, " +
                            "name TEXT NOT NULL" +
                    ")");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS exam(" +
                            "id INTEGER PRIMARY KEY NOT NULL, " +
                            "name TEXT NOT NULL, " +
                            "date INTERGER NOT NULL" +
                    ")");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS score(" +
                            "id INTEGER PRIMARY KEY NOT NULL, " +
                            "exam_name TEXT NOT NULL, " +
                            "score INTEGER NOT NULL " +
                    ")");
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS subject(" +
                            "id INTEGER PRIMARY KEY NOT NULL, " +
                            "name TEXT NOT NULL " +
                    ")");
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            System.err.println(e.getMessage());
            System.exit(0);
        }
        return connection;
    }
}