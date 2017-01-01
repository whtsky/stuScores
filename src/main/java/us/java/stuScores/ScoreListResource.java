package us.java.stuScores;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Score.GetAllScore;

import java.sql.*;

/**
 * Created by 15437 on 2016/12/5.
 */
@Path("score")
public class ScoreListResource {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Score[] getScore(){
        return GetAllScore();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Score[] addScore(
            @FormParam("student_id") String student_id,
            @FormParam("exam_id") String exam_id,
            @FormParam("score") String score
    ){
        try
        {
            Statement statement = getConnection().createStatement();
            statement.setQueryTimeout(30);  // set timeout to 30 sec.
            statement.executeUpdate("INSERT INTO score (student_id, exam_id, score) VALUES (" + student_id + "," + exam_id + "," + score  + ");");
            return GetAllScore();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
