package us.java.stuScores;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static us.java.stuScores.JDBC.createStatement;

public class User {
    public int id;
    public String username;

    public User(){}

    public User(int id, String name){
        this.id = id;
        this.username = name;
    }

    public static int count() {
        try {
            Statement statement = createStatement();

            ResultSet r = statement.executeQuery("SELECT COUNT(*) AS rowcount FROM user;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static User[] GetAllUser(){
        try {
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM user;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM user;");
            User users[] = new User[rows];
            int i = 0;
            while (rs.next()){
                users[i++] = new User(rs.getInt("id"), rs.getString("name"));
            }
            return users;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
