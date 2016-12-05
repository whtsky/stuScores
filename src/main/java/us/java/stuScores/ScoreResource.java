package us.java.stuScores;

/**
 * Created by 15437 on 2016/12/5.
 */
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static us.java.stuScores.JDBC.*;
import java.sql.*;

@Path("/score/{id}")
public class ScoreResource{
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Score getScore (@PathParam("id") String id) throws SQLException {
        try{
            Statement statement = createStatement();
            ResultSet g_rs = statement.executeQuery("SELECT * FROM score WHERE id = " + Integer.parseInt(id) + ";");
            g_rs.next();
            long st_id = g_rs.getLong("student_id");
            String st_name = g_rs.getString("student_name");
            String ex_name = g_rs.getString("exam_name");
            String su_name = g_rs.getString("subject_name");
            int score = g_rs.getInt("score");
            return new Score(Integer.parseInt(id), st_id, st_name, ex_name, su_name, score);
        }catch (SQLException e){
            e.printStackTrace();
            return  null;
        }
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/x-www-form-urlencoded")
    public Student modifyScore (
            @PathParam("id") String id,
            @FormParam("student_id") String student_id,
            @FormParam("student_name") String student_ name,
            @FormParam("student_id") String student_id,
    )
}
