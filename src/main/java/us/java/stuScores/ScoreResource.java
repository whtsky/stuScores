package us.java.stuScores;

/**
 * Created by 15437 on 2016/12/5.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import static us.java.stuScores.Score.GetAllScore;

import java.sql.*;

@Path("/score/{id}")
public class ScoreResource{
    @Secured
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Score getScore (@PathParam("id") String id){
        try{
            Statement statement = createStatement();
            ResultSet g_rs = statement.executeQuery("SELECT * FROM score WHERE id = " + id + ";");
            g_rs.next();
            return new Score(Long.parseLong(id), g_rs.getLong("student_id"), g_rs.getLong("exam_id"), g_rs.getInt("score"));
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }

    @Secured
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Score modifyScore (
            @PathParam("id") String id,
            @FormParam("score") String score
    ){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("UPDATE score SET score = " + score + " WHERE id = " + id + ";");
            ResultSet p_rs = statement.executeQuery("SELECT * FROM score WHERE id = " + id + ";");
            p_rs.next();
            return new Score(p_rs.getLong("id"), p_rs.getLong("student_id"), p_rs.getLong("exam_id"), p_rs.getInt("score"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Secured
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Score[] deleteScore(@PathParam("id") String id){
        try {
            Statement statement = createStatement();
            statement.executeUpdate("DELETE FROM score WHERE id = " + id + ";");
            return GetAllScore();
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
