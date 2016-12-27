package us.java.stuScores;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static us.java.stuScores.JDBC.createStatement;

/**
 * Created by 15437 on 2016/11/21.
 */
@XmlRootElement
public class Student {
    public String name;
    public long id;

    public Student(){
        name = "";
        id = 0;
    }

    public Student(String name, long id){
        this.name = name;
        this.id = id;
    }

    public static Student[] GetAllStudent(){
        try {
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM student;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM student;");
            Student students[] = new Student[rows];
            int i = 0;
            while (rs.next()){
                long id = rs.getLong("id");
                String name = rs.getString("name");
                students[i++] = new Student(name, id);
            }
            return students;
        } catch (SQLException e) {
            return null;
        }
    }
}
