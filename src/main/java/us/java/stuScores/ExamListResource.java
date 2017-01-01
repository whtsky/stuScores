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
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)

    public Exam[] getExam(){
        return GetAllExam();
    }

    @Secured
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Exam[] addExam(
            @FormParam("name") String name,
            @FormParam("date") String date,
            @FormParam("subject") String subject
    ){
        try{
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("INSERT INTO exam (name, date, subject_id) VALUES ('" + name + "','" + date + "'," + subject + ");");
            return GetAllExam();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}