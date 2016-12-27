package us.java.stuScores;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Student.GetAllStudent;

import java.sql.*;

@Path("student")
public class StudentListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Student[] getStudent(){
        return GetAllStudent();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student[] addStudent(
            @FormParam("name") String name,
            @FormParam("id") long id
    ) throws SQLException{
        try
        {
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("INSERT INTO student (id, name) VALUES (" + id + ",'" + name + "');");
            return GetAllStudent();
        }
        catch(SQLException e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
            return  null;
        }
    }
}