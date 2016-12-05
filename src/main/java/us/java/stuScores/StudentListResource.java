package us.java.stuScores;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

@Path("student")
public class StudentListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student[] getStudent() throws SQLException{
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

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void addStudent(
            @FormParam("name") String name,
            @FormParam("id") long id
    ) throws SQLException{
        try
        {
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("INSERT INTO student (id, name) VALUES (" + id + ",'" + name + "');");
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
        }
    }
}