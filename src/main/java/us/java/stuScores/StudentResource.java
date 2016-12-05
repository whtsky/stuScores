package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/student/{id}")
public class StudentResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent (@PathParam("id") String id) throws SQLException {
        try {
            Statement statement = createStatement();
            ResultSet g_rs =  statement.executeQuery("SELECT name FROM student WHERE id = " + Long.parseLong(id) + ";");
            g_rs.next();
            return new Student(g_rs.getString("name"), Long.parseLong(id));
        }catch (SQLException e){
            return null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student modifyStudent (
            @PathParam("id") String id,
            @FormParam("name") String name
    ) throws SQLException{
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE student SET name = '" + name + "'WHERE id = " + Long.parseLong(id) + ";");
            ResultSet p_rs = statement.executeQuery("SELECT name FROM student WHERE id = " + Long.parseLong(id) + ";");
            p_rs.next();
            return new Student(p_rs.getString("name"), Long.parseLong(id));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Student[] deleteStudent(@PathParam("id") String id) throws SQLException{
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM student WHERE id = " + Long.parseLong(id) +";");
            System.out.println(Long.parseLong(id));
            ResultSet r = statement.executeQuery("SELECT COUNT (*) AS rowcount FROM student;");
            r.next();
            int rows = r.getInt("rowcount");
            r.close();
            ResultSet d_rs = statement.executeQuery("SELECT * FROM student;");
            Student students[] = new Student[rows];
            int i = 0;
            while (d_rs.next()){
                long rs_id = d_rs.getLong("id");
                String rs_name = d_rs.getString("name");
                students[i++] = new Student(rs_name, rs_id);
            }
            return students;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}