package us.java.stuScores;

/**
 * Created by 15437 on 2016/11/21.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.Exam.GetAllExam;
import static us.java.stuScores.JDBC.*;
import java.sql.*;

@Path("exam")
public class ExamListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Exam[] getExam(){
        return GetAllExam();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Exam[] addExam(
            @FormParam("name") String name,
            @FormParam("date") String date
    ){
        try{
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.

            statement.executeUpdate("INSERT INTO exam (name, date) VALUES ('" + name + "','" + date + "');");
            return GetAllExam();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}