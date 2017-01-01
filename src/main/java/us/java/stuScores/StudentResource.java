package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Student.GetAllStudent;

import java.sql.*;

/**
 * Created by 15437 on 2016/11/21.
 */
@Path("/student/{id}")
public class StudentResource{
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student getStudent (@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            ResultSet g_rs =  statement.executeQuery("SELECT * FROM student WHERE id = " + id + ";");
            g_rs.next();
            return new Student(g_rs.getString("name"), Long.parseLong(id));
        }catch (SQLException e){
            return null;
        }
    }

    @Secured
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student modifyStudent (
            @PathParam("id") String id,
            @FormParam("name") String name
    ){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE student SET name = '" + name + "'WHERE id = " + id + ";");
            return new Student(name, Long.parseLong(id));
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    @Secured
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Student[] deleteStudent(@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM student WHERE id = " + id +";");
            return GetAllStudent();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}