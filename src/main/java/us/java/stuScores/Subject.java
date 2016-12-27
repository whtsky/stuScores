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
public class Subject {
    public String name;
    public long id;
    public Subject() {}

    public Subject(String name, long id){
        this.name = name;
        this.id = id;
    }

    public Subject[] GetAllSubject(){
        try {
            Statement statement = createStatement();
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM subject;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet rs = statement.executeQuery("SELECT * FROM subject;");
            Subject subjects[] = new Subject[rows];
            int i = 0;
            while (rs.next()){
                subjects[i++] = new Subject(rs.getString("name"), rs.getLong("id"));
            }
            return subjects;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
