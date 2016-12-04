package us.java.stuScores;

import java.sql.*;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ExceptionMapper;

import static us.java.stuScores.JDBC.*;

@Path("student")
public class StudentListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Student[] getStudent() {
        Student students[] = new Student[2];
        students[0] = new Student("吴昊天", "2015220201018");
        students[1] = new Student("天", "2015220201033");
        return students;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public void addStudent(
            @FormParam("name") String name,
            @FormParam("id") String id
    ) throws ClassNotFoundException{

        try
        {
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("INSERT INTO student (id, name) VALUES (" + id + ",'" + name + "');");
        }
        catch(Exception e)
        {
            // if the error message is "out of memory",
            // it probably means no database file is found
            e.printStackTrace();
        }
    }
}